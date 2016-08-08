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
import sdmx.commonreferences.NCNameID;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.gateway.entities.Codelist;
import sdmx.structure.base.ItemType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.datastructure.DataStructureType;

/**
 *
 * @author James
 */
public class CodelistUtil {

    public static sdmx.gateway.entities.Codelist createDatabaseCodelist(EntityManager em, CodelistType c) {
        sdmx.gateway.entities.Codelist cl = new sdmx.gateway.entities.Codelist();
        sdmx.gateway.entities.CodelistPK pk = new sdmx.gateway.entities.CodelistPK();
        pk.setAgencyID(c.getAgencyID().toString());
        pk.setId(c.getId().toString());
        pk.setVersion(c.getVersion().toString());
        cl.setAnnotations(AnnotationsUtil.toDatabaseAnnotations(c.getAnnotations()));
        cl.setCodelistPK(pk);
        List<sdmx.gateway.entities.Code> codes = new ArrayList<>();
        for (int i = 0; i < c.size(); i++) {
            codes.add(CodeUtil.createDatabaseCode(em, cl, c.getCode(i)));
        }
        NameUtil.setName(em, cl, c);
        cl.setCodeList(codes);
        return cl;
    }

    public static Codelist findDatabaseCodelist(EntityManager em, String agency, String id, String version) {
        try {
            Query q = em.createQuery("select c from Codelist c where c.codelistPK.agencyID=:agency and c.codelistPK.id=:id and c.codelistPK.version=:version");
            q.setParameter("agency", agency);
            q.setParameter("id", id);
            q.setParameter("version", version);
            return (sdmx.gateway.entities.Codelist) q.getSingleResult();
        } catch (Exception ex) {
            return null;
        }
    }

    public static List<sdmx.gateway.entities.Codelist> searchCodelist(EntityManager em, String agency, String id, String version) {
        if ("*".equals(version) && "all".equals(id) && "all".equals(agency)) {
            Query q = em.createQuery("select c from Codelist c");
            return (List<sdmx.gateway.entities.Codelist>) q.getResultList();
        } else if ("all".equals(id) && "all".equals(agency)) {
            Query q = em.createQuery("select c from Codelist c where c.codelistPK.version=:version");
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.Codelist>) q.getResultList();
        } else if ("*".equals(version) && "all".equals(id)) {
            Query q = em.createQuery("select c from Codelist c where c.codelistPK.agencyID=:agency");
            q.setParameter("agency", agency);
            return (List<sdmx.gateway.entities.Codelist>) q.getResultList();
        } else if ("*".equals(version) && "all".equals(agency)) {
            Query q = em.createQuery("select c from Codelist c where c.codelistPK.id=:id");
            q.setParameter("id", id);
            return (List<sdmx.gateway.entities.Codelist>) q.getResultList();
        } else if ("*".equals(version)) {
            Query q = em.createQuery("select c from Codelist c where c.codelistPK.agencyID=:agency and c.codelistPK.id=:id");
            q.setParameter("agency", agency);
            q.setParameter("id", id);
            return (List<sdmx.gateway.entities.Codelist>) q.getResultList();
        } else if ("all".equals(id)) {
            Query q = em.createQuery("select c from Codelist c where c.codelistPK.agencyID=:agency and c.codelistPK.version=:version");
            q.setParameter("agency", agency);
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.Codelist>) q.getResultList();
        } else if ("all".equals(agency)) {
            Query q = em.createQuery("select c from Codelist c where c.codelistPK.id=:id and c.codelistPK.version=:version");
            q.setParameter("id", id);
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.Codelist>) q.getResultList();
        } else {
            Query q = em.createQuery("select c from Codelist c where c.codelistPK.agencyID=:agency and c.codelistPK.id=:id and c.codelistPK.version=:version");
            q.setParameter("agency", agency);
            q.setParameter("id", id);
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.Codelist>) q.getResultList();
        }
    }

    public static CodelistType toSDMXCodelist(sdmx.gateway.entities.Codelist c) {
        CodelistType cl = new CodelistType();
        cl.setNames(NameUtil.toSDMXName(c.getName()));
        cl.setAgencyID(new NestedNCNameID(c.getCodelistPK().getAgencyID()));
        cl.setId(new NCNameID(c.getCodelistPK().getId()));
        cl.setVersion(new Version(c.getCodelistPK().getVersion()));
        List<sdmx.gateway.entities.Code> codes = c.getCodeList();
        List<ItemType> items = new ArrayList<ItemType>();
        for (sdmx.gateway.entities.Code cd : codes) {
            items.add(CodeUtil.toSDMXCode(cd));
        }
        cl.setItems(items);
        return cl;
    }

    public static List<CodelistType> toSDMXCodelist(List<sdmx.gateway.entities.Codelist> cls) {
        List<CodelistType> result = new ArrayList<>();
        for (sdmx.gateway.entities.Codelist c : cls) {
            result.add(toSDMXCodelist(c));
        }
        return result;
    }
    public static void stub(CodelistType cl) {
        cl.setItems(null);
    
    }
}
