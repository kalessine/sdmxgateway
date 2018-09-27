/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@Entity
@Table(name = "AttributeRelationshipType", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "AttributeRelationshipType.findAll", query = "SELECT a FROM AttributeRelationshipType a")
    , @NamedQuery(name = "AttributeRelationshipType.findByAttributeRelationshipType", query = "SELECT a FROM AttributeRelationshipType a WHERE a.attributeRelationshipType = :attributeRelationshipType")
    , @NamedQuery(name = "AttributeRelationshipType.findByEmpty", query = "SELECT a FROM AttributeRelationshipType a WHERE a.empty = :empty")
    , @NamedQuery(name = "AttributeRelationshipType.findByAttachGroup", query = "SELECT a FROM AttributeRelationshipType a WHERE a.attachGroup = :attachGroup")
    , @NamedQuery(name = "AttributeRelationshipType.findByAttachGroups", query = "SELECT a FROM AttributeRelationshipType a WHERE a.attachGroups = :attachGroups")
    , @NamedQuery(name = "AttributeRelationshipType.findByPrimaryMeasureReference", query = "SELECT a FROM AttributeRelationshipType a WHERE a.primaryMeasureReference = :primaryMeasureReference")})
public class AttributeRelationshipType implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "attributeRelationshipType", nullable = false)
    private Long attributeRelationshipType;
    @Column(name = "empty")
    private Short empty;
    @Size(max = 2147483647)
    @Column(name = "attachGroup", length = 2147483647)
    private String attachGroup;
    @Column(name = "attachGroups")
    private Short attachGroups;
    @Size(max = 2147483647)
    @Column(name = "primaryMeasureReference", length = 2147483647)
    private String primaryMeasureReference;
    @OneToOne(mappedBy = "attributeRelationshipType")
    private DataStructureComponent dataStructureComponent;

    public AttributeRelationshipType() {
    }

    public AttributeRelationshipType(Long attributeRelationshipType) {
        this.attributeRelationshipType = attributeRelationshipType;
    }

    public Long getAttributeRelationshipType() {
        return attributeRelationshipType;
    }

    public void setAttributeRelationshipType(Long attributeRelationshipType) {
        this.attributeRelationshipType = attributeRelationshipType;
    }

    public Short getEmpty() {
        return empty;
    }

    public void setEmpty(Short empty) {
        this.empty = empty;
    }

    public String getAttachGroup() {
        return attachGroup;
    }

    public void setAttachGroup(String attachGroup) {
        this.attachGroup = attachGroup;
    }

    public Short getAttachGroups() {
        return attachGroups;
    }

    public void setAttachGroups(Short attachGroups) {
        this.attachGroups = attachGroups;
    }

    public String getPrimaryMeasureReference() {
        return primaryMeasureReference;
    }

    public void setPrimaryMeasureReference(String primaryMeasureReference) {
        this.primaryMeasureReference = primaryMeasureReference;
    }

    public DataStructureComponent getDataStructureComponent() {
        return dataStructureComponent;
    }

    public void setDataStructureComponent(DataStructureComponent dataStructureComponent) {
        this.dataStructureComponent = dataStructureComponent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attributeRelationshipType != null ? attributeRelationshipType.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AttributeRelationshipType)) {
            return false;
        }
        AttributeRelationshipType other = (AttributeRelationshipType) object;
        if ((this.attributeRelationshipType == null && other.attributeRelationshipType != null) || (this.attributeRelationshipType != null && !this.attributeRelationshipType.equals(other.attributeRelationshipType))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.AttributeRelationshipType[ attributeRelationshipType=" + attributeRelationshipType + " ]";
    }
    
}
