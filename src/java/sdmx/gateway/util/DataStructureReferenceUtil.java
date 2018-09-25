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
import sdmx.gateway.entities.DataStructureReference;

/**
 *
 * @author James
 */
public class DataStructureReferenceUtil {

    public static sdmx.gateway.entities.DataStructureReference toDatabaseDataStructureReference(EntityManager em, sdmx.commonreferences.DataStructureReference reference) {
        sdmx.gateway.entities.DataStructureReference ref = new sdmx.gateway.entities.DataStructureReference();
        sdmx.gateway.entities.DataStructure ds = DataStructureUtil.findDataStructure(em, reference.getAgencyId().toString(),reference.getMaintainableParentId().toString(),reference.getVersion().toString());
        if (ds != null) {
            ref.setDataStructure(ds);
            ds.getDataStructureReferenceList().add(ref);
            em.persist(ref);
            em.merge(ds);
            em.flush();
            em.refresh(ref);
            return ref;
        } else {
            System.out.println("Data Structure doesn't exist!!!");
            reference.dump();
            return null;
        }

    }

    public static sdmx.commonreferences.DataStructureReference toSDMXDataStructureReference(DataStructureReference ref) {
        return sdmx.commonreferences.DataStructureReference.create(new NestedNCNameID(ref.getDataStructure().getDataStructurePK().getAgencyID()),new IDType(ref.getDataStructure().getDataStructurePK().getId()), new Version(ref.getDataStructure().getDataStructurePK().getVersion()));
    }
}
