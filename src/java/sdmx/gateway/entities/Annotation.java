/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
<<<<<<< HEAD
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
=======
import javax.persistence.Basic;
>>>>>>> origin/master
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
<<<<<<< HEAD
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
=======
>>>>>>> origin/master
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
<<<<<<< HEAD
import javax.validation.constraints.Size;
=======
>>>>>>> origin/master
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Owner
 */
@Entity
@Table(name = "Annotation", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Annotation.findAll", query = "SELECT a FROM Annotation a")
<<<<<<< HEAD
    , @NamedQuery(name = "Annotation.findByTitle", query = "SELECT a FROM Annotation a WHERE a.title = :title")
    , @NamedQuery(name = "Annotation.findByUrl", query = "SELECT a FROM Annotation a WHERE a.url = :url")
    , @NamedQuery(name = "Annotation.findByType", query = "SELECT a FROM Annotation a WHERE a.type = :type")
    , @NamedQuery(name = "Annotation.findByAnnotationId", query = "SELECT a FROM Annotation a WHERE a.annotationId = :annotationId")
    , @NamedQuery(name = "Annotation.findById", query = "SELECT a FROM Annotation a WHERE a.id = :id")})
=======
    , @NamedQuery(name = "Annotation.findByAnnotated", query = "SELECT a FROM Annotation a WHERE a.annotated = :annotated")})
>>>>>>> origin/master
public class Annotation implements Serializable {

    private static final long serialVersionUID = 1L;
<<<<<<< HEAD
    @Size(max = 255)
    @Column(name = "title", length = 255)
    private String title;
    @Size(max = 300)
    @Column(name = "url", length = 300)
    private String url;
    @Size(max = 255)
    @Column(name = "type", length = 255)
    private String type;
    @Size(max = 255)
    @Column(name = "annotationId", length = 255)
    private String annotationId;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private Long id;
    @JoinColumn(name = "annotated", referencedColumnName = "annotated")
    @ManyToOne
    private Annotated annotated;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "annotation")
    private List<AnnotationText> annotationTextList;
=======
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "annotated", nullable = false)
    private Long annotated;
    @OneToOne(mappedBy = "annotated")
    private Concept concept;
    @OneToOne(mappedBy = "annotated")
    private Codelist codelist;
    @OneToOne(mappedBy = "annotated")
    private Code code;
    @OneToOne(mappedBy = "annotated")
    private DataStructureComponent dataStructureComponent;
    @OneToOne(mappedBy = "annotated")
    private ConceptScheme conceptScheme;
>>>>>>> origin/master

    public Annotation() {
    }

<<<<<<< HEAD
    public Annotation(Long id) {
        this.id = id;
=======
    public Annotation(Long annotated) {
        this.annotated = annotated;
    }

    public Long getAnnotated() {
        return annotated;
    }

    public void setAnnotated(Long annotated) {
        this.annotated = annotated;
    }

    public Concept getConcept() {
        return concept;
>>>>>>> origin/master
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    public Codelist getCodelist() {
        return codelist;
    }

    public void setCodelist(Codelist codelist) {
        this.codelist = codelist;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    public DataStructureComponent getDataStructureComponent() {
        return dataStructureComponent;
    }

    public void setDataStructureComponent(DataStructureComponent dataStructureComponent) {
        this.dataStructureComponent = dataStructureComponent;
    }

    public ConceptScheme getConceptScheme() {
        return conceptScheme;
    }

<<<<<<< HEAD
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
=======
    public void setConceptScheme(ConceptScheme conceptScheme) {
        this.conceptScheme = conceptScheme;
>>>>>>> origin/master
    }

    @Override
    public int hashCode() {
        int hash = 0;
<<<<<<< HEAD
        hash += (id != null ? id.hashCode() : 0);
=======
        hash += (annotated != null ? annotated.hashCode() : 0);
>>>>>>> origin/master
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Annotation)) {
            return false;
        }
        Annotation other = (Annotation) object;
<<<<<<< HEAD
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
=======
        if ((this.annotated == null && other.annotated != null) || (this.annotated != null && !this.annotated.equals(other.annotated))) {
>>>>>>> origin/master
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
<<<<<<< HEAD
        return "sdmx.gateway.entities.Annotation[ id=" + id + " ]";
=======
        return "sdmx.gateway.entities.Annotation[ annotated=" + annotated + " ]";
>>>>>>> origin/master
    }
    
}
