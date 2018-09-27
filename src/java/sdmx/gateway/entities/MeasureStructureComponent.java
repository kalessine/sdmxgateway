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
@Table(name = "MeasureStructureComponent", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "MeasureStructureComponent.findAll", query = "SELECT m FROM MeasureStructureComponent m")
    , @NamedQuery(name = "MeasureStructureComponent.findByAgencyID", query = "SELECT m FROM MeasureStructureComponent m WHERE m.measureStructureComponentPK.agencyID = :agencyID")
    , @NamedQuery(name = "MeasureStructureComponent.findById", query = "SELECT m FROM MeasureStructureComponent m WHERE m.measureStructureComponentPK.id = :id")
    , @NamedQuery(name = "MeasureStructureComponent.findByVersion", query = "SELECT m FROM MeasureStructureComponent m WHERE m.measureStructureComponentPK.version = :version")
    , @NamedQuery(name = "MeasureStructureComponent.findByComponentId", query = "SELECT m FROM MeasureStructureComponent m WHERE m.measureStructureComponentPK.componentId = :componentId")
    , @NamedQuery(name = "MeasureStructureComponent.findByPosition", query = "SELECT m FROM MeasureStructureComponent m WHERE m.position = :position")
    , @NamedQuery(name = "MeasureStructureComponent.findByAssignmentStatus", query = "SELECT m FROM MeasureStructureComponent m WHERE m.assignmentStatus = :assignmentStatus")
    , @NamedQuery(name = "MeasureStructureComponent.findByConceptIdentity", query = "SELECT m FROM MeasureStructureComponent m WHERE m.conceptIdentity = :conceptIdentity")
    , @NamedQuery(name = "MeasureStructureComponent.findByAttributeRelationshipType", query = "SELECT m FROM MeasureStructureComponent m WHERE m.attributeRelationshipType = :attributeRelationshipType")
    , @NamedQuery(name = "MeasureStructureComponent.findByAnnotations", query = "SELECT m FROM MeasureStructureComponent m WHERE m.annotations = :annotations")
    , @NamedQuery(name = "MeasureStructureComponent.findByType", query = "SELECT m FROM MeasureStructureComponent m WHERE m.type = :type")
    , @NamedQuery(name = "MeasureStructureComponent.findByCodelistEnumeration", query = "SELECT m FROM MeasureStructureComponent m WHERE m.codelistEnumeration = :codelistEnumeration")
    , @NamedQuery(name = "MeasureStructureComponent.findByConceptSchemeEnumeration", query = "SELECT m FROM MeasureStructureComponent m WHERE m.conceptSchemeEnumeration = :conceptSchemeEnumeration")})
public class MeasureStructureComponent implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected MeasureStructureComponentPK measureStructureComponentPK;
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

    public MeasureStructureComponent() {
    }

    public MeasureStructureComponent(MeasureStructureComponentPK measureStructureComponentPK) {
        this.measureStructureComponentPK = measureStructureComponentPK;
    }

    public MeasureStructureComponent(String agencyID, String id, String version, String componentId) {
        this.measureStructureComponentPK = new MeasureStructureComponentPK(agencyID, id, version, componentId);
    }

    public MeasureStructureComponentPK getMeasureStructureComponentPK() {
        return measureStructureComponentPK;
    }

    public void setMeasureStructureComponentPK(MeasureStructureComponentPK measureStructureComponentPK) {
        this.measureStructureComponentPK = measureStructureComponentPK;
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
        hash += (measureStructureComponentPK != null ? measureStructureComponentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof MeasureStructureComponent)) {
            return false;
        }
        MeasureStructureComponent other = (MeasureStructureComponent) object;
        if ((this.measureStructureComponentPK == null && other.measureStructureComponentPK != null) || (this.measureStructureComponentPK != null && !this.measureStructureComponentPK.equals(other.measureStructureComponentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.MeasureStructureComponent[ measureStructureComponentPK=" + measureStructureComponentPK + " ]";
    }
    
}
