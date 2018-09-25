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
@Table(name = "TimeStructureComponent", catalog = "repository", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"enumeration"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TimeStructureComponent.findAll", query = "SELECT t FROM TimeStructureComponent t")
    , @NamedQuery(name = "TimeStructureComponent.findByPosition", query = "SELECT t FROM TimeStructureComponent t WHERE t.position = :position")
    , @NamedQuery(name = "TimeStructureComponent.findById", query = "SELECT t FROM TimeStructureComponent t WHERE t.timeStructureComponentPK.id = :id")
    , @NamedQuery(name = "TimeStructureComponent.findByVersion", query = "SELECT t FROM TimeStructureComponent t WHERE t.timeStructureComponentPK.version = :version")
    , @NamedQuery(name = "TimeStructureComponent.findByAssignmentStatus", query = "SELECT t FROM TimeStructureComponent t WHERE t.assignmentStatus = :assignmentStatus")
    , @NamedQuery(name = "TimeStructureComponent.findByConceptIdentity", query = "SELECT t FROM TimeStructureComponent t WHERE t.conceptIdentity = :conceptIdentity")
    , @NamedQuery(name = "TimeStructureComponent.findByAttributeRelationshipType", query = "SELECT t FROM TimeStructureComponent t WHERE t.attributeRelationshipType = :attributeRelationshipType")
    , @NamedQuery(name = "TimeStructureComponent.findByAnnotations", query = "SELECT t FROM TimeStructureComponent t WHERE t.annotations = :annotations")
    , @NamedQuery(name = "TimeStructureComponent.findByAgencyID", query = "SELECT t FROM TimeStructureComponent t WHERE t.timeStructureComponentPK.agencyID = :agencyID")
    , @NamedQuery(name = "TimeStructureComponent.findByComponentId", query = "SELECT t FROM TimeStructureComponent t WHERE t.timeStructureComponentPK.componentId = :componentId")})
public class TimeStructureComponent implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TimeStructureComponentPK timeStructureComponentPK;
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

    public TimeStructureComponent() {
    }

    public TimeStructureComponent(TimeStructureComponentPK timeStructureComponentPK) {
        this.timeStructureComponentPK = timeStructureComponentPK;
    }

    public TimeStructureComponent(String id, String version, String agencyID, String componentId) {
        this.timeStructureComponentPK = new TimeStructureComponentPK(id, version, agencyID, componentId);
    }

    public TimeStructureComponentPK getTimeStructureComponentPK() {
        return timeStructureComponentPK;
    }

    public void setTimeStructureComponentPK(TimeStructureComponentPK timeStructureComponentPK) {
        this.timeStructureComponentPK = timeStructureComponentPK;
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
        hash += (timeStructureComponentPK != null ? timeStructureComponentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TimeStructureComponent)) {
            return false;
        }
        TimeStructureComponent other = (TimeStructureComponent) object;
        if ((this.timeStructureComponentPK == null && other.timeStructureComponentPK != null) || (this.timeStructureComponentPK != null && !this.timeStructureComponentPK.equals(other.timeStructureComponentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.TimeStructureComponent[ timeStructureComponentPK=" + timeStructureComponentPK + " ]";
    }
    
}
