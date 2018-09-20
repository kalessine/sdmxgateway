/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author James
 */
@Entity
@Table(catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Annotation.findAll", query = "SELECT a FROM Annotation a")
    , @NamedQuery(name = "Annotation.findByTitle", query = "SELECT a FROM Annotation a WHERE a.title = :title")
    , @NamedQuery(name = "Annotation.findByUrl", query = "SELECT a FROM Annotation a WHERE a.url = :url")
    , @NamedQuery(name = "Annotation.findByType", query = "SELECT a FROM Annotation a WHERE a.type = :type")
    , @NamedQuery(name = "Annotation.findByAnnotationId", query = "SELECT a FROM Annotation a WHERE a.annotationId = :annotationId")
    , @NamedQuery(name = "Annotation.findById", query = "SELECT a FROM Annotation a WHERE a.id = :id")})
public class Annotation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(length = 255)
    private String title;
    @Size(max = 300)
    @Column(length = 300)
    private String url;
    @Size(max = 255)
    @Column(length = 255)
    private String type;
    @Size(max = 255)
    @Column(length = 255)
    private String annotationId;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long id;
    @JoinColumn(name = "annotated", referencedColumnName = "annotated")
    @ManyToOne
    private Annotated annotated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "annotation")
    private List<AnnotationText> annotationTextList;

    public Annotation() {
    }

    public Annotation(Long id) {
        this.id = id;
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

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Annotated getAnnotated() {
        return annotated;
    }

    public void setAnnotated(Annotated annotated) {
        this.annotated = annotated;
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
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Annotation)) {
            return false;
        }
        Annotation other = (Annotation) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Annotation[ id=" + id + " ]";
    }
    
}
