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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
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
    @NamedQuery(name = "Annotation.findById", query = "SELECT a FROM Annotation a WHERE a.id = :id"),
    @NamedQuery(name = "Annotation.findByTitle", query = "SELECT a FROM Annotation a WHERE a.title = :title"),
    @NamedQuery(name = "Annotation.findByUrl", query = "SELECT a FROM Annotation a WHERE a.url = :url"),
    @NamedQuery(name = "Annotation.findByType", query = "SELECT a FROM Annotation a WHERE a.type = :type"),
    @NamedQuery(name = "Annotation.findByAnnotationId", query = "SELECT a FROM Annotation a WHERE a.annotationId = :annotationId")})
public class Annotation implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
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
    @ManyToMany(mappedBy = "annotationList")
    private List<Annotated> annotatedList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "annotation1")
    private Annotationtext annotationtext;

    public Annotation() {
    }

    public Annotation(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
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

    @XmlTransient
    public List<Annotated> getAnnotatedList() {
        return annotatedList;
    }

    public void setAnnotatedList(List<Annotated> annotatedList) {
        this.annotatedList = annotatedList;
    }

    public Annotationtext getAnnotationtext() {
        return annotationtext;
    }

    public void setAnnotationtext(Annotationtext annotationtext) {
        this.annotationtext = annotationtext;
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
