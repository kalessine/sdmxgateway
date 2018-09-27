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
@Table(name = "AttributeStructureComponent", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AttributeStructureComponent.findAll", query = "SELECT a FROM AttributeStructureComponent a")
    , @NamedQuery(name = "AttributeStructureComponent.findByAgencyID", query = "SELECT a FROM AttributeStructureComponent a WHERE a.attributeStructureComponentPK.agencyID = :agencyID")
    , @NamedQuery(name = "AttributeStructureComponent.findById", query = "SELECT a FROM AttributeStructureComponent a WHERE a.attributeStructureComponentPK.id = :id")
    , @NamedQuery(name = "AttributeStructureComponent.findByVersion", query = "SELECT a FROM AttributeStructureComponent a WHERE a.attributeStructureComponentPK.version = :version")
    , @NamedQuery(name = "AttributeStructureComponent.findByComponentId", query = "SELECT a FROM AttributeStructureComponent a WHERE a.attributeStructureComponentPK.componentId = :componentId")
    , @NamedQuery(name = "AttributeStructureComponent.findByPosition", query = "SELECT a FROM AttributeStructureComponent a WHERE a.position = :position")
    , @NamedQuery(name = "AttributeStructureComponent.findByAssignmentStatus", query = "SELECT a FROM AttributeStructureComponent a WHERE a.assignmentStatus = :assignmentStatus")
    , @NamedQuery(name = "AttributeStructureComponent.findByConceptIdentity", query = "SELECT a FROM AttributeStructureComponent a WHERE a.conceptIdentity = :conceptIdentity")
    , @NamedQuery(name = "AttributeStructureComponent.findByAttributeRelationshipType", query = "SELECT a FROM AttributeStructureComponent a WHERE a.attributeRelationshipType = :attributeRelationshipType")
    , @NamedQuery(name = "AttributeStructureComponent.findByAnnotations", query = "SELECT a FROM AttributeStructureComponent a WHERE a.annotations = :annotations")
    , @NamedQuery(name = "AttributeStructureComponent.findByType", query = "SELECT a FROM AttributeStructureComponent a WHERE a.type = :type")
    , @NamedQuery(name = "AttributeStructureComponent.findByCodelistEnumeration", query = "SELECT a FROM AttributeStructureComponent a WHERE a.codelistEnumeration = :codelistEnumeration")
    , @NamedQuery(name = "AttributeStructureComponent.findByConceptSchemeEnumeration", query = "SELECT a FROM AttributeStructureComponent a WHERE a.conceptSchemeEnumeration = :conceptSchemeEnumeration")})
public class AttributeStructureComponent implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AttributeStructureComponentPK attributeStructureComponentPK;
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

    public AttributeStructureComponent() {
    }

    public AttributeStructureComponent(AttributeStructureComponentPK attributeStructureComponentPK) {
        this.attributeStructureComponentPK = attributeStructureComponentPK;
    }

    public AttributeStructureComponent(String agencyID, String id, String version, String componentId) {
        this.attributeStructureComponentPK = new AttributeStructureComponentPK(agencyID, id, version, componentId);
    }

    public AttributeStructureComponentPK getAttributeStructureComponentPK() {
        return attributeStructureComponentPK;
    }

    public void setAttributeStructureComponentPK(AttributeStructureComponentPK attributeStructureComponentPK) {
        this.attributeStructureComponentPK = attributeStructureComponentPK;
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
        hash += (attributeStructureComponentPK != null ? attributeStructureComponentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AttributeStructureComponent)) {
            return false;
        }
        AttributeStructureComponent other = (AttributeStructureComponent) object;
        if ((this.attributeStructureComponentPK == null && other.attributeStructureComponentPK != null) || (this.attributeStructureComponentPK != null && !this.attributeStructureComponentPK.equals(other.attributeStructureComponentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.AttributeStructureComponent[ attributeStructureComponentPK=" + attributeStructureComponentPK + " ]";
    }
    
}