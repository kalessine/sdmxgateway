/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.util;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.FlushModeType;
import javax.validation.ConstraintViolationException;
import sdmx.common.TextType;
import sdmx.gateway.entities.Dataflow;
import sdmx.gateway.entities.DataStructure;
import sdmx.gateway.entities.Name;
import sdmx.structure.codelist.CodeType;
import sdmx.structure.codelist.CodelistType;
import sdmx.structure.concept.ConceptSchemeType;
import sdmx.structure.concept.ConceptType;
import sdmx.structure.dataflow.DataflowType;
import sdmx.structure.datastructure.DataStructureType;

/**
 *
 * @author James
 */
public class NameUtil {

    public static void setName(EntityManager em, DataStructure ds2, DataStructureType ds) {
        sdmx.gateway.entities.Name name = toDatabaseName(em, ds.getNames());
        ds2.setName(name);
    }

    public static void setName(EntityManager em, Dataflow df1, DataflowType df) {
        sdmx.gateway.entities.Name name = toDatabaseName(em, df.getNames());
        df1.setName(name);
    }

    public static void setName(EntityManager em, sdmx.gateway.entities.ConceptScheme conceptScheme, ConceptSchemeType cl) {
        sdmx.gateway.entities.Name name = toDatabaseName(em, cl.getNames());
        conceptScheme.setName(name);
    }

    public static void setName(EntityManager em, sdmx.gateway.entities.Concept concept, ConceptType cl) {
        sdmx.gateway.entities.Name name = toDatabaseName(em, cl.getNames());
        concept.setName(name);
    }

    public static void setName(EntityManager em, sdmx.gateway.entities.Codelist codeList, CodelistType cl) {
        sdmx.gateway.entities.Name name = toDatabaseName(em, cl.getNames());
        codeList.setName(name);
    }

    public static void setName(EntityManager em, sdmx.gateway.entities.Code code, CodeType cl) {
        sdmx.gateway.entities.Name name = toDatabaseName(em, cl.getNames());
        code.setName(name);
    }

    public static sdmx.gateway.entities.Name toDatabaseName(EntityManager em, List<sdmx.common.Name> names) {
        sdmx.gateway.entities.Name nm = new sdmx.gateway.entities.Name();
        List<sdmx.gateway.entities.NameText> result = new ArrayList<>();
        em.persist(nm);
        em.flush();
        em.refresh(nm);
        for (int i = 0; i < names.size(); i++) {
            result.add(toDatabaseNameText(nm, names.get(i)));
        }
        nm.setNameTextList(result);
        em.merge(nm);
        return nm;
    }

    public static sdmx.gateway.entities.NameText toDatabaseNameText(sdmx.gateway.entities.Name nam, sdmx.common.Name name) {
        sdmx.gateway.entities.NameText nm = new sdmx.gateway.entities.NameText();
        sdmx.gateway.entities.NameTextPK pk = new sdmx.gateway.entities.NameTextPK();
        nm.setText(name.getText());
        pk.setName(nam.getName());
        if ("en".equals(name.getLang())) {
            nam.setEn(name.getText());
        }
        pk.setLang(name.getLang());
        nm.setNameTextPK(pk);
        return nm;
    }

    public static List<sdmx.common.Name> toSDMXName(sdmx.gateway.entities.Name name) {
        List<sdmx.common.Name> result = new ArrayList<sdmx.common.Name>();
        for (sdmx.gateway.entities.NameText nt : name.getNameTextList()) {
            result.add(new sdmx.common.Name(nt.getNameTextPK().getLang(), nt.getText()));
        }
        return result;
    }

}
