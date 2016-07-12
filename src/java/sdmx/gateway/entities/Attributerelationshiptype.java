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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author James
 */
@Entity
@Table(name = "attributerelationshiptype", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attributerelationshiptype.findAll", query = "SELECT a FROM Attributerelationshiptype a"),
    @NamedQuery(name = "Attributerelationshiptype.findById", query = "SELECT a FROM Attributerelationshiptype a WHERE a.id = :id"),
    @NamedQuery(name = "Attributerelationshiptype.findByEmpty", query = "SELECT a FROM Attributerelationshiptype a WHERE a.empty = :empty"),
    @NamedQuery(name = "Attributerelationshiptype.findByAttachGroup", query = "SELECT a FROM Attributerelationshiptype a WHERE a.attachGroup = :attachGroup"),
    @NamedQuery(name = "Attributerelationshiptype.findByAttachGroups", query = "SELECT a FROM Attributerelationshiptype a WHERE a.attachGroups = :attachGroups"),
    @NamedQuery(name = "Attributerelationshiptype.findByPrimaryMeasureReference", query = "SELECT a FROM Attributerelationshiptype a WHERE a.primaryMeasureReference = :primaryMeasureReference")})
public class Attributerelationshiptype implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Column(name = "empty")
    private Boolean empty;
    @Size(max = 200)
    @Column(name = "attachGroup", length = 200)
    private String attachGroup;
    @Column(name = "attachGroups")
    private Boolean attachGroups;
    @Size(max = 200)
    @Column(name = "primaryMeasureReference", length = 200)
    private String primaryMeasureReference;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "attributerelationshiptype")
    private List<Attributerelationshipattachgroups> attributerelationshipattachgroupsList;
    @OneToMany(mappedBy = "attributeRelationshipType")
    private List<Datastructurecomponents> datastructurecomponentsList;

    public Attributerelationshiptype() {
    }

    public Attributerelationshiptype(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Boolean getEmpty() {
        return empty;
    }

    public void setEmpty(Boolean empty) {
        this.empty = empty;
    }

    public String getAttachGroup() {
        return attachGroup;
    }

    public void setAttachGroup(String attachGroup) {
        this.attachGroup = attachGroup;
    }

    public Boolean getAttachGroups() {
        return attachGroups;
    }

    public void setAttachGroups(Boolean attachGroups) {
        this.attachGroups = attachGroups;
    }

    public String getPrimaryMeasureReference() {
        return primaryMeasureReference;
    }

    public void setPrimaryMeasureReference(String primaryMeasureReference) {
        this.primaryMeasureReference = primaryMeasureReference;
    }

    @XmlTransient
    public List<Attributerelationshipattachgroups> getAttributerelationshipattachgroupsList() {
        return attributerelationshipattachgroupsList;
    }

    public void setAttributerelationshipattachgroupsList(List<Attributerelationshipattachgroups> attributerelationshipattachgroupsList) {
        this.attributerelationshipattachgroupsList = attributerelationshipattachgroupsList;
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
        if (!(object instanceof Attributerelationshiptype)) {
            return false;
        }
        Attributerelationshiptype other = (Attributerelationshiptype) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Attributerelationshiptype[ id=" + id + " ]";
    }
    
}
