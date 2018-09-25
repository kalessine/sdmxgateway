/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.util;

import javax.persistence.EntityManager;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.ItemSchemeReferenceBase;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.gateway.entities.ConceptReference;
import sdmx.gateway.entities.ConceptSchemeReference;

/**
 *
 * @author James
 */
public class ConceptSchemeReferenceUtil {

    public static ConceptSchemeReference toDatabaseConceptSchemeReference(EntityManager em, sdmx.commonreferences.ConceptSchemeReference conceptIdentity) {
        sdmx.gateway.entities.ConceptSchemeReference ref = new sdmx.gateway.entities.ConceptSchemeReference();
        sdmx.gateway.entities.ConceptScheme c = ConceptSchemeUtil.findDatabaseConceptScheme(em, conceptIdentity.getAgencyId().toString(), conceptIdentity.getMaintainableParentId().toString(), conceptIdentity.getVersion().toString());
        if (c != null) {
            ref.setConceptScheme(c);
            c.getConceptSchemeReferenceList().add(ref);
            em.persist(ref);
            em.flush();
            em.refresh(ref);
            return ref;
        } else {
            System.out.println("Concept scheme doesn't exist!!!");
            conceptIdentity.dump();
            return null;
        }

    }

    public static ItemSchemeReferenceBase toSDMXConceptSchemeReference(ConceptSchemeReference csr) {
        return sdmx.commonreferences.ConceptSchemeReference.create(new NestedNCNameID(csr.getConceptScheme().getConceptSchemePK().getAgencyID()), new IDType(csr.getConceptScheme().getConceptSchemePK().getId()), new Version(csr.getConceptScheme().getConceptSchemePK().getVersion()));
    }
}
