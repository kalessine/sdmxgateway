/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.vtlconnector;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.ref.Reference;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.function.BiConsumer;
import java.util.function.Consumer;
import java.util.logging.Level;
import java.util.logging.Logger;
import no.ssb.vtl.connectors.Connector;
import no.ssb.vtl.connectors.ConnectorException;
import no.ssb.vtl.model.Component;
import no.ssb.vtl.model.Component.Role;
import no.ssb.vtl.model.DataPoint;
import no.ssb.vtl.model.DataStructure;
import no.ssb.vtl.model.Dataset;
import sdmx.SdmxIO;
import sdmx.common.DataType;
import sdmx.commonreferences.CodelistReference;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.ConceptSchemeReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.ItemSchemeReference;
import sdmx.commonreferences.NCNameID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.ReferenceType;
import sdmx.commonreferences.Version;
import sdmx.cube.Cube;
import sdmx.data.AttachmentLevel;
import sdmx.data.ColumnMapper;
import sdmx.data.flat.FlatObs;
import sdmx.message.StructureType;
import sdmx.structure.CodelistsType;
import sdmx.structure.ConceptsType;
import sdmx.structure.DataStructuresType;
import sdmx.structure.DataflowsType;
import sdmx.structure.StructuresType;
import sdmx.structure.base.RepresentationType;
import sdmx.structure.base.TextFormatType;
import sdmx.structure.codelist.CodeType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.structure.concept.ConceptType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.AttributeType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.structure.datastructure.MeasureDimensionType;
import sdmx.structure.datastructure.PrimaryMeasure;
import sdmx.structure.datastructure.SimpleDataStructureRepresentationType;
import sdmx.structure.datastructure.TimeDimensionType;
import sdmx.version.common.ParseDataCallbackHandler;
import sdmx.version.common.ParseParams;
import sdmx.xml.positiveInteger;

/**
 *
 * @author James
 */
public class VTLFileConnector implements Connector {

    private String agency = "";

    public VTLFileConnector(String agency) {
        this.agency = agency;
    }

    @Override
    public boolean canHandle(String string) {
        return string.startsWith("file:");
    }

    @Override
    public Dataset getDataset(String string) throws ConnectorException {
        return null;
    }

