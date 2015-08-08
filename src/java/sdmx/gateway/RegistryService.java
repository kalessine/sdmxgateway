/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway;

import java.io.IOException;
import java.io.OutputStream;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.StreamingOutput;
import sdmx.SdmxIO;
import sdmx.commonreferences.CodelistReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.exception.ParseException;
import sdmx.message.BaseHeaderType;
import sdmx.message.HeaderTimeType;
import sdmx.structure.CodelistsType;
import sdmx.message.StructureType;
import sdmx.structure.StructuresType;
import sdmx.structure.codelist.CodelistType;
import sdmx.version.common.ParseParams;

/**
 *
 * @author James
 */
@Path("/")
public class RegistryService {
    @GET
    @Path("codelist/{agencyId}/{idtype}/{version}")
    @Produces("application/vnd.sdmx.structure+xml;version=2.1")
    public Response getCodelistXML21(@PathParam("agencyId") String agencyId,@PathParam("idtype") String id, @PathParam("version") String version,@QueryParam("references") String references){
        CodelistReference ref = CodelistReference.create(new NestedNCNameID(agencyId), new IDType(id), new Version(version));
        List<CodelistType> codelists = SdmxGatewayApplication.getApplication().getQueryable().getRegistry().search(ref);
        StructureType struct = new StructureType();
        CodelistsType codelists2 = new CodelistsType(codelists);
        StructuresType structures = new StructuresType();
        structures.setCodelists(codelists2);
        struct.setStructures(structures);
        struct.setHeader(getStructureHeader());
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException,
                    WebApplicationException {
                ParseParams params = new ParseParams();
                SdmxIO.write(params, "application/vnd.sdmx.structure+xml;version=2.1", struct, os);
                os.flush();
                os.close();
            }
        };
        MediaType m = new MediaType("application", "vnd.sdmx.genericdata+xml;version=2.1");
        return Response.ok(stream).type(m).build();
    }
    /*
    @Path("/codelist/{agencyId}/{id}/{version}")
    public Response getCodelistXML20(@PathParam("agencyId") String agencyId,@PathParam("id") String id, @PathParam("version") String version,@QueryParam("references") String references){
        return Response.ok().build();
    }
    @Path("/codelist/{agencyId}/{id}/{version}")
    public Response getCodelistJSON(@PathParam("agencyId") String agencyId,@PathParam("id") String id, @PathParam("version") String version,@QueryParam("references") String references){
        return Response.ok().build();
    }
    */
    private BaseHeaderType getStructureHeader() {
        BaseHeaderType header = new BaseHeaderType();
        header.setId("");
        HeaderTimeType htt = new HeaderTimeType();
        htt.setDate(new sdmx.xml.DateTime(new Date()));
        header.setPrepared(htt);
        return header;
    }
}
