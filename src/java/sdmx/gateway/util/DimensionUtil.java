/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.util;

import java.math.BigInteger;
import javax.persistence.EntityManager;
import sdmx.gateway.entities.MeasureStructureComponent;
import sdmx.structure.base.Component;
import sdmx.structure.base.RepresentationType;
import sdmx.structure.datastructure.AttributeType;
import sdmx.structure.datastructure.DataStructureType;
import sdmx.structure.datastructure.DimensionType;
import sdmx.structure.datastructure.MeasureDimensionType;
import sdmx.structure.datastructure.PrimaryMeasure;
import sdmx.structure.datastructure.SimpleDataStructureRepresentationType;
import sdmx.structure.datastructure.TimeDimensionType;

/**
 *
 * @author James
 */
public class DimensionUtil {

    public static final int TYPE_DIMENSION = 0;
    public static final int TYPE_TIMEDIMENSION = 1;
    public static final int TYPE_MEASURE = 2;
    public static final int TYPE_PRIMARYMEASURE = 3;
    public static final int TYPE_ATTRIBUTE = 4;

    public static sdmx.gateway.entities.MeasureStructureComponent toDatabaseMeasureDimension(EntityManager em, DataStructureType struct, MeasureDimension c, int position) {
            MeasureDimensionType dim = (MeasureDimensionType) c;
            sdmx.gateway.entities.MeasureStructureComponent dsc = new sdmx.gateway.entities.MeasureStructureComponent();
            sdmx.gateway.entities.MeasureStructureComponentPK pk = new sdmx.gateway.entities.MeasureStructureComponentPK();
            pk.setAgencyID(struct.getAgencyID().toString());
            pk.setId(struct.getId().toString());
            pk.setVersion(struct.getVersion().toString());
            pk.setComponentId(c.getId().toString());
            dsc.setMeasureStructureComponentPK(pk);
            dsc.setConceptIdentity(BigInteger.valueOf(ConceptReferenceUtil.toDatabaseConceptReference(em, c.getConceptIdentity()).getReference()));
            RepresentationType rep = dim.getLocalRepresentation();
            if (rep == null) {
                // Get Concept Representation
            }
            if (rep != null && rep.getEnumeration() != null) {
                dsc.setEnumeration(ConceptSchemeReferenceUtil.toDatabaseConceptSchemeReference(em, rep.getEnumeration().asConceptSchemeReference()));
            }
            return dsc;
    }
    public static sdmx.gateway.entities.TimeStructureComponent toDatabaseMeasureDimension(EntityManager em, DataStructureType struct, TimeDimension dim, int position) {
            sdmx.gateway.entities.TimeStructureComponent dsc = new sdmx.gateway.entities.TimeStructureComponent();
            sdmx.gateway.entities.TimeStructureComponentPK pk = new sdmx.gateway.entities.TimeStructureComponentPK();
            pk.setAgencyID(struct.getAgencyID().toString());
            pk.setId(struct.getId().toString());
            pk.setVersion(struct.getVersion().toString());
            pk.setComponentId(dim.getId().toString());
            dsc.setTimeStructureComponentPK(pk);
            dsc.setConceptIdentity(BigInteger.valueOf(ConceptReferenceUtil.toDatabaseConceptReference(em, dim.getConceptIdentity()).getReference()));
            RepresentationType rep = dim.getLocalRepresentation();
            if (rep == null) {
                // Get Concept Representation
            }
            if (rep != null && rep.getEnumeration() != null) {
                dsc.setEnumeration(CodelistReferenceUtil.toDatabaseCodelistReference(em, rep.getEnumeration().asCodelistReference()));
            }
            return dsc;
    }
    public static sdmx.gateway.entities.PrimaryMeasureStructureComponent toDatabaseMeasureDimension(EntityManager em, DataStructureType struct, PrimaryMeasure dim, int position) {
            sdmx.gateway.entities.PrimaryMeasureStructureComponent dsc = new sdmx.gateway.entities.PrimaryMeasureStructureComponent();
            sdmx.gateway.entities.PrimaryMeasureStructureComponentPK pk = new sdmx.gateway.entities.PrimaryMeasureStructureComponentPK();
            pk.setAgencyID(struct.getAgencyID().toString());
            pk.setId(struct.getId().toString());
            pk.setVersion(struct.getVersion().toString());
            pk.setComponentId(dim.getId().toString());
            dsc.setPrimaryMeasureStructureComponentPK(pk);
            dsc.setConceptIdentity(BigInteger.valueOf(ConceptReferenceUtil.toDatabaseConceptReference(em, dim.getConceptIdentity()).getReference()));
            RepresentationType rep = dim.getLocalRepresentation();
            if (rep == null) {
                // Get Concept Representation
            }
            if (rep != null && rep.getEnumeration() != null) {
            }
            return dsc;
    }
    public static sdmx.gateway.entities.DimensionStructureComponent toDatabaseDimension(EntityManager em, DataStructureType struct, DimensionType dim, int position) {
            sdmx.gateway.entities.DimensionStructureComponent dsc = new sdmx.gateway.entities.DimensionStructureComponent();
            sdmx.gateway.entities.DimensionStructureComponentPK pk = new sdmx.gateway.entities.DimensionStructureComponentPK();
            pk.setAgencyID(struct.getAgencyID().toString());
            pk.setId(struct.getId().toString());
            pk.setVersion(struct.getVersion().toString());
            pk.setComponentId(dim.getId().toString());
            dsc.setDimensionStructureComponentPK(pk);
            dsc.setConceptIdentity(BigInteger.valueOf(ConceptReferenceUtil.toDatabaseConceptReference(em, dim.getConceptIdentity()).getReference()));
            RepresentationType rep = dim.getLocalRepresentation();
            if (rep == null) {
                // Get Concept Representation
            }
            if (rep != null && rep.getEnumeration() != null) {
            }
            return dsc;
    }
    public static sdmx.gateway.entities.AttributeStructureComponent toDatabaseDimension(EntityManager em, DataStructureType struct, AttributeType dim, int position) {
            sdmx.gateway.entities.AttributeStructureComponent dsc = new sdmx.gateway.entities.AttributeStructureComponent();
            sdmx.gateway.entities.AttributeStructureComponentPK pk = new sdmx.gateway.entities.AttributeStructureComponentPK();
            pk.setAgencyID(struct.getAgencyID().toString());
            pk.setId(struct.getId().toString());
            pk.setVersion(struct.getVersion().toString());
            pk.setComponentId(dim.getId().toString());
            dsc.setAttributeStructureComponentPK(pk);
            dsc.setConceptIdentity(BigInteger.valueOf(ConceptReferenceUtil.toDatabaseConceptReference(em, dim.getConceptIdentity()).getReference()));
            RepresentationType rep = dim.getLocalRepresentation();
            if (rep == null) {
                // Get Concept Representation
            }
            if (rep != null && rep.getEnumeration() != null) {
            }
            return dsc;
    }
    
