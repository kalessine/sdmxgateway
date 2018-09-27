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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Owner
 */
@Entity
@Table(name = "Annotations", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Annotations.findAll", query = "SELECT a FROM Annotations a")
    , @NamedQuery(name = "Annotations.findByAnnotations", query = "SELECT a FROM Annotations a WHERE a.annotations = :annotations")})
public class Annotations implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "Annotations", nullable = false)
    @GeneratedValue(strategy=GenerationType.TABLE)
    private Long annotations;
    @OneToOne(mappedBy = "annotations")
    private Codelist codelist;
    @OneToOne(mappedBy = "annotations")
    private ConceptScheme conceptScheme;
    @OneToOne(mappedBy = "annotations")
    private Concept concept;
    @OneToOne(mappedBy = "annotations")
    private DataStructure dataStructure;
    @OneToOne(mappedBy = "annotations")
    private Code code;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "annotations1")
    private List<Annotation> annotationList;
    @OneToOne(mappedBy = "annotations")
    private DataStructureComponent dataStructureComponent;
    @OneToOne(mappedBy = "annotations")
    private Dataflow dataflow;

    public Annotations() {
    }

    public Annotations(Long annotations) {
        this.annotations = annotations;
    }

    public Long getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Long annotations) {
        this.annotations = annotations;
    }

    public Codelist getCodelist() {
        return codelist;
    }

    public void setCodelist(Codelist codelist) {
        this.codelist = codelist;
    }

    public ConceptScheme getConceptScheme() {
        return conceptScheme;
    }

    public void setConceptScheme(ConceptScheme conceptScheme) {
        this.conceptScheme = conceptScheme;
    }

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    public DataStructure getDataStructure() {
        return dataStructure;
    }

    public void setDataStructure(DataStructure dataStructure) {
        this.dataStructure = dataStructure;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    @XmlTransient
    public List<Annotation> getAnnotationList() {
        return annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList = annotationList;
    }

    public DataStructureComponent getDataStructureComponent() {
        return dataStructureComponent;
    }

    public void setDataStructureComponent(DataStructureComponent dataStructureComponent) {
        this.dataStructureComponent = dataStructureComponent;
    }

    public Dataflow getDataflow() {
        return dataflow;
    }

    public void setDataflow(Dataflow dataflow) {
        this.dataflow = dataflow;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (annotations != null ? annotations.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Annotations)) {
            return false;
        }
        Annotations other = (Annotations) object;
        if ((this.annotations == null && other.annotations != null) || (this.annotations != null && !this.annotations.equals(other.annotations))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Annotations[ annotations=" + annotations + " ]";
    }
    
}
