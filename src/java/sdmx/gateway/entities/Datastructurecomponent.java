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
import javax.persistence.FetchType;
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
@Table(name = "datastructurecomponent", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Datastructurecomponent.findAll", query = "SELECT d FROM Datastructurecomponent d"),
    @NamedQuery(name = "Datastructurecomponent.findByDataStructureAgencyID", query = "SELECT d FROM Datastructurecomponent d WHERE d.datastructurecomponentPK.dataStructureAgencyID = :dataStructureAgencyID"),
    @NamedQuery(name = "Datastructurecomponent.findByDataStructureID", query = "SELECT d FROM Datastructurecomponent d WHERE d.datastructurecomponentPK.dataStructureID = :dataStructureID"),
    @NamedQuery(name = "Datastructurecomponent.findByDataStructureVersion", query = "SELECT d FROM Datastructurecomponent d WHERE d.datastructurecomponentPK.dataStructureVersion = :dataStructureVersion"),
    @NamedQuery(name = "Datastructurecomponent.findByPosition", query = "SELECT d FROM Datastructurecomponent d WHERE d.datastructurecomponentPK.position = :position"),
    @NamedQuery(name = "Datastructurecomponent.findByType", query = "SELECT d FROM Datastructurecomponent d WHERE d.type = :type"),
    @NamedQuery(name = "Datastructurecomponent.findByAssignmentStatus", query = "SELECT d FROM Datastructurecomponent d WHERE d.assignmentStatus = :assignmentStatus")})
public class Datastructurecomponent implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DatastructurecomponentPK datastructurecomponentPK;
    @Column(name = "Type")
    private Integer type;
    @Size(max = 50)
    @Column(name = "AssignmentStatus", length = 50)
    private String assignmentStatus;
    @JoinColumn(name = "annotations", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Annotated annotations;
    @JoinColumn(name = "AttributeRelationshipType", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Attributerelationshiptype attributeRelationshipType;
    @JoinColumn(name = "CodelistEnumeration", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Codelistreference codelistEnumeration;
    @JoinColumn(name = "ConceptIdentity", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Conceptreference conceptIdentity;
    @JoinColumn(name = "ConceptSchemeEnumeration", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Conceptschemereference conceptSchemeEnumeration;
    @JoinColumns({
        @JoinColumn(name = "DataStructureAgencyID", referencedColumnName = "AgencyID", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "DataStructureID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "DataStructureVersion", referencedColumnName = "Version", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Datastructure datastructure;

    public Datastructurecomponent() {
    }

    public Datastructurecomponent(DatastructurecomponentPK datastructurecomponentPK) {
        this.datastructurecomponentPK = datastructurecomponentPK;
    }

    public Datastructurecomponent(String dataStructureAgencyID, String dataStructureID, String dataStructureVersion, int position) {
        this.datastructurecomponentPK = new DatastructurecomponentPK(dataStructureAgencyID, dataStructureID, dataStructureVersion, position);
    }

    public DatastructurecomponentPK getDatastructurecomponentPK() {
        return datastructurecomponentPK;
    }

    public void setDatastructurecomponentPK(DatastructurecomponentPK datastructurecomponentPK) {
        this.datastructurecomponentPK = datastructurecomponentPK;
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
        hash += (datastructurecomponentPK != null ? datastructurecomponentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Datastructurecomponent)) {
            return false;
        }
        Datastructurecomponent other = (Datastructurecomponent) object;
        if ((this.datastructurecomponentPK == null && other.datastructurecomponentPK != null) || (this.datastructurecomponentPK != null && !this.datastructurecomponentPK.equals(other.datastructurecomponentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Datastructurecomponent[ datastructurecomponentPK=" + datastructurecomponentPK + " ]";
    }
    
}
