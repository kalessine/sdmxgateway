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
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Owner
 */
@Entity
@Table(name = "DimensionStructureComponent", catalog = "repository", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"enumeration"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DimensionStructureComponent.findAll", query = "SELECT d FROM DimensionStructureComponent d")
    , @NamedQuery(name = "DimensionStructureComponent.findByPosition", query = "SELECT d FROM DimensionStructureComponent d WHERE d.position = :position")
    , @NamedQuery(name = "DimensionStructureComponent.findById", query = "SELECT d FROM DimensionStructureComponent d WHERE d.dimensionStructureComponentPK.id = :id")
    , @NamedQuery(name = "DimensionStructureComponent.findByVersion", query = "SELECT d FROM DimensionStructureComponent d WHERE d.dimensionStructureComponentPK.version = :version")
    , @NamedQuery(name = "DimensionStructureComponent.findByAssignmentStatus", query = "SELECT d FROM DimensionStructureComponent d WHERE d.assignmentStatus = :assignmentStatus")
    , @NamedQuery(name = "DimensionStructureComponent.findByConceptIdentity", query = "SELECT d FROM DimensionStructureComponent d WHERE d.conceptIdentity = :conceptIdentity")
    , @NamedQuery(name = "DimensionStructureComponent.findByAttributeRelationshipType", query = "SELECT d FROM DimensionStructureComponent d WHERE d.attributeRelationshipType = :attributeRelationshipType")
    , @NamedQuery(name = "DimensionStructureComponent.findByAnnotations", query = "SELECT d FROM DimensionStructureComponent d WHERE d.annotations = :annotations")
    , @NamedQuery(name = "DimensionStructureComponent.findByAgencyID", query = "SELECT d FROM DimensionStructureComponent d WHERE d.dimensionStructureComponentPK.agencyID = :agencyID")
    , @NamedQuery(name = "DimensionStructureComponent.findByComponentId", query = "SELECT d FROM DimensionStructureComponent d WHERE d.dimensionStructureComponentPK.componentId = :componentId")})
public class DimensionStructureComponent implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DimensionStructureComponentPK dimensionStructureComponentPK;
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
    @JoinColumn(name = "enumeration", referencedColumnName = "reference")
    @OneToOne
    private CodelistReference enumeration;

    public DimensionStructureComponent() {
    }

    public DimensionStructureComponent(DimensionStructureComponentPK dimensionStructureComponentPK) {
        this.dimensionStructureComponentPK = dimensionStructureComponentPK;
    }

    public DimensionStructureComponent(String id, String version, String agencyID, String componentId) {
        this.dimensionStructureComponentPK = new DimensionStructureComponentPK(id, version, agencyID, componentId);
    }

    public DimensionStructureComponentPK getDimensionStructureComponentPK() {
        return dimensionStructureComponentPK;
    }

    public void setDimensionStructureComponentPK(DimensionStructureComponentPK dimensionStructureComponentPK) {
        this.dimensionStructureComponentPK = dimensionStructureComponentPK;
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

    public CodelistReference getEnumeration() {
        return enumeration;
    }

    public void setEnumeration(CodelistReference enumeration) {
        this.enumeration = enumeration;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dimensionStructureComponentPK != null ? dimensionStructureComponentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DimensionStructureComponent)) {
            return false;
        }
        DimensionStructureComponent other = (DimensionStructureComponent) object;
        if ((this.dimensionStructureComponentPK == null && other.dimensionStructureComponentPK != null) || (this.dimensionStructureComponentPK != null && !this.dimensionStructureComponentPK.equals(other.dimensionStructureComponentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.DimensionStructureComponent[ dimensionStructureComponentPK=" + dimensionStructureComponentPK + " ]";
    }
    
}
