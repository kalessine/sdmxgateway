/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Collections;
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
import sdmx.common.TextType;
import sdmx.commonreferences.CodelistReference;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.ConceptSchemeReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.exception.ParseException;
import sdmx.message.BaseHeaderType;
import sdmx.message.HeaderTimeType;
import sdmx.structure.CodelistsType;
import sdmx.message.StructureType;
import sdmx.structure.ConceptsType;
import sdmx.structure.StructuresType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.version.common.ParseParams;
import sdmx.commonreferences.DataStructureReference;
import sdmx.message.ContactType;
import sdmx.message.PartyType;
import sdmx.message.SenderType;
import sdmx.structure.DataStructuresType;
import sdmx.structure.base.ItemType;
import sdmx.structure.concept.ConceptType;
import sdmx.structure.datastructure.AttributeType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.structure.datastructure.MeasureDimensionType;
import sdmx.structure.datastructure.PrimaryMeasure;
import sdmx.structure.datastructure.TimeDimensionType;

/**
 *
 * @author James
 */
@Path("/")
public class RegistryService {

    @GET
    @Path("codelist/{agencyId}/{idtype}/{version}")
    @Produces("application/vnd.sdmx.structure+xml;version=2.1")
    public Response getCodelistXML21(@PathParam("agencyId") String agencyId, @PathParam("idtype") String id, @PathParam("version") String version, @QueryParam("detail") String detail, @QueryParam("references") String references) {
        CodelistReference ref = CodelistReference.create(new NestedNCNameID(agencyId), new IDType(id), new Version(version));
        List<CodelistType> codelists = SdmxGatewayApplication.getApplication().getRegistry().search(ref);
        final StructureType struct = new StructureType();

        if ("full".equals(detail)) {

        } else if ("stubs".equals(detail)) {
            for (int i = 0; i < codelists.size(); i++) {
                codelists.get(i).setItems(null);
            }
        } else if ("referencestubs".equals(detail)) {
            // codelists don't reference anything

        }
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
                SdmxIO.writeStructure("application/vnd.sdmx.structure+xml;version=2.1", struct, os);
                os.flush();
                os.close();
            }
        };
        MediaType m = new MediaType("application", "application/vnd.sdmx.structure+xml;version=2.1");
        return Response.ok(stream).type(m).build();
    }

    @GET
    @Path("conceptscheme/{agencyId}/{idtype}/{version}")
    @Produces("application/vnd.sdmx.structure+xml;version=2.1")
    public Response getConceptSchemetXML21(@PathParam("agencyId") String agencyId, @PathParam("idtype") String id, @PathParam("version") String version, @QueryParam("detail") String detail, @QueryParam("references") String references) {
        ConceptSchemeReference ref = ConceptSchemeReference.create(new NestedNCNameID(agencyId), new IDType(id), new Version(version));
        List<ConceptSchemeType> conceptschemes = SdmxGatewayApplication.getApplication().getRegistry().search(ref);
        if ("full".equals(detail)) {

        } else if ("stubs".equals(detail)) {
            for (int i = 0; i < conceptschemes.size(); i++) {
                conceptschemes.get(i).setItems(null);
            }
        } else if ("referencestubs".equals(detail)) {
            // codelists don't reference anything

        }
        final StructureType struct = new StructureType();
        ConceptsType concepts2 = new ConceptsType();
        concepts2.setConceptSchemes(conceptschemes);
        StructuresType structures = new StructuresType();
        structures.setConcepts(concepts2);
        struct.setStructures(structures);
        struct.setHeader(getStructureHeader());
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException,
                    WebApplicationException {
                SdmxIO.writeStructure("application/vnd.sdmx.structure+xml;version=2.1", struct, os);
                os.flush();
                os.close();
            }
        };
        MediaType m = new MediaType("application", "application/vnd.sdmx.structure+xml;version=2.1");
        return Response.ok(stream).type(m).build();
    }

    @GET
    @Path("datastructure/{agencyId}/{idtype}/{version}")
    @Produces("application/vnd.sdmx.structure+xml;version=2.1")
    public Response getDataStructre21(@PathParam("agencyId") String agencyId, @PathParam("idtype") String id, @PathParam("version") String version, @QueryParam("detail") String detail, @QueryParam("references") String references) {
        final StructureType struct = new StructureType();
        StructuresType structures = new StructuresType();
        DataStructureReference ref = DataStructureReference.create(new NestedNCNameID(agencyId), new IDType(id), new Version(version));

        List<DataStructureType> dss = SdmxGatewayApplication.getApplication().getRegistry().search(ref);
        CodelistsType cd = new CodelistsType();
        ConceptsType cs = new ConceptsType();
        if ("parents".equals(references)) {
            // dataflows
            // categorisations

        } else if ("parentsandsiblings".equals(references)) {

        } else if ("children".equals(references)) {
            for (int i = 0; i < dss.size(); i++) {
                for (int j = 0; j < dss.get(i).getDataStructureComponents().getDimensionList().size(); j++) {
                    DimensionType dim = dss.get(i).getDataStructureComponents().getDimensionList().getDimension(j);
                    ConceptReference cref = dim.getConceptIdentity();
                    ConceptSchemeReference csref = cref.toConceptSchemeReference();
                    ConceptSchemeType cscheme = null;
                    ConceptType ct = SdmxGatewayApplication.getApplication().getRegistry().find(cref);
                    if (cs.find(csref) != null) {
                        cscheme = cs.find(csref);
                        cscheme.addConcept(ct);
                    } else {
                        cscheme = SdmxGatewayApplication.getApplication().getRegistry().find(csref);
                        cs.getConceptSchemes().add(cscheme);
                        cscheme.setItems(new ArrayList<ItemType>());
                        cscheme.addConcept(ct);
                    }
                    if (dim.getLocalRepresentation().getEnumeration() != null) {
                        CodelistType cl = (CodelistType) SdmxGatewayApplication.getApplication().getRegistry().find(dim.getLocalRepresentation().getEnumeration());
                        cd.getCodelists().add(cl);
                    }
                }
                if (dss.get(i).getDataStructureComponents().getDimensionList().getMeasureDimension() != null) {
                    MeasureDimensionType dim = dss.get(i).getDataStructureComponents().getDimensionList().getMeasureDimension();
                    ConceptReference cref = dim.getConceptIdentity();
                    ConceptSchemeReference csref = cref.toConceptSchemeReference();
                    ConceptSchemeType cscheme = null;
                    ConceptType ct = SdmxGatewayApplication.getApplication().getRegistry().find(cref);
                    if (cs.find(csref) != null) {
                        cscheme = cs.find(csref);
                        cscheme.addConcept(ct);
                    } else {
                        cscheme = SdmxGatewayApplication.getApplication().getRegistry().find(csref);
                        cs.getConceptSchemes().add(cscheme);
                        cscheme.setItems(new ArrayList<ItemType>());
                        cscheme.addConcept(ct);
                    }
                    if (dim.getLocalRepresentation().getEnumeration() != null) {
                        CodelistType cl = (CodelistType) SdmxGatewayApplication.getApplication().getRegistry().find(dim.getLocalRepresentation().getEnumeration());
                        cd.getCodelists().add(cl);
                    }
                }
                if (dss.get(i).getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
                    TimeDimensionType dim = dss.get(i).getDataStructureComponents().getDimensionList().getTimeDimension();
                    ConceptReference cref = dim.getConceptIdentity();
                    ConceptSchemeReference csref = cref.toConceptSchemeReference();
                    ConceptSchemeType cscheme = null;
                    ConceptType ct = SdmxGatewayApplication.getApplication().getRegistry().find(cref);
                    if (cs.find(csref) != null) {
                        cscheme = cs.find(csref);
                        cscheme.addConcept(ct);
                    } else {
                        cscheme = SdmxGatewayApplication.getApplication().getRegistry().find(csref);
                        cs.getConceptSchemes().add(cscheme);
                        cscheme.setItems(new ArrayList<ItemType>());
                        cscheme.addConcept(ct);
                    }
                    if (dim.getLocalRepresentation().getEnumeration() != null) {
                        CodelistType cl = (CodelistType) SdmxGatewayApplication.getApplication().getRegistry().find(dim.getLocalRepresentation().getEnumeration());
                        cd.getCodelists().add(cl);
                    }
                }

                for (int j = 0; j < dss.get(i).getDataStructureComponents().getAttributeList().size(); j++) {
                    AttributeType dim = dss.get(i).getDataStructureComponents().getAttributeList().getAttribute(j);
                    ConceptReference cref = dim.getConceptIdentity();
                    ConceptSchemeReference csref = cref.toConceptSchemeReference();
                    ConceptSchemeType cscheme = null;
                    ConceptType ct = SdmxGatewayApplication.getApplication().getRegistry().find(cref);
                    if (cs.find(csref) != null) {
                        cscheme = cs.find(csref);
                        cscheme.addConcept(ct);
                    } else {
                        cscheme = SdmxGatewayApplication.getApplication().getRegistry().find(csref);
                        cs.getConceptSchemes().add(cscheme);
                        cscheme.setItems(new ArrayList<ItemType>());
                        cscheme.addConcept(ct);
                    }
                    if (dim.getLocalRepresentation().getEnumeration() != null) {
                        CodelistType cl = (CodelistType) SdmxGatewayApplication.getApplication().getRegistry().find(dim.getLocalRepresentation().getEnumeration());
                        cd.getCodelists().add(cl);
                    }
                }
                PrimaryMeasure dim = dss.get(i).getDataStructureComponents().getMeasureList().getPrimaryMeasure();
                ConceptReference cref = dim.getConceptIdentity();
                ConceptSchemeReference csref = cref.toConceptSchemeReference();
                ConceptSchemeType cscheme = null;
                ConceptType ct = SdmxGatewayApplication.getApplication().getRegistry().find(cref);
                if (cs.find(csref) != null) {
                    cscheme = cs.find(csref);
                    cscheme.addConcept(ct);
                } else {
                    cscheme = SdmxGatewayApplication.getApplication().getRegistry().find(csref);
                    cs.getConceptSchemes().add(cscheme);
                    cscheme.setItems(new ArrayList<ItemType>());
                    cscheme.addConcept(ct);
                }
                if (dim.getLocalRepresentation().getEnumeration() != null) {
                    CodelistType cl = (CodelistType) SdmxGatewayApplication.getApplication().getRegistry().find(dim.getLocalRepresentation().getEnumeration());
                    cd.getCodelists().add(cl);
                }
            }
            structures.setCodelists(cd);
            structures.setConcepts(cs);
        }

        DataStructuresType dst2 = new DataStructuresType();

        dst2.setDataStructures(dss);

        structures.setDataStructures(dst2);

        struct.setStructures(structures);

        struct.setHeader(getStructureHeader());
        StreamingOutput stream = new StreamingOutput() {
            @Override
            public void write(OutputStream os) throws IOException,
                    WebApplicationException {
                ParseParams params = new ParseParams();
                SdmxIO.writeStructure("application/vnd.sdmx.structure+xml;version=2.1", struct, os);
                os.flush();
                os.close();
            }
        };
        MediaType m = new MediaType("application", "application/vnd.sdmx.structure+xml;version=2.1");

        return Response.ok(stream)
                .type(m).build();
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
        SenderType party = new SenderType();
        party.setId(new IDType("SdmxGateway"));
        ContactType sender = new ContactType();
        sender.setEmails(Collections.singletonList("jsg@internode.on.net"));
        party.setContacts(Collections.singletonList(sender));
        header.setSender(party);
        HeaderTimeType htt = new HeaderTimeType();
        htt.setDate(new sdmx.xml.DateTime(new Date()));
        header.setPrepared(htt);
        return header;
    }
}
