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
import sdmx.gateway.entities.Conceptreference;
import sdmx.gateway.entities.Conceptschemereference;

/**
 *
 * @author James
 */
public class ConceptSchemeReferenceUtil {

    public static Conceptschemereference toDatabaseConceptschemereference(EntityManager em, ConceptSchemeReference conceptIdentity) {
        sdmx.gateway.entities.Conceptschemereference ref = new sdmx.gateway.entities.Conceptschemereference();
        sdmx.gateway.entities.Conceptscheme c = ConceptSchemeUtil.findDatabaseConceptScheme(em, conceptIdentity.getAgencyId().toString(), conceptIdentity.getMaintainableParentId().toString(), conceptIdentity.getVersion().toString());
        if (c != null) {
            ref.setConceptscheme(c);
            c.getConceptschemereferenceList().add(ref);
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

    public static ItemSchemeReferenceBase toSDMXConceptSchemeReference(Conceptschemereference csr) {
        return ConceptSchemeReference.create(new NestedNCNameID(csr.getConceptscheme().getConceptschemePK().getAgencyID()), new IDType(csr.getConceptscheme().getConceptschemePK().getId()), new Version(csr.getConceptscheme().getConceptschemePK().getVersion()));
    }
}
