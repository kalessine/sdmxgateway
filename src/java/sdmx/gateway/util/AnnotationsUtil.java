/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.util;

import java.util.ArrayList;
import java.util.List;
import sdmx.common.Annotations;
import sdmx.common.AnnotationType;
import sdmx.common.TextType;
import sdmx.gateway.entities.Annotation;
import sdmx.gateway.entities.AnnotationText;

/**
 *
 * @author James
 */
public class AnnotationsUtil {
      public static sdmx.gateway.entities.Annotations toDatabaseAnnotations(Annotations annots) {
          if( annots==null ) return null;
          sdmx.gateway.entities.Annotations at = new sdmx.gateway.entities.Annotations();
          List<sdmx.gateway.entities.Annotation> dbAnnots = new ArrayList<sdmx.gateway.entities.Annotation>(annots.size());
          for(int i=0;i<annots.size();i++) {
              dbAnnots.add(toDatabaseAnnotation(annots.getAnnotation(i)));
              dbAnnots.get(dbAnnots.size()-1).getAnnotationPK().setIndex(dbAnnots.size()-1);
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
          List<sdmx.gateway.entities.AnnotationText> texts = new ArrayList<sdmx.gateway.entities.AnnotationText>();
          for(int i=0;i<annot.getAnnotationText().size();i++) {
              texts.add(toDatabaseAnnotationText(annot.getAnnotationText().get(i)));
              texts.get(texts.size()-1).getAnnotationTextPK().setTextIndex(texts.size()-1);
          }
          return dbAnnot;
      }
      public static sdmx.gateway.entities.AnnotationText toDatabaseAnnotationText(TextType tt) {
          sdmx.gateway.entities.AnnotationText at = new sdmx.gateway.entities.AnnotationText();
          at.setText(tt.getText());
          at.setLang(tt.getLang());
          return at;
      }
      public static Annotations toSDMXAnnotations(sdmx.gateway.entities.Annotations annot) {
          if( annot == null ) return null;
          Annotations annotations = new Annotations();
          List<sdmx.gateway.entities.Annotation> annots = annot.getAnnotationList();
          for(sdmx.gateway.entities.Annotation an:annots) {
              annotations.addAnnotation(toSDMXAnnotation(an));
          }
          return annotations;
      }

    public static sdmx.common.AnnotationType toSDMXAnnotation(sdmx.gateway.entities.Annotation an) {
        AnnotationType annot = new AnnotationType();
        annot.setAnnotationTitle(an.getTitle());
        annot.setAnnotationType(an.getType());
        annot.setAnnotationUrl(an.getUrl());
        for(AnnotationText text:an.getAnnotationTextList()){
            annot.addAnnotationText(new TextType(text.getLang(),text.getText()));
        }
        return annot;
    }
}
