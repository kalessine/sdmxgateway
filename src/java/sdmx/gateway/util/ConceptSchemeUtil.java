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
import sdmx.gateway.entities.Concept;
import sdmx.gateway.entities.Conceptscheme;
import sdmx.structure.base.ItemType;
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
    public static List<sdmx.gateway.entities.Conceptscheme> searchConceptScheme(EntityManager em, String agency, String id, String version) {
        if ("*".equals(version) && "all".equals(id) && "all".equals(agency)) {
            Query q = em.createQuery("select c from Conceptscheme c");
            return (List<sdmx.gateway.entities.Conceptscheme>) q.getResultList();
        } else if ("all".equals(id) && "all".equals(agency)) {
            Query q = em.createQuery("select c from Conceptscheme c where c.conceptschemePK.version=:version");
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.Conceptscheme>) q.getResultList();
        } else if ("*".equals(version) && "all".equals(id)) {
            Query q = em.createQuery("select c from Conceptscheme c where c.conceptschemePK.agencyID=:agency");
            q.setParameter("agency", agency);
            return (List<sdmx.gateway.entities.Conceptscheme>) q.getResultList();
        } else if ("*".equals(version) && "all".equals(agency)) {
            Query q = em.createQuery("select c from Conceptscheme c where c.conceptschemePK.id=:id");
            q.setParameter("id", id);
            return (List<sdmx.gateway.entities.Conceptscheme>) q.getResultList();
        } else if ("*".equals(version)) {
            Query q = em.createQuery("select c from Conceptscheme c where c.conceptschemePK.agencyID=:agency and c.conceptschemePK.id=:id");
            q.setParameter("agency", agency);
            q.setParameter("id", id);
            return (List<sdmx.gateway.entities.Conceptscheme>) q.getResultList();
        } else if ("all".equals(id)) {
            Query q = em.createQuery("select c from Conceptscheme c where c.conceptschemePK.agencyID=:agency and c.conceptschemePK.version=:version");
            q.setParameter("agency", agency);
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.Conceptscheme>) q.getResultList();
        } else if ("all".equals(agency)) {
            Query q = em.createQuery("select c from Conceptscheme c where c.conceptschemePK.id==:id and c.conceptschemePK.version=:version");
            q.setParameter("id", id);
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.Conceptscheme>) q.getResultList();
        } else {
            Query q = em.createQuery("select c from Conceptscheme c where c.conceptschemePK.agencyID=:agency and c.conceptschemePK.id=:id and c.conceptschemePK.version=:version");
            q.setParameter("agency", agency);
            q.setParameter("id", id);
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.Conceptscheme>) q.getResultList();
        }
    }
  public static ConceptSchemeType toSDMXConceptSchemeType(sdmx.gateway.entities.Conceptscheme c) {
        ConceptSchemeType cl = new ConceptSchemeType();
        cl.setNames(NameUtil.toSDMXName(c.getName()));
        cl.setAgencyID(new NestedNCNameID(c.getConceptschemePK().getAgencyID()));
        cl.setId(new NCNameID(c.getConceptschemePK().getId()));
        cl.setVersion(new Version(c.getConceptschemePK().getVersion()));
        List<sdmx.gateway.entities.Concept> concepts = c.getConceptList();
        List<ItemType> items = new ArrayList<ItemType>();
        for (Concept cd : concepts) {
            items.add(ConceptUtil.toSDMXConcept(cd));
        }
        cl.setItems(items);
        return cl;
    }

    public static List<ConceptSchemeType> toSDMXConceptSchemeTypes(List<sdmx.gateway.entities.Conceptscheme> cls) {
        List<ConceptSchemeType> result = new ArrayList<>();
        for (sdmx.gateway.entities.Conceptscheme c : cls) {
            result.add(toSDMXConceptSchemeType(c));
        }
        return result;
    }
}
