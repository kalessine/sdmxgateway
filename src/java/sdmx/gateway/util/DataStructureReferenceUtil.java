/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.util;

import javax.persistence.EntityManager;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.ConceptSchemeReference;
import sdmx.commonreferences.DataStructureReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.ItemSchemeReferenceBase;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;
import sdmx.gateway.entities.Conceptreference;
import sdmx.gateway.entities.Conceptschemereference;
import sdmx.gateway.entities.Datastructurereference;

/**
 *
 * @author James
 */
public class DataStructureReferenceUtil {

    public static Datastructurereference toDatabaseDataStructureReference(EntityManager em, DataStructureReference reference) {
        sdmx.gateway.entities.Datastructurereference ref = new sdmx.gateway.entities.Datastructurereference();
        sdmx.gateway.entities.Datastructure ds = DataStructureUtil.findDataStructure(em, reference.getAgencyId().toString(),reference.getMaintainableParentId().toString(),reference.getVersion().toString());
        if (ds != null) {
            ref.setDatastructure(ds);
            ds.getDatastructurereferenceList().add(ref);
            em.persist(ref);
            em.flush();
            em.refresh(ref);
            return ref;
        } else {
            System.out.println("Data Structure doesn't exist!!!");
            reference.dump();
            return null;
        }

    }

    public static DataStructureReference toSDMXDataStructureReference(Datastructurereference ref) {
        return DataStructureReference.create(new NestedNCNameID(ref.getDatastructure().getDatastructurePK().getAgencyID()),new IDType(ref.getDatastructure().getDatastructurePK().getId()), new Version(ref.getDatastructure().getDatastructurePK().getVersion()));
    }
}
