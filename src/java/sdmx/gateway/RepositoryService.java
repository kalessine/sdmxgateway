/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.DefaultValue;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import sdmx.Queryable;
import sdmx.Registry;
import sdmx.Repository;
import sdmx.SdmxIO;
import sdmx.exception.ParseException;
import sdmx.message.DataMessage;
import sdmx.message.DataQueryMessage;
import sdmx.net.list.DataProvider;
import sdmx.query.base.TimeValue;
import sdmx.query.data.DataParametersAndType;
import sdmx.query.data.DataParametersOrType;
import sdmx.query.data.DataParametersType;
import sdmx.query.data.DataQuery;
import sdmx.query.data.DimensionValueType;
import sdmx.query.data.TimeDimensionValueType;
import sdmx.structure.base.ComponentUtil;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.version.common.ParseParams;
import org.apache.http.client.HttpClient;
import sdmx.commonreferences.DataflowReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;

/**
 *
 * @author James
 */
@Path("repository")
public class RepositoryService {

    HashMap<Integer, Queryable> providerMap = null;

    public RepositoryService() {
        this.providerMap = new HashMap<Integer, Queryable>();
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "text/plain" media type.
     *
     * @param <error>
     * @param startPeriod
     * @param firstNObservations
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("{provider}/data/{flowRef}/{query}/{providerRef}")
    @Produces({"application/vnd.sdmx.data+json;version=1.0.0-wd"})
    public Response getJSONData(@DefaultValue("2000-01-01") @QueryParam("startPeriod") String startPeriod,
            @DefaultValue("2010-01-01") @QueryParam("endPeriod") String endPeriod,
            @DefaultValue("") @QueryParam("updatedAfter") String updatedAfter,
            @QueryParam("firstNObservations") int firstNObservations,
            @QueryParam("lastNObservations") int lastNObservations,
            @QueryParam("dimensionAtObservation") String dimensionAtObservation,
            @QueryParam("detail") String detail,
            @QueryParam("includeHistory") boolean includeHistory,
            @PathParam("flowRef") String flowRef,
            @PathParam("query") String queryString,
            @PathParam("providerRef") String providerRef,
            @PathParam("provider") Integer provider,@Context HttpServletRequest request) {
        Queryable quer = getProviderQueryable(provider);
        Registry reg = quer.getRegistry();
        Repository rep = quer.getRepository();
        ParseParams params = new ParseParams();
        params.setLocale(request.getLocale());
        params.setRegistry(reg);
        DataflowType flow = null;
        List<DataflowType> dataflowList = quer.getRegistry().listDataflows();
        for (int i = 0; i < dataflowList.size(); i++) {
            if (dataflowList.get(i).getId().equals(flowRef)) {
                flow = dataflowList.get(i);
            }
        }
        if( flow == null ) {
            DataflowReference ref = DataflowReference.create(new NestedNCNameID(providerRef), new IDType(flowRef),Version.ONE);
            flow = reg.find(ref);
        }
        params.setDataflow(flow);
        DataQueryMessage query = new DataQueryMessage();
        DataStructureType struct = quer.getRegistry().find(flow.getStructure());
        // Ignore the Header, it will be filled in by the driver
        // You can set the Header fields yourself if you wish, and the driver
        // will not overwrite them
        DataQuery q = new DataQuery();
        DataParametersAndType dw = new DataParametersAndType();
        List<DataParametersOrType> ors = new ArrayList<DataParametersOrType>();
        // This sets which cube we want to query...
        // some queryables fudge the dataflow name (like SDW)
        // as SDW does not have dataflows, only a list of datastructures
        dw.setDataflow(Collections.singletonList(flow.asReference()));
        // year-month-day
        dw.setTimeDimensionValue(Collections.singletonList(new TimeDimensionValueType(new TimeValue("2010-01-01"), new TimeValue("2014-01-01"))));
        for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            DataParametersOrType or = new DataParametersOrType();
            List<DimensionValueType> dims = new ArrayList<DimensionValueType>();
            CodelistType codes1 = reg.find(ComponentUtil.getRepresentation(reg, struct.getDataStructureComponents().getDimensionList().getDimension(i)).getEnumeration().asCodelistReference());
            dims.add(new DimensionValueType(struct.getDataStructureComponents().getDimensionList().getDimension(i).getId().toString(), codes1.getItem(0).getId().toString()));
            // If there is more than 1 code in the codelist, include the second code too!
            if (codes1.size() > 1) {
                dims.add(new DimensionValueType(struct.getDataStructureComponents().getDimensionList().getDimension(i).getId().toString(), codes1.getItem(1).getId().toString()));
            }
            or.setDimensionValue(dims);
            ors.add(or);
        }
        dw.setOr(ors);
        DataParametersType dpt = new DataParametersType();
        // Some Providers require another "AND" query element to be under the main DataParametersType(which is an And)
        // so we put everything into dw and set it here
        dpt.setAnd(Collections.singletonList(dw));
        q.setDataWhere(dpt);
        query.setQuery(q);
        long t3 = System.currentTimeMillis();
        SdmxIO.setDumpQuery(true);

        String queryString2 = queryString + "?startPeriod=" + startPeriod + "&endPeriod=" + endPeriod;
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException,
                    WebApplicationException {
                params.setCallbackHandler(SdmxIO.openForStreamWriting("application/vnd.sdmx.data+json;version=1.0.0-wd", os, params));
                try {
                    rep.query(params, queryString2);
                } catch (ParseException ex) {
                    Logger.getLogger(RepositoryService.class.getName()).log(Level.SEVERE, null, ex);
                }
                os.flush();
                os.close();
            }
        };
        MediaType m = new MediaType("application", "vnd.sdmx.data+json;version=1.0.0-wd");
        return Response.ok(stream).type(m).build();
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "text/plain" media type.
     *
     * @param <error>
     * @param startPeriod
     * @param firstNObservations
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("{provider}/data/{flowRef}/{query}/{providerRef}")
    @Produces({"application/vnd.sdmx.genericdata+xml;version=2.1"})
    public Response getGenericData(@DefaultValue("2000-01-01") @QueryParam("startPeriod") String startPeriod,
            @DefaultValue("2010-01-01") @QueryParam("endPeriod") String endPeriod,
            @DefaultValue("") @QueryParam("updatedAfter") String updatedAfter,
            @QueryParam("firstNObservations") int firstNObservations,
            @QueryParam("lastNObservations") int lastNObservations,
            @QueryParam("dimensionAtObservation") String dimensionAtObservation,
            @QueryParam("detail") String detail,
            @QueryParam("includeHistory") boolean includeHistory,
            @PathParam("flowRef") String flowRef,
            @PathParam("query") String queryString,
            @PathParam("providerRef") String providerRef,
            @PathParam("provider") Integer provider,@Context HttpServletRequest request) {
        Queryable quer = getProviderQueryable(provider);
        Registry reg = quer.getRegistry();
        Repository rep = quer.getRepository();
        ParseParams params = new ParseParams();
        params.setRegistry(reg);
        params.setLocale(request.getLocale());
        DataflowType flow = null;
        List<DataflowType> dataflowList = quer.getRegistry().listDataflows();
        for (int i = 0; i < dataflowList.size(); i++) {
            if (dataflowList.get(i).getId().equals(flowRef)) {
                flow = dataflowList.get(i);
            }
        }
        if( flow == null ) {
            DataflowReference ref = DataflowReference.create(new NestedNCNameID(providerRef), new IDType(flowRef),Version.ONE);
            flow = reg.find(ref);
        }
        params.setDataflow(flow);
        DataQueryMessage query = new DataQueryMessage();
        DataStructureType struct = quer.getRegistry().find(flow.getStructure());
        // Ignore the Header, it will be filled in by the driver
        // You can set the Header fields yourself if you wish, and the driver
        // will not overwrite them
        DataQuery q = new DataQuery();
        DataParametersAndType dw = new DataParametersAndType();
        List<DataParametersOrType> ors = new ArrayList<DataParametersOrType>();
        // This sets which cube we want to query...
        // some queryables fudge the dataflow name (like SDW)
        // as SDW does not have dataflows, only a list of datastructures
        dw.setDataflow(Collections.singletonList(flow.asReference()));
        // year-month-day
        dw.setTimeDimensionValue(Collections.singletonList(new TimeDimensionValueType(new TimeValue("2010-01-01"), new TimeValue("2014-01-01"))));
        for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            DataParametersOrType or = new DataParametersOrType();
            List<DimensionValueType> dims = new ArrayList<DimensionValueType>();
            CodelistType codes1 = reg.find(ComponentUtil.getRepresentation(reg, struct.getDataStructureComponents().getDimensionList().getDimension(i)).getEnumeration().asCodelistReference());
            dims.add(new DimensionValueType(struct.getDataStructureComponents().getDimensionList().getDimension(i).getId().toString(), codes1.getItem(0).getId().toString()));
            // If there is more than 1 code in the codelist, include the second code too!
            if (codes1.size() > 1) {
                dims.add(new DimensionValueType(struct.getDataStructureComponents().getDimensionList().getDimension(i).getId().toString(), codes1.getItem(1).getId().toString()));
            }
            or.setDimensionValue(dims);
            ors.add(or);
        }
        dw.setOr(ors);
        DataParametersType dpt = new DataParametersType();
        // Some Providers require another "AND" query element to be under the main DataParametersType(which is an And)
        // so we put everything into dw and set it here
        dpt.setAnd(Collections.singletonList(dw));
        q.setDataWhere(dpt);
        query.setQuery(q);
        long t3 = System.currentTimeMillis();
        SdmxIO.setDumpQuery(true);

