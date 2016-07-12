/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
    @NamedQuery(name = "Annotationtext.findByAnnotation", query = "SELECT a FROM Annotationtext a WHERE a.annotation = :annotation"),
    @NamedQuery(name = "Annotationtext.findByLang", query = "SELECT a FROM Annotationtext a WHERE a.lang = :lang")})
public class Annotationtext implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Annotation", nullable = false)
    private Long annotation;
    @Size(max = 10)
    @Column(name = "lang", length = 10)
    private String lang;
    @Lob
    @Size(max = 65535)
    @Column(name = "text", length = 65535)
    private String text;
    @JoinColumn(name = "Annotation", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Annotation annotation1;

    public Annotationtext() {
    }

    public Annotationtext(Long annotation) {
        this.annotation = annotation;
    }

    public Long getAnnotation() {
        return annotation;
    }

    public void setAnnotation(Long annotation) {
        this.annotation = annotation;
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

    public Annotation getAnnotation1() {
        return annotation1;
    }

    public void setAnnotation1(Annotation annotation1) {
        this.annotation1 = annotation1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (annotation != null ? annotation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Annotationtext)) {
            return false;
        }
        Annotationtext other = (Annotationtext) object;
        if ((this.annotation == null && other.annotation != null) || (this.annotation != null && !this.annotation.equals(other.annotation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Annotationtext[ annotation=" + annotation + " ]";
    }
    
}
