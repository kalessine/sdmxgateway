/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.util;

import javax.persistence.EntityManager;
import sdmx.commonreferences.ConceptReference;
import sdmx.gateway.entities.Codelistreference;
import sdmx.gateway.entities.Conceptreference;

/**
 *
 * @author James
 */
public class CodelistReferenceUtil {
    public static Codelistreference toDatabaseCodelistReference(EntityManager em,sdmx.commonreferences.CodelistReference enumeration) {
         sdmx.gateway.entities.Codelistreference ref = new sdmx.gateway.entities.Codelistreference();
         sdmx.gateway.entities.Codelist c = CodelistUtil.findDatabaseCodelist(em,enumeration.getAgencyId().toString(),enumeration.getMaintainableParentId().toString(),enumeration.getVersion().toString());
         if(c !=null ) {
         ref.setCodelist(c);
         c.getCodelistreferenceList().add(ref);
         em.getTransaction().begin();
         em.persist(ref);
         em.getTransaction().commit();
         em.refresh(ref);
         }else{
             System.out.println("Target Codelist is null!!"+":"+enumeration.getAgencyId()+":"+enumeration.getId()+":"+enumeration.getVersion());
             return null;
         }
         return ref;
    }    
}
