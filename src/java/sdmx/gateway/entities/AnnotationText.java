/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@Entity
@Table(catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AnnotationText.findAll", query = "SELECT a FROM AnnotationText a")
    , @NamedQuery(name = "AnnotationText.findById", query = "SELECT a FROM AnnotationText a WHERE a.annotationTextPK.id = :id")
    , @NamedQuery(name = "AnnotationText.findByLang", query = "SELECT a FROM AnnotationText a WHERE a.lang = :lang")
    , @NamedQuery(name = "AnnotationText.findByText", query = "SELECT a FROM AnnotationText a WHERE a.text = :text")
    , @NamedQuery(name = "AnnotationText.findByTextIndex", query = "SELECT a FROM AnnotationText a WHERE a.annotationTextPK.textIndex = :textIndex")})
public class AnnotationText implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AnnotationTextPK annotationTextPK;
    @Size(max = 255)
    @Column(length = 255)
    private String lang;
    @Size(max = 2147483647)
    @Column(length = 2147483647)
    private String text;
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Annotation annotation;

    public AnnotationText() {
    }

    public AnnotationText(AnnotationTextPK annotationTextPK) {
        this.annotationTextPK = annotationTextPK;
    }

    public AnnotationText(long id, short textIndex) {
        this.annotationTextPK = new AnnotationTextPK(id, textIndex);
    }

    public AnnotationTextPK getAnnotationTextPK() {
        return annotationTextPK;
    }

    public void setAnnotationTextPK(AnnotationTextPK annotationTextPK) {
        this.annotationTextPK = annotationTextPK;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Annotation getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Annotation annotation) {
        this.annotation = annotation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (annotationTextPK != null ? annotationTextPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnnotationText)) {
            return false;
        }
        AnnotationText other = (AnnotationText) object;
        if ((this.annotationTextPK == null && other.annotationTextPK != null) || (this.annotationTextPK != null && !this.annotationTextPK.equals(other.annotationTextPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.AnnotationText[ annotationTextPK=" + annotationTextPK + " ]";
    }
    
}
