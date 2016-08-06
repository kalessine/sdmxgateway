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
import javax.persistence.JoinColumns;
import javax.persistence.Lob;
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
@Table(name = "annotationtext", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Annotationtext.findAll", query = "SELECT a FROM Annotationtext a"),
    @NamedQuery(name = "Annotationtext.findByAnnotated", query = "SELECT a FROM Annotationtext a WHERE a.annotationtextPK.annotated = :annotated"),
    @NamedQuery(name = "Annotationtext.findByIndex", query = "SELECT a FROM Annotationtext a WHERE a.annotationtextPK.index = :index"),
    @NamedQuery(name = "Annotationtext.findByTextIndex", query = "SELECT a FROM Annotationtext a WHERE a.annotationtextPK.textIndex = :textIndex"),
    @NamedQuery(name = "Annotationtext.findByLang", query = "SELECT a FROM Annotationtext a WHERE a.lang = :lang")})
public class Annotationtext implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AnnotationtextPK annotationtextPK;
    @Size(max = 10)
    @Column(name = "lang", length = 10)
    private String lang;
    @Lob
    @Size(max = 65535)
    @Column(name = "text", length = 65535)
    private String text;
    @JoinColumns({
        @JoinColumn(name = "Annotated", referencedColumnName = "Annotated", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "index", referencedColumnName = "index", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Annotation annotation;

    public Annotationtext() {
    }

    public Annotationtext(AnnotationtextPK annotationtextPK) {
        this.annotationtextPK = annotationtextPK;
    }

    public Annotationtext(long annotated, long index, long textIndex) {
        this.annotationtextPK = new AnnotationtextPK(annotated, index, textIndex);
    }

    public AnnotationtextPK getAnnotationtextPK() {
        return annotationtextPK;
    }

    public void setAnnotationtextPK(AnnotationtextPK annotationtextPK) {
        this.annotationtextPK = annotationtextPK;
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
        hash += (annotationtextPK != null ? annotationtextPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Annotationtext)) {
            return false;
        }
        Annotationtext other = (Annotationtext) object;
        if ((this.annotationtextPK == null && other.annotationtextPK != null) || (this.annotationtextPK != null && !this.annotationtextPK.equals(other.annotationtextPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Annotationtext[ annotationtextPK=" + annotationtextPK + " ]";
    }
    
}