        String queryString2 = queryString + "?startPeriod=" + startPeriod + "&endPeriod=" + endPeriod;
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException,
                    WebApplicationException {
                params.setCallbackHandler(SdmxIO.openForStreamWriting("application/vnd.sdmx.genericdata+xml;version=2.1", os, params));
                try {
                    rep.query(params, queryString2);
                } catch (ParseException ex) {
                    Logger.getLogger(RepositoryService.class.getName()).log(Level.SEVERE, null, ex);
                }
                os.flush();
                os.close();
            }
        };
        MediaType m = new MediaType("application", "vnd.sdmx.genericdata+xml;version=2.1");
        return Response.ok(stream).type(m).build();
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "text/plain" media type.
     *
     * @param <error>
     * @param startPeriod
     * @param firstNObservations
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("{provider}/data/{flowRef}/{query}/{providerRef}")
    @Produces({"application/vnd.sdmx.structurespecificdata+xml;version=2.1"})
    public Response getSructSpecData(@DefaultValue("2000-01-01") @QueryParam("startPeriod") String startPeriod,
            @DefaultValue("2010-01-01") @QueryParam("endPeriod") String endPeriod,
            @DefaultValue("") @QueryParam("updatedAfter") String updatedAfter,
            @QueryParam("firstNObservations") int firstNObservations,
            @QueryParam("lastNObservations") int lastNObservations,
            @QueryParam("dimensionAtObservation") String dimensionAtObservation,
            @QueryParam("detail") String detail,
            @QueryParam("includeHistory") boolean includeHistory,
            @PathParam("flowRef") String flowRef,
            @PathParam("query") String queryString,
            @PathParam("providerRef") String providerRef,
            @PathParam("provider") Integer provider,@Context HttpServletRequest request) {
        Queryable quer = getProviderQueryable(provider);
        Registry reg = quer.getRegistry();
        Repository rep = quer.getRepository();
        ParseParams params = new ParseParams();
        params.setRegistry(reg);
        params.setLocale(request.getLocale());
        DataflowType flow = null;
        List<DataflowType> dataflowList = quer.getRegistry().listDataflows();
        for (int i = 0; i < dataflowList.size(); i++) {
            if (dataflowList.get(i).getId().equals(flowRef)) {
                flow = dataflowList.get(i);
            }
        }
        if( flow == null ) {
            DataflowReference ref = DataflowReference.create(new NestedNCNameID(providerRef), new IDType(flowRef),Version.ONE);
            flow = reg.find(ref);
        }
        params.setDataflow(flow);
        DataQueryMessage query = new DataQueryMessage();
        DataStructureType struct = quer.getRegistry().find(flow.getStructure());
        // Ignore the Header, it will be filled in by the driver
        // You can set the Header fields yourself if you wish, and the driver
        // will not overwrite them
        DataQuery q = new DataQuery();
        DataParametersAndType dw = new DataParametersAndType();
        List<DataParametersOrType> ors = new ArrayList<DataParametersOrType>();
        // This sets which cube we want to query...
        // some queryables fudge the dataflow name (like SDW)
        // as SDW does not have dataflows, only a list of datastructures
        dw.setDataflow(Collections.singletonList(flow.asReference()));
        // year-month-day
        dw.setTimeDimensionValue(Collections.singletonList(new TimeDimensionValueType(new TimeValue("2010-01-01"), new TimeValue("2014-01-01"))));
        for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            DataParametersOrType or = new DataParametersOrType();
            List<DimensionValueType> dims = new ArrayList<DimensionValueType>();
            CodelistType codes1 = reg.find(ComponentUtil.getRepresentation(reg, struct.getDataStructureComponents().getDimensionList().getDimension(i)).getEnumeration().asCodelistReference());
            dims.add(new DimensionValueType(struct.getDataStructureComponents().getDimensionList().getDimension(i).getId().toString(), codes1.getItem(0).getId().toString()));
            // If there is more than 1 code in the codelist, include the second code too!
            if (codes1.size() > 1) {
                dims.add(new DimensionValueType(struct.getDataStructureComponents().getDimensionList().getDimension(i).getId().toString(), codes1.getItem(1).getId().toString()));
            }
            or.setDimensionValue(dims);
            ors.add(or);
        }
        dw.setOr(ors);
        DataParametersType dpt = new DataParametersType();
        // Some Providers require another "AND" query element to be under the main DataParametersType(which is an And)
        // so we put everything into dw and set it here
        dpt.setAnd(Collections.singletonList(dw));
        q.setDataWhere(dpt);
        query.setQuery(q);
        long t3 = System.currentTimeMillis();
        SdmxIO.setDumpQuery(true);

        String queryString2 = queryString + "?startPeriod=" + startPeriod + "&endPeriod=" + endPeriod;
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException,
                    WebApplicationException {
                params.setCallbackHandler(SdmxIO.openForStreamWriting("application/vnd.sdmx.structurespecificdata+xml;version=2.1", os, params));
                try {
                    rep.query(params, queryString2);
                } catch (ParseException ex) {
                    Logger.getLogger(RepositoryService.class.getName()).log(Level.SEVERE, null, ex);
                }
                os.flush();
                os.close();
            }
        };
        MediaType m = new MediaType("application", "vnd.sdmx.structurespecificdata+xml;version=2.1");
        return Response.ok(stream).type(m).build();
    }

    /**
     * Method handling HTTP GET requests. The returned object will be sent to
     * the client as "text/plain" media type.
     *
     * @param <error>
     * @param startPeriod
     * @param firstNObservations
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Path("{provider}/data/{flowRef}/{query}/{providerRef}")
    @Produces({"application/json"})
    public Response getJsonStat(@DefaultValue("2000-01-01") @QueryParam("startPeriod") String startPeriod,
            @DefaultValue("2010-01-01") @QueryParam("endPeriod") String endPeriod,
            @DefaultValue("") @QueryParam("updatedAfter") String updatedAfter,
            @QueryParam("firstNObservations") int firstNObservations,
            @QueryParam("lastNObservations") int lastNObservations,
            @QueryParam("dimensionAtObservation") String dimensionAtObservation,
            @QueryParam("detail") String detail,
            @QueryParam("includeHistory") boolean includeHistory,
            @PathParam("flowRef") String flowRef,
            @PathParam("query") String queryString,
            @PathParam("providerRef") String providerRef,
            @PathParam("provider") Integer provider,@Context HttpServletRequest request) {
        try {
            Queryable quer = getProviderQueryable(provider);
            Registry reg = quer.getRegistry();
            Repository rep = quer.getRepository();
            ParseParams params = new ParseParams();
            params.setRegistry(reg);
            params.setLocale(request.getLocale());
            DataflowType flow = null;
            List<DataflowType> dataflowList = quer.getRegistry().listDataflows();
            for (int i = 0; i < dataflowList.size(); i++) {
                if (dataflowList.get(i).getId().equals(flowRef)) {
                    flow = dataflowList.get(i);
                }
            }
            params.setDataflow(flow);
            DataQueryMessage query = new DataQueryMessage();
            DataStructureType struct = quer.getRegistry().find(flow.getStructure());
            // Ignore the Header, it will be filled in by the driver
            // You can set the Header fields yourself if you wish, and the driver
            // will not overwrite them
            DataQuery q = new DataQuery();
            DataParametersAndType dw = new DataParametersAndType();
            List<DataParametersOrType> ors = new ArrayList<DataParametersOrType>();
            // This sets which cube we want to query...
            // some queryables fudge the dataflow name (like SDW)
            // as SDW does not have dataflows, only a list of datastructures
            dw.setDataflow(Collections.singletonList(flow.asReference()));
            // year-month-day
            dw.setTimeDimensionValue(Collections.singletonList(new TimeDimensionValueType(new TimeValue("2010-01-01"), new TimeValue("2014-01-01"))));
            for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
                DataParametersOrType or = new DataParametersOrType();
                List<DimensionValueType> dims = new ArrayList<DimensionValueType>();
                CodelistType codes1 = reg.find(ComponentUtil.getRepresentation(reg, struct.getDataStructureComponents().getDimensionList().getDimension(i)).getEnumeration().asCodelistReference());
                dims.add(new DimensionValueType(struct.getDataStructureComponents().getDimensionList().getDimension(i).getId().toString(), codes1.getItem(0).getId().toString()));
                // If there is more than 1 code in the codelist, include the second code too!
                if (codes1.size() > 1) {
                    dims.add(new DimensionValueType(struct.getDataStructureComponents().getDimensionList().getDimension(i).getId().toString(), codes1.getItem(1).getId().toString()));
                }
                or.setDimensionValue(dims);
                ors.add(or);
            }
            dw.setOr(ors);
            DataParametersType dpt = new DataParametersType();
            // Some Providers require another "AND" query element to be under the main DataParametersType(which is an And)
            // so we put everything into dw and set it here
            dpt.setAnd(Collections.singletonList(dw));
            q.setDataWhere(dpt);
            query.setQuery(q);
            long t3 = System.currentTimeMillis();
            String queryString2 = queryString + "?startPeriod=" + startPeriod + "&endPeriod=" + endPeriod;
            DataMessage msg = rep.query(params, queryString2);
            msg.setDataStructure(flow.getStructure(), null);
            StreamingOutput stream = new StreamingOutput() {
                @Override
                public void write(OutputStream os) throws IOException,
                        WebApplicationException {
                    SdmxIO.write(params, "application/json", msg, os);
                    os.flush();
                    os.close();
                }
            };
            MediaType m = new MediaType("application", "json");
            return Response.ok(stream).type(m).build();
        } catch (ParseException ex) {
            Logger.getLogger(RepositoryService.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(RepositoryService.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Response.serverError().build();
    }

    public Queryable getProviderQueryable(int i) {
        Queryable q = providerMap.get(i);
        if (q == null) {
            q = DataProvider.getList().get(i-1).getQueryable();
            providerMap.put(i, q);
        }
        return q;
    }
}