    public static Component toSDMXDimension(sdmx.gateway.entities.DataStructureComponent dc) {
        Component comp = null;
        switch (dc.getType()) {
            case DimensionUtil.TYPE_DIMENSION:
                comp = new DimensionType();
                break;
            case DimensionUtil.TYPE_ATTRIBUTE:
                comp = new AttributeType();
                break;
            case DimensionUtil.TYPE_TIMEDIMENSION:
                comp = new TimeDimensionType();
                break;
            case DimensionUtil.TYPE_MEASURE:
                comp = new MeasureDimensionType();
                break;
            case DimensionUtil.TYPE_PRIMARYMEASURE:
                comp = new PrimaryMeasure();
                break;
        }
        comp.setConceptIdentity(ConceptReferenceUtil.toSDMXReference(dc.getConceptIdentity()));
        RepresentationType lr = new SimpleDataStructureRepresentationType();
        if (dc.getCodelistEnumeration() != null) {
            lr.setEnumeration(CodelistReferenceUtil.toSDMXCodelistReference(dc.getCodelistEnumeration()));
        }
        if (dc.getConceptSchemeEnumeration() != null) {
            lr.setEnumeration(ConceptSchemeReferenceUtil.toSDMXConceptSchemeReference(dc.getConceptSchemeEnumeration()));
        }
        comp.setLocalRepresentation(lr);
        return comp;
    }
}
