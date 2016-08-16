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
    @Path("/data/{flowRef}/{query}/{providerRef}")
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
            @Context HttpServletRequest request) {
        Queryable quer = SdmxGatewayApplication.getApplication().getQueryable();
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
        if (flow == null) {
            DataflowReference ref = DataflowReference.create(new NestedNCNameID(providerRef), new IDType(flowRef), Version.ONE);
            flow = reg.find(ref);
        }
        params.setDataflow(flow);
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
                    ex.printStackTrace();
                }
                os.flush();
                os.close();
            }
        };
        MediaType m = new MediaType("application", "application/vnd.sdmx.data+json;version=1.0.0-wd");
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
    @Path("/data/{flowRef}/{query}/{providerRef}")
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
            @Context HttpServletRequest request) {
        Queryable quer = SdmxGatewayApplication.getApplication().getQueryable();
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
        if (flow == null) {
            DataflowReference ref = DataflowReference.create(new NestedNCNameID(providerRef), new IDType(flowRef), Version.ONE);
            flow = reg.find(ref);
        }
        params.setDataflow(flow);
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
                    ex.printStackTrace();
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
    @Path("/data/{flowRef}/{query}/{providerRef}")
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
            @Context HttpServletRequest request) {
        Queryable quer = SdmxGatewayApplication.getApplication().getQueryable();
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
        if (flow == null) {
            DataflowReference ref = DataflowReference.create(new NestedNCNameID(providerRef), new IDType(flowRef), Version.ONE);
            flow = reg.find(ref);
        }
        params.setDataflow(flow);
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
                    ex.printStackTrace();
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
    @Path("/data/{flowRef}/{query}/{providerRef}")
    @Produces({"application/json"})
    public Response getJSONStatData(@DefaultValue("2000-01-01") @QueryParam("startPeriod") String startPeriod,
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
            @Context HttpServletRequest request) {
        try {
            Queryable quer = SdmxGatewayApplication.getApplication().getQueryable();
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
            if (flow == null) {
                DataflowReference ref = DataflowReference.create(new NestedNCNameID(providerRef), new IDType(flowRef), Version.ONE);
                flow = reg.find(ref);
            }
            params.setDataflow(flow);
            String queryString2 = queryString + "?startPeriod=" + startPeriod + "&endPeriod=" + endPeriod;
            DataMessage dm = rep.query(params, queryString2);
            StreamingOutput stream = new StreamingOutput() {
                @Override
                public void write(OutputStream os) throws IOException,
                        WebApplicationException {
                    SdmxIO.write(params, "application/json", dm, os);
                    os.flush();
                    os.close();
                }
            };
            MediaType m = new MediaType("application", "application/json");
            return Response.ok(stream).type(m).build();
        } catch (ParseException ex) {
            Logger.getLogger(RepositoryService.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        } catch (IOException ex) {
            Logger.getLogger(RepositoryService.class.getName()).log(Level.SEVERE, null, ex);
            ex.printStackTrace();
        }
        return Response.serverError().build();
    }

    public Queryable getProviderQueryable(int i) {
        Queryable q = providerMap.get(i);
        if (q == null) {
            q = DataProvider.getList().get(i - 1).getQueryable();
            providerMap.put(i, q);
        }
        return q;
    }
}
