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
import sdmx.gateway.entities.Concept;
import sdmx.gateway.entities.Conceptscheme;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptSchemeType;

/**
 *
 * @author James
 */
public class ConceptSchemeUtil {
    public static sdmx.gateway.entities.Conceptscheme createDatabaseConceptScheme(EntityManager em,ConceptSchemeType c) {
        sdmx.gateway.entities.Conceptscheme cs = new sdmx.gateway.entities.Conceptscheme();
        sdmx.gateway.entities.ConceptschemePK pk = new sdmx.gateway.entities.ConceptschemePK();
        pk.setAgencyID(c.getAgencyID().toString());
        pk.setId(c.getId().toString());
        pk.setVersion(c.getVersion().toString());
        cs.setAnnotations(AnnotationsUtil.toDatabaseAnnotations(c.getAnnotations()));
        cs.setConceptschemePK(pk);
        List<sdmx.gateway.entities.Concept> concepts = new ArrayList<>();
        for (int i = 0; i < c.size(); i++) {
            concepts.add(ConceptUtil.createDatabaseConcept(em,cs,c.getConcept(i)));
        }
        NameUtil.setName(em,cs, c);
        cs.setConceptList(concepts);
        return cs;
    }    

    public static Conceptscheme findDatabaseConceptScheme(EntityManager em, String agency, String id, String version) {
        try{
        Query q = em.createQuery("select c from Conceptscheme c where c.conceptschemePK.agencyID=:agency and c.conceptschemePK.id=:id and c.conceptschemePK.version=:version");
        q.setParameter("agency", agency);
        q.setParameter("id", id);
        q.setParameter("version", version);
        return (sdmx.gateway.entities.Conceptscheme)q.getSingleResult();
        }catch(Exception ex) { return null; }
    }
}
