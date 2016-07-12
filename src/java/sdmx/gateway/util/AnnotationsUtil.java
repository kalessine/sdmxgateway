/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.util;

import java.util.ArrayList;
import java.util.List;
import sdmx.common.AnnotationType;
import sdmx.common.Annotations;
import sdmx.common.TextType;
import sdmx.gateway.entities.Annotated;
import sdmx.gateway.entities.Annotationtext;

/**
 *
 * @author James
 */
public class AnnotationsUtil {
      public static sdmx.gateway.entities.Annotated toDatabaseAnnotations(Annotations annots) {
          if( annots==null ) return null;
          Annotated at = new Annotated();
          List<sdmx.gateway.entities.Annotation> dbAnnots = new ArrayList<>(annots.size());
          for(int i=0;i<annots.size();i++) {
              dbAnnots.add(toDatabaseAnnotation(annots.getAnnotation(i)));
          }
          at.setAnnotationList(dbAnnots);
          return at;
      }
      public static sdmx.gateway.entities.Annotation toDatabaseAnnotation(AnnotationType annot) {
          if( annot == null ) return null;
          sdmx.gateway.entities.Annotation dbAnnot = new sdmx.gateway.entities.Annotation();
          dbAnnot.setTitle(annot.getAnnotationTitle());
          dbAnnot.setAnnotationId(annot.getId());
          dbAnnot.setType(annot.getAnnotationType());
          dbAnnot.setUrl(annot.getAnnotationUrl());
          List<sdmx.gateway.entities.Annotationtext> texts = new ArrayList<>();
          for(int i=0;i<annot.getAnnotationText().size();i++) {
              texts.add(toDatabaseAnnotationText(annot.getAnnotationText().get(i)));
          }
          return dbAnnot;
      }
      public static sdmx.gateway.entities.Annotationtext toDatabaseAnnotationText(TextType tt) {
          sdmx.gateway.entities.Annotationtext at = new sdmx.gateway.entities.Annotationtext();
          at.setText(tt.getText());
          at.setLang(tt.getLang());
          return at;
      }
}
