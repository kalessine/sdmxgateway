/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
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
@Table(name = "Annotated", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Annotated.findAll", query = "SELECT a FROM Annotated a")
    , @NamedQuery(name = "Annotated.findByAnnotated", query = "SELECT a FROM Annotated a WHERE a.annotated = :annotated")})
public class Annotated implements Serializable {

    private static final long serialVersionUID = 1L;
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
    @OneToMany(mappedBy = "annotated")
    private List<Annotation> annotationList;
    @OneToOne(mappedBy = "annotated")
    private DataStructureComponent dataStructureComponent;
    @OneToOne(mappedBy = "annotated")
    private ConceptScheme conceptScheme;

    public Annotated() {
    }

    public Annotated(Long annotated) {
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

    public ConceptScheme getConceptScheme() {
        return conceptScheme;
    }

    public void setConceptScheme(ConceptScheme conceptScheme) {
        this.conceptScheme = conceptScheme;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (annotated != null ? annotated.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Annotated)) {
            return false;
        }
        Annotated other = (Annotated) object;
        if ((this.annotated == null && other.annotated != null) || (this.annotated != null && !this.annotated.equals(other.annotated))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Annotated[ annotated=" + annotated + " ]";
    }
    
}
