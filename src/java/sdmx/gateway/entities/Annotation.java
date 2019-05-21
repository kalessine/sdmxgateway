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
@Table(name = "Annotation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Annotation.findAll", query = "SELECT a FROM Annotation a")
    , @NamedQuery(name = "Annotation.findByAnnotations", query = "SELECT a FROM Annotation a WHERE a.annotationPK.annotations = :annotations")
    , @NamedQuery(name = "Annotation.findByIndex", query = "SELECT a FROM Annotation a WHERE a.annotationPK.index = :index")
    , @NamedQuery(name = "Annotation.findByTitle", query = "SELECT a FROM Annotation a WHERE a.title = :title")
    , @NamedQuery(name = "Annotation.findByUrl", query = "SELECT a FROM Annotation a WHERE a.url = :url")
    , @NamedQuery(name = "Annotation.findByType", query = "SELECT a FROM Annotation a WHERE a.type = :type")
    , @NamedQuery(name = "Annotation.findByAnnotationId", query = "SELECT a FROM Annotation a WHERE a.annotationId = :annotationId")})
public class Annotation implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AnnotationPK annotationPK;
    @Size(max = 255)
    @Column(name = "title", length = 255)
    private String title;
    @Size(max = 600)
    @Column(name = "url", length = 600)
    private String url;
    @Size(max = 255)
    @Column(name = "type", length = 255)
    private String type;
    @Size(max = 255)
    @Column(name = "annotationId", length = 255)
    private String annotationId;
    @JoinColumn(name = "annotations", referencedColumnName = "annotations", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Annotations annotations1;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "annotation")
    private List<AnnotationText> annotationTextList;

    public Annotation() {
    }

    public Annotation(AnnotationPK annotationPK) {
        this.annotationPK = annotationPK;
    }

    public Annotation(long annotations, short index) {
        this.annotationPK = new AnnotationPK(annotations, index);
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

    public Annotations getAnnotations1() {
        return annotations1;
    }

    public void setAnnotations1(Annotations annotations1) {
        this.annotations1 = annotations1;
    }

    @XmlTransient
    public List<AnnotationText> getAnnotationTextList() {
        return annotationTextList;
    }

    public void setAnnotationTextList(List<AnnotationText> annotationTextList) {
        this.annotationTextList = annotationTextList;
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
