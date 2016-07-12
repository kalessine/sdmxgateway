/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.util;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import sdmx.gateway.entities.Concept;
import sdmx.structure.codelist.CodeType;
import sdmx.structure.codelist.CodelistType;

/**
 *
 * @author James
 */
public class CodeUtil {

    public static sdmx.gateway.entities.Code findDatabaseCode(EntityManager em, String agency, String csid, String version, String id) {
        try {
            Query q = em.createQuery("select c from Code c where c.codePK.agencyID=:agency and c.codePK.codelistID=:csid and c.codePK.version=:version and c.codePK.id=:id");
            //System.out.println("Find:" + agency + ":" + csid + ":" + version + ":" + id);
            q.setParameter("agency", agency);
            q.setParameter("csid", csid);
            q.setParameter("version", version);
            q.setParameter("id", id);
            return (sdmx.gateway.entities.Code) q.getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }

    public static sdmx.gateway.entities.Code createDatabaseCode(EntityManager em, sdmx.gateway.entities.Codelist cl, CodeType c) {
        sdmx.gateway.entities.Code code = new sdmx.gateway.entities.Code();
        sdmx.gateway.entities.CodePK pk = new sdmx.gateway.entities.CodePK();
        pk.setAgencyID(cl.getCodelistPK().getAgencyID());
        pk.setCodelistID(cl.getCodelistPK().getId());
        pk.setVersion(cl.getCodelistPK().getVersion());
        pk.setId(c.getId().toString());
        NameUtil.setName(em, code, c);
        code.setCodePK(pk);
        if (c.getParent() != null) {
            code.setParentCode(c.getParent().getId().toString());
        }
        return code;
    }
}
