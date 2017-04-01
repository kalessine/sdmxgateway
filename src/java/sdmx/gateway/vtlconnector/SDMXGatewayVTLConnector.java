package sdmx.gateway.vtlconnector;

import com.google.common.collect.Lists;
import no.ssb.vtl.connector.Connector;
import no.ssb.vtl.connector.ConnectorException;
import no.ssb.vtl.connector.NotFoundException;
import no.ssb.vtl.model.Component;
import no.ssb.vtl.model.DataPoint;
import no.ssb.vtl.model.DataStructure;
import no.ssb.vtl.model.Dataset;

import java.net.URI;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;
import static java.util.Arrays.asList;
import javax.script.Bindings;
import javax.script.ScriptContext;
import javax.script.ScriptException;
import jersey.repackaged.com.google.common.collect.Maps;
import no.ssb.vtl.model.VTLObject;
import no.ssb.vtl.script.VTLScriptEngine;
import sdmx.Queryable;
import sdmx.SdmxIO;
import sdmx.commonreferences.DataflowRef;
import sdmx.commonreferences.DataflowReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedNCNameID;

/**
 * A VTL connector that gets data from api.ssb.no.
 */
public class SDMXGatewayVTLConnector implements Connector {

    public static void main(String args[]) throws ScriptException {
        SDMXGatewayVTLConnector con = new SDMXGatewayVTLConnector();
        VTLScriptEngine engine = new VTLScriptEngine(new Connector[]{con});
        Bindings bindings = engine.getBindings(ScriptContext.ENGINE_SCOPE);
        engine.eval("ds1 := get(\"sdmx://ABS/ABS/ABS_CENSUS2011_B01/.....?startPeriod=2000&endPeriod=2015\")");
        DatasetWrapper wrapper = convertToDatasetWrapper("ds1", (Dataset) bindings.get("ds1"));
        for(int i=0;i<wrapper.getData().size();i++) {
            for(int j=0;j<wrapper.getData().get(i).size();j++) {
                System.out.print(wrapper.getData().get(i).get(j));
                if(j<wrapper.getData().get(i).size()){
                    System.out.print(" ");
                }
            }
            System.out.println();
        }
    }

    /*
        The list of available datasets:
        http://data.ssb.no/api/v0/dataset/list.json?lang=en

        Example dataset:
        http://data.ssb.no/api/v0/dataset/1106.json?lang=en

     */
    public SDMXGatewayVTLConnector() {
    }

    public boolean canHandle(String identifier) {
        return identifier.startsWith("sdmx://");
    }
    /*
    sdmx://ABS/ABS/ALC/.....
     */

    public Dataset getDataset(String identifier) throws ConnectorException {
        String agency = identifier.substring("sdmx://".length(), identifier.indexOf("/", "sdmx://".length() + 1));
        int end = identifier.indexOf("/", ("sdmx://" + agency).length() + 1);
        String dfagency = identifier.substring(("(sdmx://" + agency).length(), identifier.indexOf("/", ("sdmx://" + agency).length() + 1));
        String dataflow = identifier.substring(("sdmx://" + agency + "/" + dfagency).length() + 1, identifier.indexOf("/", ("sdmx://" + agency + "/" + dfagency).length() + 1));
        String query = identifier.substring(("sdmx://" + agency + "/" + dfagency + "/" + dataflow).length() + 1, identifier.indexOf("?", ("sdmx://" + agency + "/" + dfagency + "/" + dataflow).length() + 1));
        String startPeriod = identifier.substring(("sdmx://" + agency + "/" + dfagency + "/" + dataflow + "/" + query + "?startPeriod").length() + 1, identifier.indexOf("&endPeriod=", ("sdmx://" + agency + "/" + dfagency + "/" + dataflow + "/" + query).length() + 1));
        String endPeriod = identifier.substring(("sdmx://" + agency + "/" + dfagency + "/" + dataflow + "/" + query + "?startPeriod=" + startPeriod + "&endPeriod").length() + 1, identifier.length());
        /*
        System.out.println("Agency:" + agency);
        System.out.println("DFAgency:" + dfagency);
        System.out.println("Dataflow:" + dataflow);
        System.out.println("Query:" + query);
        System.out.println("SP:" + startPeriod);
        System.out.println("EP:" + endPeriod);
        */
        Queryable q = SdmxIO.simpleConnect(agency);
        DataflowRef ref = new DataflowRef(new NestedNCNameID(dfagency), new IDType(dataflow), null);
        DataflowReference reference = new DataflowReference(ref, null);
        return new SdmxDataset(q, q.getRegistry().find(reference), query, startPeriod, endPeriod);
    }

    public Dataset putDataset(String identifier, Dataset dataset) throws ConnectorException {
        return null;
    }
    public static DatasetWrapper convertToDatasetWrapper(String name, Dataset dataset) {
        DatasetWrapper wrapper = new DatasetWrapper();
        wrapper.setName(name);
        List<ComponentWrapper> structure = wrapper.getStructure();
        for (Map.Entry<String, Component> component : dataset.getDataStructure().entrySet()) {
            ComponentWrapper componentWrapper = new ComponentWrapper();
            componentWrapper.setName(component.getKey());
            componentWrapper.setRole(component.getValue().getRole());
            componentWrapper.setType(component.getValue().getType());
            structure.add(componentWrapper);
        }
        List<List<Object>> data = dataset.getData()
                .map(tuple -> tuple.stream()
                        .map(VTLObject::get).collect(Collectors.toList())
                ).collect(Collectors.toList());
        wrapper.setData(data);
        return wrapper;
    }
    static class DatasetWrapper {

        private String name;
        private List<ComponentWrapper> structure = Lists.newArrayList();
        private List<List<Object>> data = Lists.newArrayList();

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<ComponentWrapper> getStructure() {
            return structure;
        }

        public void setStructure(List<ComponentWrapper> structure) {
            this.structure = structure;
        }

        public List<List<Object>> getData() {
            return data;
        }

        public void setData(List<List<Object>> data) {
            this.data = data;
        }
    }

    static class ComponentWrapper {

        private String name;
        private Component.Role role;
        private Class<?> type;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public Class<?> getType() {
            return type;
        }

        public void setType(Class<?> type) {
            this.type = type;
        }

        public Class<?> getClassType() {
            return type;
        }

        public Component.Role getRole() {
            return role;
        }

        public void setRole(Component.Role role) {
            this.role = role;
        }

    }
}
