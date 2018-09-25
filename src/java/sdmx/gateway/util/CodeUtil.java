/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.util;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.LocalCodeRef;
import sdmx.commonreferences.LocalItemReference;
import sdmx.commonreferences.types.ItemSchemePackageTypeCodelistType;
import sdmx.commonreferences.types.ItemTypeCodelistType;
import sdmx.commonreferences.types.ObjectTypeCodelistType;
import sdmx.commonreferences.types.PackageTypeCodelistType;
import sdmx.gateway.entities.Code;
import sdmx.gateway.entities.Concept;
import sdmx.structure.base.ItemType;
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
        pk.setId(cl.getCodelistPK().getId());
        pk.setVersion(cl.getCodelistPK().getVersion());
        pk.setCodeId(c.getId().toString());
        NameUtil.setName(em, code, c);
        code.setCodePK(pk);
        if (c.getParent() != null) {
            code.setParentCode(c.getParent().getId().toString());
        }
        return code;
    }

    public static CodeType toSDMXCode(Code c) {
        CodeType cd = new CodeType();
        cd.setAnnotations(AnnotationsUtil.toSDMXAnnotations(c.getAnnotations()));
        cd.setNames(NameUtil.toSDMXName(c.getName()));
        cd.setId(new IDType(c.getCodePK().getCodeId()));
        if (c.getParentCode() != null) {
            sdmx.commonreferences.LocalCodeRef ref = new LocalCodeRef(new IDType(c.getParentCode()), ItemTypeCodelistType.CODE, ItemSchemePackageTypeCodelistType.CODELIST);
            cd.setParent(new LocalItemReference(ref));
        }
        return cd;
    }
}
