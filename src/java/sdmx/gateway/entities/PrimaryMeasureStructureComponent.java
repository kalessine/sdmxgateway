/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import java.math.BigInteger;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Owner
 */
@Entity
@Table(name = "PrimaryMeasureStructureComponent", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "PrimaryMeasureStructureComponent.findAll", query = "SELECT p FROM PrimaryMeasureStructureComponent p")
    , @NamedQuery(name = "PrimaryMeasureStructureComponent.findByAgencyID", query = "SELECT p FROM PrimaryMeasureStructureComponent p WHERE p.primaryMeasureStructureComponentPK.agencyID = :agencyID")
    , @NamedQuery(name = "PrimaryMeasureStructureComponent.findById", query = "SELECT p FROM PrimaryMeasureStructureComponent p WHERE p.primaryMeasureStructureComponentPK.id = :id")
    , @NamedQuery(name = "PrimaryMeasureStructureComponent.findByVersion", query = "SELECT p FROM PrimaryMeasureStructureComponent p WHERE p.primaryMeasureStructureComponentPK.version = :version")
    , @NamedQuery(name = "PrimaryMeasureStructureComponent.findByComponentId", query = "SELECT p FROM PrimaryMeasureStructureComponent p WHERE p.primaryMeasureStructureComponentPK.componentId = :componentId")
    , @NamedQuery(name = "PrimaryMeasureStructureComponent.findByPosition", query = "SELECT p FROM PrimaryMeasureStructureComponent p WHERE p.position = :position")
    , @NamedQuery(name = "PrimaryMeasureStructureComponent.findByAssignmentStatus", query = "SELECT p FROM PrimaryMeasureStructureComponent p WHERE p.assignmentStatus = :assignmentStatus")
    , @NamedQuery(name = "PrimaryMeasureStructureComponent.findByConceptIdentity", query = "SELECT p FROM PrimaryMeasureStructureComponent p WHERE p.conceptIdentity = :conceptIdentity")
    , @NamedQuery(name = "PrimaryMeasureStructureComponent.findByAttributeRelationshipType", query = "SELECT p FROM PrimaryMeasureStructureComponent p WHERE p.attributeRelationshipType = :attributeRelationshipType")
    , @NamedQuery(name = "PrimaryMeasureStructureComponent.findByAnnotations", query = "SELECT p FROM PrimaryMeasureStructureComponent p WHERE p.annotations = :annotations")
    , @NamedQuery(name = "PrimaryMeasureStructureComponent.findByType", query = "SELECT p FROM PrimaryMeasureStructureComponent p WHERE p.type = :type")
    , @NamedQuery(name = "PrimaryMeasureStructureComponent.findByCodelistEnumeration", query = "SELECT p FROM PrimaryMeasureStructureComponent p WHERE p.codelistEnumeration = :codelistEnumeration")
    , @NamedQuery(name = "PrimaryMeasureStructureComponent.findByConceptSchemeEnumeration", query = "SELECT p FROM PrimaryMeasureStructureComponent p WHERE p.conceptSchemeEnumeration = :conceptSchemeEnumeration")})
public class PrimaryMeasureStructureComponent implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected PrimaryMeasureStructureComponentPK primaryMeasureStructureComponentPK;
    @Column(name = "position")
    private Short position;
    @Size(max = 2147483647)
    @Column(name = "assignmentStatus", length = 2147483647)
    private String assignmentStatus;
    @Column(name = "conceptIdentity")
    private BigInteger conceptIdentity;
    @Column(name = "attributeRelationshipType")
    private BigInteger attributeRelationshipType;
    @Column(name = "Annotations")
    private BigInteger annotations;
    @Column(name = "type")
    private Integer type;
    @Column(name = "codelistEnumeration")
    private BigInteger codelistEnumeration;
    @Column(name = "conceptSchemeEnumeration")
    private BigInteger conceptSchemeEnumeration;

    public PrimaryMeasureStructureComponent() {
    }

    public PrimaryMeasureStructureComponent(PrimaryMeasureStructureComponentPK primaryMeasureStructureComponentPK) {
        this.primaryMeasureStructureComponentPK = primaryMeasureStructureComponentPK;
    }

    public PrimaryMeasureStructureComponent(String agencyID, String id, String version, String componentId) {
        this.primaryMeasureStructureComponentPK = new PrimaryMeasureStructureComponentPK(agencyID, id, version, componentId);
    }

    public PrimaryMeasureStructureComponentPK getPrimaryMeasureStructureComponentPK() {
        return primaryMeasureStructureComponentPK;
    }

    public void setPrimaryMeasureStructureComponentPK(PrimaryMeasureStructureComponentPK primaryMeasureStructureComponentPK) {
        this.primaryMeasureStructureComponentPK = primaryMeasureStructureComponentPK;
    }

    public Short getPosition() {
        return position;
    }

    public void setPosition(Short position) {
        this.position = position;
    }

    public String getAssignmentStatus() {
        return assignmentStatus;
    }

    public void setAssignmentStatus(String assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
    }

    public BigInteger getConceptIdentity() {
        return conceptIdentity;
    }

    public void setConceptIdentity(BigInteger conceptIdentity) {
        this.conceptIdentity = conceptIdentity;
    }

    public BigInteger getAttributeRelationshipType() {
        return attributeRelationshipType;
    }

    public void setAttributeRelationshipType(BigInteger attributeRelationshipType) {
        this.attributeRelationshipType = attributeRelationshipType;
    }

    public BigInteger getAnnotations() {
        return annotations;
    }

    public void setAnnotations(BigInteger annotations) {
        this.annotations = annotations;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public BigInteger getCodelistEnumeration() {
        return codelistEnumeration;
    }

    public void setCodelistEnumeration(BigInteger codelistEnumeration) {
        this.codelistEnumeration = codelistEnumeration;
    }

    public BigInteger getConceptSchemeEnumeration() {
        return conceptSchemeEnumeration;
    }

    public void setConceptSchemeEnumeration(BigInteger conceptSchemeEnumeration) {
        this.conceptSchemeEnumeration = conceptSchemeEnumeration;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (primaryMeasureStructureComponentPK != null ? primaryMeasureStructureComponentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof PrimaryMeasureStructureComponent)) {
            return false;
        }
        PrimaryMeasureStructureComponent other = (PrimaryMeasureStructureComponent) object;
        if ((this.primaryMeasureStructureComponentPK == null && other.primaryMeasureStructureComponentPK != null) || (this.primaryMeasureStructureComponentPK != null && !this.primaryMeasureStructureComponentPK.equals(other.primaryMeasureStructureComponentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.PrimaryMeasureStructureComponent[ primaryMeasureStructureComponentPK=" + primaryMeasureStructureComponentPK + " ]";
    }
    
}
