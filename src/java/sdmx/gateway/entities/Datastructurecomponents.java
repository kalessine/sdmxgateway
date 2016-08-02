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
@Table(name = "datastructurecomponents", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Datastructurecomponents.findAll", query = "SELECT d FROM Datastructurecomponents d"),
    @NamedQuery(name = "Datastructurecomponents.findByDataStructureAgencyID", query = "SELECT d FROM Datastructurecomponents d WHERE d.datastructurecomponentsPK.dataStructureAgencyID = :dataStructureAgencyID"),
    @NamedQuery(name = "Datastructurecomponents.findByDataStructureID", query = "SELECT d FROM Datastructurecomponents d WHERE d.datastructurecomponentsPK.dataStructureID = :dataStructureID"),
    @NamedQuery(name = "Datastructurecomponents.findByDataStructureVersion", query = "SELECT d FROM Datastructurecomponents d WHERE d.datastructurecomponentsPK.dataStructureVersion = :dataStructureVersion"),
    @NamedQuery(name = "Datastructurecomponents.findByPosition", query = "SELECT d FROM Datastructurecomponents d WHERE d.datastructurecomponentsPK.position = :position"),
    @NamedQuery(name = "Datastructurecomponents.findByType", query = "SELECT d FROM Datastructurecomponents d WHERE d.type = :type"),
    @NamedQuery(name = "Datastructurecomponents.findByAssignmentStatus", query = "SELECT d FROM Datastructurecomponents d WHERE d.assignmentStatus = :assignmentStatus")})
public class Datastructurecomponents implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DatastructurecomponentsPK datastructurecomponentsPK;
    @Column(name = "Type")
    private Integer type;
    @Size(max = 50)
    @Column(name = "AssignmentStatus", length = 50)
    private String assignmentStatus;
    @JoinColumn(name = "annotations", referencedColumnName = "id")
    @ManyToOne
    private Annotated annotations;
    @JoinColumn(name = "AttributeRelationshipType", referencedColumnName = "id")
    @ManyToOne
    private Attributerelationshiptype attributeRelationshipType;
    @JoinColumn(name = "CodelistEnumeration", referencedColumnName = "id")
    @ManyToOne
    private Codelistreference codelistEnumeration;
    @JoinColumn(name = "ConceptIdentity", referencedColumnName = "id")
    @ManyToOne
    private Conceptreference conceptIdentity;
    @JoinColumn(name = "ConceptSchemeEnumeration", referencedColumnName = "id")
    @ManyToOne
    private Conceptschemereference conceptSchemeEnumeration;
    @JoinColumns({
        @JoinColumn(name = "DataStructureAgencyID", referencedColumnName = "AgencyID", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "DataStructureID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "DataStructureVersion", referencedColumnName = "Version", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Datastructure datastructure;

    public Datastructurecomponents() {
    }

    public Datastructurecomponents(DatastructurecomponentsPK datastructurecomponentsPK) {
        this.datastructurecomponentsPK = datastructurecomponentsPK;
    }

    public Datastructurecomponents(String dataStructureAgencyID, String dataStructureID, String dataStructureVersion, int position) {
        this.datastructurecomponentsPK = new DatastructurecomponentsPK(dataStructureAgencyID, dataStructureID, dataStructureVersion, position);
    }

    public DatastructurecomponentsPK getDatastructurecomponentsPK() {
        return datastructurecomponentsPK;
    }

    public void setDatastructurecomponentsPK(DatastructurecomponentsPK datastructurecomponentsPK) {
        this.datastructurecomponentsPK = datastructurecomponentsPK;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getAssignmentStatus() {
        return assignmentStatus;
    }

    public void setAssignmentStatus(String assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
    }

    public Annotated getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotated annotations) {
        this.annotations = annotations;
    }

    public Attributerelationshiptype getAttributeRelationshipType() {
        return attributeRelationshipType;
    }

    public void setAttributeRelationshipType(Attributerelationshiptype attributeRelationshipType) {
        this.attributeRelationshipType = attributeRelationshipType;
    }

    public Codelistreference getCodelistEnumeration() {
        return codelistEnumeration;
    }

    public void setCodelistEnumeration(Codelistreference codelistEnumeration) {
        this.codelistEnumeration = codelistEnumeration;
    }

    public Conceptreference getConceptIdentity() {
        return conceptIdentity;
    }

    public void setConceptIdentity(Conceptreference conceptIdentity) {
        this.conceptIdentity = conceptIdentity;
    }

    public Conceptschemereference getConceptSchemeEnumeration() {
        return conceptSchemeEnumeration;
    }

    public void setConceptSchemeEnumeration(Conceptschemereference conceptSchemeEnumeration) {
        this.conceptSchemeEnumeration = conceptSchemeEnumeration;
    }

    public Datastructure getDatastructure() {
        return datastructure;
    }

    public void setDatastructure(Datastructure datastructure) {
        this.datastructure = datastructure;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (datastructurecomponentsPK != null ? datastructurecomponentsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Datastructurecomponents)) {
            return false;
        }
        Datastructurecomponents other = (Datastructurecomponents) object;
        if ((this.datastructurecomponentsPK == null && other.datastructurecomponentsPK != null) || (this.datastructurecomponentsPK != null && !this.datastructurecomponentsPK.equals(other.datastructurecomponentsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Datastructurecomponents[ datastructurecomponentsPK=" + datastructurecomponentsPK + " ]";
    }
    
}
