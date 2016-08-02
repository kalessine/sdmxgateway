/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.util;

import javax.persistence.EntityManager;
import sdmx.commonreferences.ConceptReference;
import sdmx.gateway.entities.Conceptreference;

/**
 *
 * @author James
 */
public class ConceptReferenceUtil {

    public static Conceptreference toDatabaseConceptreference(EntityManager em, ConceptReference conceptIdentity) {
        sdmx.gateway.entities.Conceptreference ref = new sdmx.gateway.entities.Conceptreference();
        sdmx.gateway.entities.Concept c = ConceptUtil.findDatabaseConcept(em, conceptIdentity.getAgencyId().toString(), conceptIdentity.getMaintainableParentId().toString(), conceptIdentity.getVersion().toString(), conceptIdentity.getId().toString());
        if (c != null) {
            ref.setConcept(c);
            em.persist(ref);
            em.flush();
            em.refresh(ref);
            return ref;
        } else {
            System.out.println("target concept is null!!!"+conceptIdentity.getAgencyId()+":"+conceptIdentity.getId()+":"+conceptIdentity.getVersion());
            conceptIdentity.dump();
            return null;
        }

    }

}
