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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author James
 */
@Entity
@Table(name = "annotated", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Annotated.findAll", query = "SELECT a FROM Annotated a"),
    @NamedQuery(name = "Annotated.findById", query = "SELECT a FROM Annotated a WHERE a.id = :id")})
public class Annotated implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "annotated1")
    private List<Annotation> annotationList;
    @OneToMany(mappedBy = "annotations")
    private List<Code> codeList;
    @OneToMany(mappedBy = "annotations")
    private List<Codelist> codelistList;
    @OneToMany(mappedBy = "annotations")
    private List<Concept> conceptList;
    @OneToMany(mappedBy = "annotations")
    private List<Dataflow> dataflowList;
    @OneToMany(mappedBy = "annotations")
    private List<Conceptscheme> conceptschemeList;
    @OneToMany(mappedBy = "annotations")
    private List<Datastructure> datastructureList;
    @OneToMany(mappedBy = "annotations")
    private List<Datastructurecomponents> datastructurecomponentsList;

    public Annotated() {
    }

    public Annotated(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlTransient
    public List<Annotation> getAnnotationList() {
        return annotationList;
    }

    public void setAnnotationList(List<Annotation> annotationList) {
        this.annotationList = annotationList;
    }

    @XmlTransient
    public List<Code> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<Code> codeList) {
        this.codeList = codeList;
    }

    @XmlTransient
    public List<Codelist> getCodelistList() {
        return codelistList;
    }

    public void setCodelistList(List<Codelist> codelistList) {
        this.codelistList = codelistList;
    }

    @XmlTransient
    public List<Concept> getConceptList() {
        return conceptList;
    }

    public void setConceptList(List<Concept> conceptList) {
        this.conceptList = conceptList;
    }

    @XmlTransient
    public List<Dataflow> getDataflowList() {
        return dataflowList;
    }

    public void setDataflowList(List<Dataflow> dataflowList) {
        this.dataflowList = dataflowList;
    }

    @XmlTransient
    public List<Conceptscheme> getConceptschemeList() {
        return conceptschemeList;
    }

    public void setConceptschemeList(List<Conceptscheme> conceptschemeList) {
        this.conceptschemeList = conceptschemeList;
    }

    @XmlTransient
    public List<Datastructure> getDatastructureList() {
        return datastructureList;
    }

    public void setDatastructureList(List<Datastructure> datastructureList) {
        this.datastructureList = datastructureList;
    }

    @XmlTransient
    public List<Datastructurecomponents> getDatastructurecomponentsList() {
        return datastructurecomponentsList;
    }

    public void setDatastructurecomponentsList(List<Datastructurecomponents> datastructurecomponentsList) {
        this.datastructurecomponentsList = datastructurecomponentsList;
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
        if (!(object instanceof Annotated)) {
            return false;
        }
        Annotated other = (Annotated) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Annotated[ id=" + id + " ]";
    }
    
}
