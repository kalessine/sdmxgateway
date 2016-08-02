/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author James
 */
@Entity
@Table(name = "annotation", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Annotation.findAll", query = "SELECT a FROM Annotation a"),
    @NamedQuery(name = "Annotation.findByAnnotated", query = "SELECT a FROM Annotation a WHERE a.annotationPK.annotated = :annotated"),
    @NamedQuery(name = "Annotation.findByIndex", query = "SELECT a FROM Annotation a WHERE a.annotationPK.index = :index"),
    @NamedQuery(name = "Annotation.findByTitle", query = "SELECT a FROM Annotation a WHERE a.title = :title"),
    @NamedQuery(name = "Annotation.findByUrl", query = "SELECT a FROM Annotation a WHERE a.url = :url"),
    @NamedQuery(name = "Annotation.findByType", query = "SELECT a FROM Annotation a WHERE a.type = :type"),
    @NamedQuery(name = "Annotation.findByAnnotationId", query = "SELECT a FROM Annotation a WHERE a.annotationId = :annotationId")})
public class Annotation implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AnnotationPK annotationPK;
    @Size(max = 200)
    @Column(name = "title", length = 200)
    private String title;
    @Size(max = 300)
    @Column(name = "url", length = 300)
    private String url;
    @Size(max = 200)
    @Column(name = "type", length = 200)
    private String type;
    @Size(max = 200)
    @Column(name = "annotationId", length = 200)
    private String annotationId;
    @JoinColumn(name = "Annotated", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Annotated annotated1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "annotation")
    private List<Annotationtext> annotationtextList;

    public Annotation() {
    }

    public Annotation(AnnotationPK annotationPK) {
        this.annotationPK = annotationPK;
    }

    public Annotation(long annotated, long index) {
        this.annotationPK = new AnnotationPK(annotated, index);
    }

    public AnnotationPK getAnnotationPK() {
        return annotationPK;
    }

    public void setAnnotationPK(AnnotationPK annotationPK) {
        this.annotationPK = annotationPK;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAnnotationId() {
        return annotationId;
    }

    public void setAnnotationId(String annotationId) {
        this.annotationId = annotationId;
    }

    public Annotated getAnnotated1() {
        return annotated1;
    }

    public void setAnnotated1(Annotated annotated1) {
        this.annotated1 = annotated1;
    }

    @XmlTransient
    public List<Annotationtext> getAnnotationtextList() {
        return annotationtextList;
    }

    public void setAnnotationtextList(List<Annotationtext> annotationtextList) {
        this.annotationtextList = annotationtextList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (annotationPK != null ? annotationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Annotation)) {
            return false;
        }
        Annotation other = (Annotation) object;
        if ((this.annotationPK == null && other.annotationPK != null) || (this.annotationPK != null && !this.annotationPK.equals(other.annotationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Annotation[ annotationPK=" + annotationPK + " ]";
    }
    
}