    @Override
    public Dataset putDataset(String string, Dataset ds) throws ConnectorException {
        //String type = string.substring("file:".length(), string.length());
        String type = "";
        String name = string.substring(("file:" + type + "//").length(), string.length());
        System.out.println(name);
        File data = new File("C:\\sdmx\\" + name + "-data.xml");
        File structure = new File("C:\\sdmx\\" + name + "-struct.xml");
        StructureType structDoc = new StructureType();
        structDoc.setStructures(new StructuresType());
        structDoc.getStructures().setDataStructures(new DataStructuresType());
        structDoc.getStructures().setCodelists(new CodelistsType());
        structDoc.getStructures().setConcepts(new ConceptsType());
        structDoc.getStructures().setDataflows(new DataflowsType());
        DataStructureType struct = createDataStructure(ds.getDataStructure(), name, structDoc);
        structDoc.getStructures().getDataStructures().addDataStructure(struct);
        Cube cube = new Cube(struct, structDoc);
        ds.getData().forEach(new Consumer<DataPoint>() {
            public void accept(DataPoint dp) {
                List<FlatObs> flat = toFlatObs(cube.getColumnMapper(), dp, ds.getDataStructure(), name);
                for (int i = 0; i < flat.size(); i++) {
                    cube.putObservation(null, cube.getColumnMapper(), flat.get(i));
                }
            }
        });
        createCodelists(structDoc, struct, cube, ds.getDataStructure());
        createConcepts(structDoc, struct);
        FileOutputStream fos = null;
        FileOutputStream fos2 = null;
        try {
            fos = new FileOutputStream(data);
            fos2 = new FileOutputStream(structure);
            ParseParams params = new ParseParams();
            params.setRegistry(structDoc);
            params.setDataflow(structDoc.getStructures().getDataflows().getDataflow(0));
            ParseDataCallbackHandler out = SdmxIO.openForStreamWriting("application/vnd.sdmx.structurespecificdata+xml;version=2.1", fos, params);
            out.headerParsed(SdmxIO.getBaseHeader());
            cube.writeTo(out.getDataSetWriter());
            out.footerParsed(null);
            out.documentFinished();
            structDoc.setHeader(SdmxIO.getBaseHeader());
            SdmxIO.writeStructure("application/vnd.sdmx.structure+xml;version=2.1", structDoc, fos2);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(VTLFileConnector.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(VTLFileConnector.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                fos.close();
            } catch (IOException ex) {
                Logger.getLogger(VTLFileConnector.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return ds;
    }

    public DataStructureType createDataStructure(DataStructure ds, String name, StructureType structDoc) {
        DataStructureType dst = new DataStructureType();
        dst.setAgencyID(new NestedNCNameID(agency));
        dst.setId(new IDType("DF_" + name));
        Iterator<String> it = ds.getRoles().keySet().iterator();
        List<Component> measures = new ArrayList<Component>();
        Component timeComponent = null;
        while (it.hasNext()) {
            String s = it.next();
            Component c = ds.get(s);
            if (c.getRole() == Role.IDENTIFIER) {
                //if (.equals("TIME") || c.getName().equals("TIME_PERIOD")) {
                //    TimeDimensionType td = new TimeDimensionType();
                //    td.setId(new IDType(c.getName()));
                //    dst.getDataStructureComponents().getDimensionList().setTimeDimension(td);
                //} else {
                    DimensionType dim = new DimensionType();
                    dim.setId(new IDType(c.toString()));
                    dst.getDataStructureComponents().getDimensionList().addDimension(dim);
                //}
            }
            if (c.getRole() == Role.MEASURE) {
                measures.add(c);
            }
            if (c.getRole() == Role.ATTRIBUTE) {
                AttributeType att = new AttributeType();
                att.setId(new IDType(c.toString()));
                dst.getDataStructureComponents().getAttributeList().addAttribute(att);
            }
        }
        if (measures.size() > 1) {
            MeasureDimensionType measure = new MeasureDimensionType();
            measure.setId(new IDType(name + "_MEASURE"));
            dst.getDataStructureComponents().getDimensionList().setMeasureDimension(measure);
            ConceptSchemeType cs = new ConceptSchemeType();
            cs.setAgencyID(new NestedNCNameID(this.agency));
            cs.setId(new NCNameID(name + "_MEASURES"));
            for (int j = 0; j < measures.size(); j++) {
                ConceptType concept = new ConceptType();
                concept.setId(new NCNameID(measures.get(j).toString()));
                cs.addConcept(concept);
            }
            RepresentationType rep = new RepresentationType();
            rep.setEnumeration(ConceptSchemeReference.create(cs.getAgencyID(), cs.getId(), cs.getVersion()));
            measure.setLocalRepresentation(rep);
            structDoc.getStructures().getConcepts().addConceptScheme(cs);
        }

        PrimaryMeasure pm = new PrimaryMeasure();
        pm.setId(new IDType("OBS_VALUE"));
        RepresentationType rep = new RepresentationType();
        TextFormatType tft = new TextFormatType();
        tft.setTextType(DataType.DECIMAL);
        tft.setDecimals(new positiveInteger(6));
        rep.setTextFormat(tft);
        pm.setLocalRepresentation(rep);
        dst.getDataStructureComponents().getMeasureList().setPrimaryMeasure(pm);
        return dst;
    }

    public List<FlatObs> toFlatObs(ColumnMapper mapper, DataPoint dp, DataStructure struct, String name) {
        List<FlatObs> result = new ArrayList<FlatObs>();
        String[] comps = struct.keySet().toArray(new String[]{});
        List<String> comps2 = Arrays.asList(comps);
        List<Component> measures = new ArrayList<Component>();
        struct.values().forEach((component) -> {
            if (component.getRole() == Role.MEASURE) {
                measures.add(component);
            }
        });
        FlatObs obs = new FlatObs(dp.size());
        for (int i = 0; i < dp.size(); i++) {
            if (((Component) struct.get(comps[i])).getRole() == Role.MEASURE) {
                FlatObs obs2 = obs.clone();
                if (!mapper.containsColumn(name + "_MEASURE")) {
                    mapper.registerColumn(name + "_MEASURE", AttachmentLevel.OBSERVATION);
                }
                obs2.setValue(mapper.getColumnIndex(name + "_MEASURE"), String.valueOf(comps[i]));
                if (!mapper.containsColumn("OBS_VALUE")) {
                    mapper.registerColumn("OBS_VALUE", AttachmentLevel.OBSERVATION);
                }
                obs2.setValue(mapper.getColumnIndex("OBS_VALUE"), String.valueOf(dp.get(i).get()));
                    //System.out.println("Index="+mapper.getColumnIndex("OBS_VALUE"));
                //System.out.println("OBS_VALUE:"+String.valueOf(dp.get(comps2.indexOf(measures.get(j).getName())).get()));
                result.add(obs2);
            } else {
                if (!mapper.containsColumn(comps[i])) {
                    mapper.registerColumn(comps[i], AttachmentLevel.OBSERVATION);
                }
                obs.setValue(mapper.getColumnIndex(comps[i]), String.valueOf(dp.get(i).get()));
            }
        }
        if (measures.size() == 1) {
            result.add(obs);
        }
        return result;
    }

    private void createCodelists(StructureType structDoc, DataStructureType struct, Cube cube, DataStructure ds) {
        for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            DimensionType dim = struct.getDataStructureComponents().getDimensionList().getDimension(i);
            CodelistType codeList = new CodelistType();
            codeList.setAgencyID(new NestedNCNameID(this.agency));
            codeList.setId(new NCNameID(dim.getId().toString() + "_CODES"));
            List<String> values = cube.getValues(dim.getId().toString());
            for (int j = 0; j < values.size(); j++) {
                if (values.get(j) != null) {
                    CodeType code = new CodeType();
                    code.setId(new IDType(values.get(j)));
                    codeList.addCode(code);
                }
            }
            RepresentationType rep = new RepresentationType();
            rep.setEnumeration(CodelistReference.create(codeList.getAgencyID(), codeList.getId(), codeList.getVersion()));
            dim.setLocalRepresentation(rep);
            structDoc.getStructures().getCodelists().addCodelist(codeList);
        }
        for (int i = 0; i < struct.getDataStructureComponents().getAttributeList().size(); i++) {
            AttributeType dim = struct.getDataStructureComponents().getAttributeList().getAttribute(i);
            CodelistType codeList = new CodelistType();
            codeList.setAgencyID(new NestedNCNameID(this.agency));
            codeList.setId(new NCNameID(dim.getId().toString() + "_CODES"));
            List<String> values = cube.getValues(dim.getId().toString());
            for (int j = 0; j < values.size(); j++) {
                // null is not an acceptable value
                if (values.get(j) != null) {
                    CodeType code = new CodeType();
                    code.setId(new IDType(values.get(j)));
                    codeList.addCode(code);
                }
            }
            RepresentationType rep = new SimpleDataStructureRepresentationType();
            rep.setEnumeration(CodelistReference.create(codeList.getAgencyID(), codeList.getId(), codeList.getVersion()));
            dim.setLocalRepresentation(rep);
            structDoc.getStructures().getCodelists().addCodelist(codeList);
        }
        if (struct.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            TimeDimensionType dim = struct.getDataStructureComponents().getDimensionList().getTimeDimension();
            CodelistType codeList = new CodelistType();
            codeList.setAgencyID(new NestedNCNameID(this.agency));
            codeList.setId(new NCNameID(dim.getId().toString() + "_CODES"));
            List<String> values = cube.getValues(dim.getId().toString());
            for (int j = 0; j < values.size(); j++) {
                // null is not an acceptable value
                if (values.get(j) != null) {
                    CodeType code = new CodeType();
                    code.setId(new IDType(values.get(j)));
                    codeList.addCode(code);
                }
            }
            RepresentationType rep = new SimpleDataStructureRepresentationType();
            // XMLBeans doesn't like this next line
            //rep.setEnumeration(CodelistReference.create(codeList.getAgencyID(), codeList.getId(), codeList.getVersion()));
            TextFormatType tft = new TextFormatType();
            tft.setTextType(DataType.OBSERVATIONAL_TIMEPERIOD);
            rep.setTextFormat(tft);
            dim.setLocalRepresentation(rep);
            structDoc.getStructures().getCodelists().addCodelist(codeList);
        }
        if (struct.getDataStructureComponents().getDimensionList().getMeasureDimension() != null) {
            MeasureDimensionType dim = struct.getDataStructureComponents().getDimensionList().getMeasureDimension();
        }

    }

    private void createConcepts(StructureType structDoc, DataStructureType struct) {
        DataflowType df = new DataflowType();
        df.setAgencyID(new NestedNCNameID(agency));
        df.setId(new NCNameID("DF_" + struct.getId().toString()));
        df.setStructure(struct.asReference());
        structDoc.getStructures().getDataflows().addDataflow(df);

        ConceptSchemeType cs = new ConceptSchemeType();
        cs.setAgencyID(new NestedNCNameID(this.agency));
        cs.setId(new IDType(struct.getId().toString() + "_CONCEPTS"));
        for (int i = 0; i < struct.getDataStructureComponents().getDimensionList().size(); i++) {
            DimensionType dim = struct.getDataStructureComponents().getDimensionList().getDimension(i);
            ConceptType concept = new ConceptType();
            concept.setId(new NCNameID(dim.getId().toString()));
            cs.addConcept(concept);
            dim.setConceptIdentity(ConceptReference.create(cs.getAgencyID(), cs.getId(), cs.getVersion(), concept.getId()));
        }
        if(struct.getDataStructureComponents().getDimensionList().getTimeDimension()!=null) {
            TimeDimensionType dim = struct.getDataStructureComponents().getDimensionList().getTimeDimension();
            ConceptType concept = new ConceptType();
            concept.setId(new NCNameID(dim.getId().toString()));
            cs.addConcept(concept);
            dim.setConceptIdentity(ConceptReference.create(cs.getAgencyID(), cs.getId(), cs.getVersion(), concept.getId()));
        }
        for (int i = 0; i < struct.getDataStructureComponents().getAttributeList().size(); i++) {
            AttributeType dim = struct.getDataStructureComponents().getAttributeList().getAttribute(i);
            ConceptType concept = new ConceptType();
            concept.setId(new NCNameID(dim.getId().toString()));
            cs.addConcept(concept);
            dim.setConceptIdentity(ConceptReference.create(cs.getAgencyID(), cs.getId(), cs.getVersion(), concept.getId()));
        }
        MeasureDimensionType dim = struct.getDataStructureComponents().getDimensionList().getMeasureDimension();
        if (dim == null) {
            return;
        }
        ConceptType concept = new ConceptType();
        concept.setId(new NCNameID(dim.getId().toString()));
        cs.addConcept(concept);
        dim.setConceptIdentity(ConceptReference.create(cs.getAgencyID(), cs.getId(), cs.getVersion(), concept.getId()));
        cs.addConcept(concept);
        ConceptType ov = new ConceptType();
        ov.setId(new NCNameID("OBS_VALUE"));
        struct.getDataStructureComponents().getMeasureList().getPrimaryMeasure().setConceptIdentity(ConceptReference.create(cs.getAgencyID(), cs.getId(), cs.getVersion(), ov.getId()));
        cs.addConcept(ov);
        structDoc.getStructures().getConcepts().addConceptScheme(cs);
    }
}
