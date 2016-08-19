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
import sdmx.commonreferences.NCNameID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.gateway.entities.Codelist;
import sdmx.gateway.entities.Dataflow;
import sdmx.structure.base.ItemType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.dataflow.DataflowType;

/**
 *
 * @author James
 */
public class DataflowUtil {
    public static sdmx.gateway.entities.Dataflow createDatabaseDataflow(EntityManager em, DataflowType df) {
        sdmx.gateway.entities.Dataflow df1 = new sdmx.gateway.entities.Dataflow();
        sdmx.gateway.entities.DataflowPK pk = new sdmx.gateway.entities.DataflowPK();
        pk.setAgencyID(df.getAgencyID().toString());
        pk.setId(df.getId().toString());
        pk.setVersion(df.getVersion().toString());
        df1.setAnnotations(AnnotationsUtil.toDatabaseAnnotations(df.getAnnotations()));
        df1.setDataflowPK(pk);
        NameUtil.setName(em, df1, df);
        
        df1.setStructure(DataStructureReferenceUtil.toDatabaseDataStructureReference(em, df.getStructure()));
        return df1;
    }

    public static Dataflow findDataflow(EntityManager em, String agency, String id, String version) {
        try {
            Query q = em.createQuery("select d from Dataflow d where d.dataflowPK.agencyID=:agency and d.dataflowPK.id=:id and d.dataflowPK.version=:version");
            q.setParameter("agency", agency);
            q.setParameter("id", id);
            q.setParameter("version", version);
            return (sdmx.gateway.entities.Dataflow) q.getSingleResult();
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }
    }

    public static List<sdmx.gateway.entities.Dataflow> searchDataflow(EntityManager em, String agency, String id, String version) {
        if ("*".equals(version) && "all".equals(id) && "all".equals(agency)) {
            Query q = em.createQuery("select d from Dataflow d");
            return (List<sdmx.gateway.entities.Dataflow>) q.getResultList();
        } else if ("all".equals(id) && "all".equals(agency)) {
            Query q = em.createQuery("select d from Dataflow d where d.dataflowPK.version=:version");
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.Dataflow>) q.getResultList();
        } else if ("*".equals(version) && "all".equals(id)) {
            Query q = em.createQuery("select d from Dataflow d where d.dataflowPK.agency=:agency");
            q.setParameter("agency", agency);
            return (List<sdmx.gateway.entities.Dataflow>) q.getResultList();
        } else if ("*".equals(version) && "all".equals(agency)) {
            Query q = em.createQuery("select d from Dataflow d where d.dataflowPK.id=:id");
            q.setParameter("id", id);
            return (List<sdmx.gateway.entities.Dataflow>) q.getResultList();
        } else if ("*".equals(version)) {
            Query q = em.createQuery("select d from Dataflow d where d.dataflowPK.agency=:agency and d.dataflowPK.id=:id");
            q.setParameter("agency", agency);
            q.setParameter("id", id);
            return (List<sdmx.gateway.entities.Dataflow>) q.getResultList();
        } else if ("all".equals(id)) {
            Query q = em.createQuery("select d from Dataflow d where d.dataflowPK.agencyID=:agency and d.dataflowPK.version=:version");
            q.setParameter("agency", agency);
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.Dataflow>) q.getResultList();
        } else if ("all".equals(agency)) {
            Query q = em.createQuery("select d from Dataflow d where d.dataflowPK.id=:id amd d.dataflowPK.id=:id");
            q.setParameter("id", id);
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.Dataflow>) q.getResultList();
        } else {
            Query q = em.createQuery("select d from Dataflow d where d.dataflowPK.agencyID=:agency and d.dataflowPK.id=:id and d.dataflowPK.version=:version");
            q.setParameter("agency", agency);
            q.setParameter("id", id);
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.Dataflow>) q.getResultList();
        }
    }

    public static DataflowType toSDMXDataflow(sdmx.gateway.entities.Dataflow df) {
        if( df == null ) return null;
        DataflowType df1 = new DataflowType();
        df1.setNames(NameUtil.toSDMXName(df.getName()));
        df1.setAgencyID(new NestedNCNameID(df.getDataflowPK().getAgencyID()));
        df1.setId(new NCNameID(df.getDataflowPK().getId()));
        df1.setVersion(new Version(df.getDataflowPK().getVersion()));
        df1.setStructure(DataStructureReferenceUtil.toSDMXDataStructureReference(df.getStructure()));
        return df1;
    }
}
