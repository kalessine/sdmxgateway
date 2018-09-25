/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.util;

import javax.persistence.EntityManager;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;

/**
 *
 * @author James
 */
public class ConceptReferenceUtil {

    public static sdmx.gateway.entities.ConceptReference toDatabaseConceptReference(EntityManager em, ConceptReference conceptIdentity) {
        sdmx.gateway.entities.ConceptReference ref = new sdmx.gateway.entities.ConceptReference();
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
    public static ConceptReference toSDMXReference(sdmx.gateway.entities.ConceptReference con) {
        return ConceptReference.create(new NestedNCNameID(con.getConcept().getConceptPK().getAgencyID()), new IDType(con.getConcept().getConceptPK().getId()), new Version(con.getConcept().getConceptPK().getVersion()), new IDType(con.getConcept().getConceptPK().getConceptID()));
    }

}
