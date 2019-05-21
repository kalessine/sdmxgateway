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
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@Entity
@Table(name = "DataStructureComponent", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"conceptIdentity"})
    , @UniqueConstraint(columnNames = {"conceptSchemeEnumeration"})
    , @UniqueConstraint(columnNames = {"attributeRelationshipType"})
    , @UniqueConstraint(columnNames = {"annotations"})
    , @UniqueConstraint(columnNames = {"codelistEnumeration"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DataStructureComponent.findAll", query = "SELECT d FROM DataStructureComponent d")
    , @NamedQuery(name = "DataStructureComponent.findByAssignmentStatus", query = "SELECT d FROM DataStructureComponent d WHERE d.assignmentStatus = :assignmentStatus")
    , @NamedQuery(name = "DataStructureComponent.findByType", query = "SELECT d FROM DataStructureComponent d WHERE d.type = :type")
    , @NamedQuery(name = "DataStructureComponent.findByComponentId", query = "SELECT d FROM DataStructureComponent d WHERE d.componentId = :componentId")
    , @NamedQuery(name = "DataStructureComponent.findByAgencyId", query = "SELECT d FROM DataStructureComponent d WHERE d.dataStructureComponentPK.agencyId = :agencyId")
    , @NamedQuery(name = "DataStructureComponent.findById", query = "SELECT d FROM DataStructureComponent d WHERE d.dataStructureComponentPK.id = :id")
    , @NamedQuery(name = "DataStructureComponent.findByVersion", query = "SELECT d FROM DataStructureComponent d WHERE d.dataStructureComponentPK.version = :version")
    , @NamedQuery(name = "DataStructureComponent.findByPosition", query = "SELECT d FROM DataStructureComponent d WHERE d.dataStructureComponentPK.position = :position")})
public class DataStructureComponent implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DataStructureComponentPK dataStructureComponentPK;
    @Size(max = 255)
    @Column(name = "assignmentStatus", length = 255)
    private String assignmentStatus;
    @Column(name = "type")
    private Integer type;
    @Size(max = 255)
    @Column(name = "componentId", length = 255)
    private String componentId;
    @JoinColumn(name = "annotations", referencedColumnName = "annotations")
    @OneToOne
    private Annotations annotations;
    @JoinColumn(name = "attributeRelationshipType", referencedColumnName = "attributeRelationshipType")
    @OneToOne
    private AttributeRelationshipType attributeRelationshipType;
    @JoinColumn(name = "codelistEnumeration", referencedColumnName = "reference")
    @OneToOne
    private CodelistReference codelistEnumeration;
    @JoinColumn(name = "conceptIdentity", referencedColumnName = "reference")
    @OneToOne
    private ConceptReference conceptIdentity;
    @JoinColumn(name = "conceptSchemeEnumeration", referencedColumnName = "reference")
    @OneToOne
    private ConceptSchemeReference conceptSchemeEnumeration;
    @JoinColumns({
        @JoinColumn(name = "agencyId", referencedColumnName = "agencyId", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "version", referencedColumnName = "version", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private DataStructure dataStructure;

    public DataStructureComponent() {
    }

    public DataStructureComponent(DataStructureComponentPK dataStructureComponentPK) {
        this.dataStructureComponentPK = dataStructureComponentPK;
    }

    public DataStructureComponent(String agencyId, String id, String version, short position) {
        this.dataStructureComponentPK = new DataStructureComponentPK(agencyId, id, version, position);
    }

    public DataStructureComponentPK getDataStructureComponentPK() {
        return dataStructureComponentPK;
    }

    public void setDataStructureComponentPK(DataStructureComponentPK dataStructureComponentPK) {
        this.dataStructureComponentPK = dataStructureComponentPK;
    }

    public String getAssignmentStatus() {
        return assignmentStatus;
    }

    public void setAssignmentStatus(String assignmentStatus) {
        this.assignmentStatus = assignmentStatus;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public String getComponentId() {
        return componentId;
    }

    public void setComponentId(String componentId) {
        this.componentId = componentId;
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotations annotations) {
        this.annotations = annotations;
    }

    public AttributeRelationshipType getAttributeRelationshipType() {
        return attributeRelationshipType;
    }

    public void setAttributeRelationshipType(AttributeRelationshipType attributeRelationshipType) {
        this.attributeRelationshipType = attributeRelationshipType;
    }

    public CodelistReference getCodelistEnumeration() {
        return codelistEnumeration;
    }

    public void setCodelistEnumeration(CodelistReference codelistEnumeration) {
        this.codelistEnumeration = codelistEnumeration;
    }

    public ConceptReference getConceptIdentity() {
        return conceptIdentity;
    }

    public void setConceptIdentity(ConceptReference conceptIdentity) {
        this.conceptIdentity = conceptIdentity;
    }

    public ConceptSchemeReference getConceptSchemeEnumeration() {
        return conceptSchemeEnumeration;
    }

    public void setConceptSchemeEnumeration(ConceptSchemeReference conceptSchemeEnumeration) {
        this.conceptSchemeEnumeration = conceptSchemeEnumeration;
    }

    public DataStructure getDataStructure() {
        return dataStructure;
    }

    public void setDataStructure(DataStructure dataStructure) {
        this.dataStructure = dataStructure;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataStructureComponentPK != null ? dataStructureComponentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataStructureComponent)) {
            return false;
        }
        DataStructureComponent other = (DataStructureComponent) object;
        if ((this.dataStructureComponentPK == null && other.dataStructureComponentPK != null) || (this.dataStructureComponentPK != null && !this.dataStructureComponentPK.equals(other.dataStructureComponentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.DataStructureComponent[ dataStructureComponentPK=" + dataStructureComponentPK + " ]";
    }
    
}
