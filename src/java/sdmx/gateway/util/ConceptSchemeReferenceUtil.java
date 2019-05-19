/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.util;

import javax.persistence.EntityManager;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.ConceptSchemeReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.ItemSchemeReferenceBase;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;


/**
 *
 * @author James
 */
public class ConceptSchemeReferenceUtil {

    public static sdmx.gateway.entities.ConceptSchemeReference toDatabaseConceptschemereference(EntityManager em, ConceptSchemeReference conceptIdentity) {
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

    public static ItemSchemeReferenceBase toSDMXConceptSchemeReference(sdmx.gateway.entities.ConceptSchemeReference csr) {
        return ConceptSchemeReference.create(new NestedNCNameID(csr.getConceptScheme().getConceptSchemePK().getAgencyId()), new IDType(csr.getConceptScheme().getConceptSchemePK().getId()), new Version(csr.getConceptScheme().getConceptSchemePK().getVersion()));
    }
}
