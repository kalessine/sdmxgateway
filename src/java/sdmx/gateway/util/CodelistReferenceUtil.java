/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.util;

import javax.persistence.EntityManager;
import sdmx.commonreferences.CodelistReference;
import sdmx.commonreferences.ConceptReference;
import sdmx.commonreferences.IDType;
import sdmx.commonreferences.ItemSchemeReferenceBase;
import sdmx.commonreferences.NestedNCNameID;
import sdmx.commonreferences.Version;

/**
 *
 * @author James
 */
public class CodelistReferenceUtil {
    public static sdmx.gateway.entities.CodelistReference toDatabaseCodelistReference(EntityManager em,sdmx.commonreferences.CodelistReference enumeration) {
         sdmx.gateway.entities.CodelistReference ref = new sdmx.gateway.entities.CodelistReference();
         sdmx.gateway.entities.Codelist c = CodelistUtil.findDatabaseCodelist(em,enumeration.getAgencyId().toString(),enumeration.getMaintainableParentId().toString(),enumeration.getVersion().toString());
         if(c !=null ) {
         ref.setCodelist(c);
         c.getCodelistReferenceList().add(ref);
         em.persist(ref);
         em.flush();
         em.refresh(ref);
         }else{
             System.out.println("Target Codelist is null!!"+":"+enumeration.getAgencyId()+":"+enumeration.getId()+":"+enumeration.getVersion());
             enumeration.dump();
             return null;
         }
         return ref;
    }    

    public static ItemSchemeReferenceBase toSDMXCodelistReference(sdmx.gateway.entities.CodelistReference clr) {
       return CodelistReference.create(new NestedNCNameID(clr.getCodelist().getCodelistPK().getAgencyId()),new IDType(clr.getCodelist().getCodelistPK().getId()),new Version(clr.getCodelist().getCodelistPK().getVersion()));
    }
}
