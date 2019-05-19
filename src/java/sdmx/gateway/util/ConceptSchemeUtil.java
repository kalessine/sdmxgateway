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
import sdmx.gateway.entities.ConceptScheme;
import sdmx.structure.base.ItemType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptSchemeType;

/**
 *
 * @author James
 */
public class ConceptSchemeUtil {
    public static sdmx.gateway.entities.ConceptScheme createDatabaseConceptScheme(EntityManager em,ConceptSchemeType c) {
        sdmx.gateway.entities.ConceptScheme cs = new sdmx.gateway.entities.ConceptScheme();
        sdmx.gateway.entities.ConceptSchemePK pk = new sdmx.gateway.entities.ConceptSchemePK();
        pk.setAgencyId(c.getAgencyID().toString());
        pk.setId(c.getId().toString());
        pk.setVersion(c.getVersion().toString());
        cs.setAnnotations(AnnotationsUtil.toDatabaseAnnotations(c.getAnnotations()));
        cs.setConceptSchemePK(pk);
        List<sdmx.gateway.entities.Concept> concepts = new ArrayList<>();
        for (int i = 0; i < c.size(); i++) {
            concepts.add(ConceptUtil.createDatabaseConcept(em,cs,c.getConcept(i)));
        }
        NameUtil.setName(em,cs, c);
        cs.setConceptList(concepts);
        return cs;
    }    

    public static ConceptScheme findDatabaseConceptScheme(EntityManager em, String agency, String id, String version) {
        try{
        Query q = em.createQuery("select c from Conceptscheme c where c.conceptschemePK.agencyID=:agency and c.conceptschemePK.id=:id and c.conceptschemePK.version=:version");
        q.setParameter("agency", agency);
        q.setParameter("id", id);
        q.setParameter("version", version);
        return (sdmx.gateway.entities.ConceptScheme)q.getSingleResult();
        }catch(Exception ex) { return null; }
    }
    public static List<sdmx.gateway.entities.ConceptScheme> searchConceptScheme(EntityManager em, String agency, String id, String version) {
        if ("*".equals(version) && "all".equals(id) && "all".equals(agency)) {
            Query q = em.createQuery("select c from ConceptScheme c");
            return (List<sdmx.gateway.entities.ConceptScheme>) q.getResultList();
        } else if ("all".equals(id) && "all".equals(agency)) {
            Query q = em.createQuery("select c from ConceptScheme c where c.conceptschemePK.version=:version");
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.ConceptScheme>) q.getResultList();
        } else if ("*".equals(version) && "all".equals(id)) {
            Query q = em.createQuery("select c from ConceptScheme c where c.conceptschemePK.agencyID=:agency");
            q.setParameter("agency", agency);
            return (List<sdmx.gateway.entities.ConceptScheme>) q.getResultList();
        } else if ("*".equals(version) && "all".equals(agency)) {
            Query q = em.createQuery("select c from ConceptScheme c where c.conceptschemePK.id=:id");
            q.setParameter("id", id);
            return (List<sdmx.gateway.entities.ConceptScheme>) q.getResultList();
        } else if ("*".equals(version)) {
            Query q = em.createQuery("select c from ConceptScheme c where c.conceptschemePK.agencyID=:agency and c.conceptschemePK.id=:id");
            q.setParameter("agency", agency);
            q.setParameter("id", id);
            return (List<sdmx.gateway.entities.ConceptScheme>) q.getResultList();
        } else if ("all".equals(id)) {
            Query q = em.createQuery("select c from ConceptScheme c where c.conceptschemePK.agencyID=:agency and c.conceptschemePK.version=:version");
            q.setParameter("agency", agency);
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.ConceptScheme>) q.getResultList();
        } else if ("all".equals(agency)) {
            Query q = em.createQuery("select c from ConceptScheme c where c.conceptschemePK.id==:id and c.conceptschemePK.version=:version");
            q.setParameter("id", id);
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.ConceptScheme>) q.getResultList();
        } else {
            Query q = em.createQuery("select c from ConceptScheme c where c.conceptschemePK.agencyID=:agency and c.conceptschemePK.id=:id and c.conceptschemePK.version=:version");
            q.setParameter("agency", agency);
            q.setParameter("id", id);
            q.setParameter("version", version);
            return (List<sdmx.gateway.entities.ConceptScheme>) q.getResultList();
        }
    }
  public static ConceptSchemeType toSDMXConceptSchemeType(sdmx.gateway.entities.ConceptScheme c) {
        ConceptSchemeType cl = new ConceptSchemeType();
        cl.setNames(NameUtil.toSDMXName(c.getName()));
        cl.setAgencyID(new NestedNCNameID(c.getConceptSchemePK().getAgencyId()));
        cl.setId(new NCNameID(c.getConceptSchemePK().getId()));
        cl.setVersion(new Version(c.getConceptSchemePK().getVersion()));
        List<sdmx.gateway.entities.Concept> concepts = c.getConceptList();
        List<ItemType> items = new ArrayList<ItemType>();
        for (Concept cd : concepts) {
            items.add(ConceptUtil.toSDMXConcept(cd));
        }
        cl.setItems(items);
        return cl;
    }

    public static List<ConceptSchemeType> toSDMXConceptSchemeTypes(List<sdmx.gateway.entities.ConceptScheme> cls) {
        List<ConceptSchemeType> result = new ArrayList<>();
        for (sdmx.gateway.entities.ConceptScheme c : cls) {
            result.add(toSDMXConceptSchemeType(c));
        }
        return result;
    }
    public static void stub(ConceptSchemeType cs) {
        cs.setItems(null);
    }
}
