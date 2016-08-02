/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.util;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.structure.datastructure.DataStructureType;

/**
 *
 * @author James
 */
public class DataStructureUtil {

    public static sdmx.gateway.entities.Datastructure createDatabaseDataStructure(EntityManager em, DataStructureType ds) {
        sdmx.gateway.entities.Datastructure ds2 = new sdmx.gateway.entities.Datastructure();
        sdmx.gateway.entities.DatastructurePK pk = new sdmx.gateway.entities.DatastructurePK();
        pk.setAgencyID(ds.getAgencyID().toString());
        pk.setId(ds.getId().toString());
        if ("".equals(ds.getVersion().toString())) {
            pk.setVersion("1.0");
        } else {
            pk.setVersion(ds.getVersion().toString());
        }
        ds2.setDatastructurePK(pk);
        ds2.setAnnotations(AnnotationsUtil.toDatabaseAnnotations(ds.getAnnotations()));
        NameUtil.setName(em, ds2, ds);
        List<sdmx.gateway.entities.Datastructurecomponents> comps = new ArrayList<>();
        int position = 0;
        for (int i = 0; i < ds.getDataStructureComponents().getDimensionList().getDimensions().size(); i++) {
            comps.add(DimensionUtil.toDatabaseDimension(em, ds, ds.getDataStructureComponents().getDimensionList().getDimensions().get(i), position++));
        }
        if (ds.getDataStructureComponents().getDimensionList().getTimeDimension() != null) {
            comps.add(DimensionUtil.toDatabaseDimension(em, ds, ds.getDataStructureComponents().getDimensionList().getTimeDimension(), position++));
        }
        if (ds.getDataStructureComponents().getDimensionList().getMeasureDimension() != null) {
            comps.add(DimensionUtil.toDatabaseDimension(em, ds, ds.getDataStructureComponents().getDimensionList().getMeasureDimension(), position++));
        }
        for (int i = 0; i < ds.getDataStructureComponents().getAttributeList().size(); i++) {
            comps.add(DimensionUtil.toDatabaseDimension(em, ds, ds.getDataStructureComponents().getAttributeList().getAttribute(i), position++));
        }
        comps.add(DimensionUtil.toDatabaseDimension(em, ds, ds.getDataStructureComponents().getMeasureList().getPrimaryMeasure(), position++));
        ds2.setDatastructurecomponentsList(comps);
        ds2.setDatastructurePK(pk);
        return ds2;
    }

    public static sdmx.gateway.entities.Datastructure findDataStructure(EntityManager em, String agency, String id, String version) {
        try {
            Query q = em.createQuery("select d from Datastructure d where d.datastructurePK.agencyID=:agency and d.datastructurePK.id=:id and d.datastructurePK.version=:version");
            q.setParameter("agency", agency);
            q.setParameter("id", id);
            q.setParameter("version", version);
            return (sdmx.gateway.entities.Datastructure) q.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static List<sdmx.gateway.entities.Datastructure> searchDataStructure(EntityManager em, String agency, String id, String version) {
        if ("*".equals(version) && "all".equals(id) && "all".equals(agency)) {
            Query q = em.createQuery("select d from Datastructure");
            return (List<sdmx.gateway.entities.Datastructure>) q.getResultList();
        } else if ("all".equals(id) && "all".equals(agency)) {
            Query q = em.createQuery("select d from Datastructure d where d.datastructurePK.version=:version");
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.Datastructure>) q.getResultList();
        } else if ("*".equals(version) && "all".equals(id)) {
            Query q = em.createQuery("select d from Datastructure d where d.datastructurePK.agencyID=:agency");
            q.setParameter("agency", agency);
            return (List<sdmx.gateway.entities.Datastructure>) q.getResultList();
        } else if ("*".equals(version) && "all".equals(agency)) {
            Query q = em.createQuery("select d from Datastructure d where d.datastructurePK.id=:id");
            q.setParameter("id", id);
            return (List<sdmx.gateway.entities.Datastructure>) q.getResultList();
        } else if ("*".equals(version)) {
            Query q = em.createQuery("select d from Datastructure d where d.datastructurePK.id=:id and d.datastructurePK.agencyID=:agency");
            q.setParameter("agency", agency);
            q.setParameter("id", id);
            return (List<sdmx.gateway.entities.Datastructure>) q.getResultList();
        } else if ("all".equals(id)) {
            Query q = em.createQuery("select d from Datastructure d where d.datastructurePK.version=:version and d.datastructurePK.agencyID=:agency");
            q.setParameter("agency", agency);
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.Datastructure>) q.getResultList();
        } else if ("all".equals(agency)) {
            Query q = em.createQuery("select d from Datastructure d where d.datastructurePK.id=:id and d.datastructurePK.version=:version");
            q.setParameter("id", id);
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.Datastructure>) q.getResultList();
        } else {
            Query q = em.createQuery("select d from Datastructure d where d.datastructurePK.agencyID=:agency and d.datastructurePK.id=:id and d.datastructurePK.version=:version");
            q.setParameter("agency", agency);
            q.setParameter("id", id);
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.Datastructure>) q.getResultList();
        }
    }
    public static DataStructureType toSDMXDataStructure(sdmx.gateway.entities.Datastructure d) {
        DataStructureType struct = new DataStructureType();
        struct.setAgencyID(new NestedNCNameID(d.getDatastructurePK().getAgencyID()));
        struct.setId(new IDType(d.getDatastructurePK().getId()));
        struct.setVersion(new Version(d.getDatastructurePK().getVersion()));
        return struct;
    }
}
