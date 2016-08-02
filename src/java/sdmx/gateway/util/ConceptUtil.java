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
import sdmx.commonreferences.LocalCodeRef;
import sdmx.commonreferences.LocalItemReference;
import sdmx.commonreferences.NCNameID;
import sdmx.commonreferences.types.ItemSchemePackageTypeCodelistType;
import sdmx.commonreferences.types.ItemTypeCodelistType;
import sdmx.gateway.entities.Code;
import sdmx.gateway.entities.Concept;
import sdmx.gateway.entities.Conceptscheme;
import sdmx.structure.base.ItemType;
import sdmx.structure.codelist.CodeType;
import sdmx.structure.concept.ConceptType;

/**
 *
 * @author James
 */
public class ConceptUtil {
    public static Concept findDatabaseConcept(EntityManager em,String agency,String csid, String version,String id) {
        try{
        Query q = em.createQuery("select c from Concept c where c.conceptPK.conceptSchemeAgencyID=:agency and c.conceptPK.conceptSchemeID=:csid and c.conceptPK.conceptSchemeVersion=:version and c.conceptPK.id=:id");
        //System.out.println("Find:"+agency+":"+csid+":"+version+":"+id);
        q.setParameter("agency", agency);
        q.setParameter("csid", csid);
        q.setParameter("version", version);
        q.setParameter("id", id);
        return (sdmx.gateway.entities.Concept)q.getSingleResult();
        }catch(Exception e) { 
            return null; }
    }
    public static Concept createDatabaseConcept(EntityManager em, Conceptscheme cs, ConceptType c) {
        sdmx.gateway.entities.Concept ct = new sdmx.gateway.entities.Concept();
        sdmx.gateway.entities.ConceptPK pk = new sdmx.gateway.entities.ConceptPK();
        pk.setConceptSchemeAgencyID(cs.getConceptschemePK().getAgencyID());
        pk.setConceptSchemeID(cs.getConceptschemePK().getId());
        pk.setConceptSchemeVersion(cs.getConceptschemePK().getVersion());
        pk.setId(c.getId().toString());
        ct.setAnnotations(AnnotationsUtil.toDatabaseAnnotations(c.getAnnotations()));
        ct.setConceptPK(pk);
        List<sdmx.gateway.entities.Concept> concepts = new ArrayList<>();
        for (int i = 0; i < c.size(); i++) {
            concepts.add(ConceptUtil.createDatabaseConcept(em,cs,c.getConcept(i)));
        }
        NameUtil.setName(em,ct, c);
        return ct;
    }

    public static ConceptType toSDMXConcept(Concept c) {
        ConceptType cd = new ConceptType();
        cd.setAnnotations(AnnotationsUtil.toSDMXAnnotations(c.getAnnotations()));
        cd.setNames(NameUtil.toSDMXName(c.getName()));
        cd.setId(new NCNameID(c.getConceptPK().getId()));
        return cd;
    }
}
