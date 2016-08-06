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
@Table(name = "attributerelationshipattachgroups", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Attributerelationshipattachgroups.findAll", query = "SELECT a FROM Attributerelationshipattachgroups a"),
    @NamedQuery(name = "Attributerelationshipattachgroups.findById", query = "SELECT a FROM Attributerelationshipattachgroups a WHERE a.attributerelationshipattachgroupsPK.id = :id"),
    @NamedQuery(name = "Attributerelationshipattachgroups.findByIndex", query = "SELECT a FROM Attributerelationshipattachgroups a WHERE a.attributerelationshipattachgroupsPK.index = :index"),
    @NamedQuery(name = "Attributerelationshipattachgroups.findByAttachGroup", query = "SELECT a FROM Attributerelationshipattachgroups a WHERE a.attachGroup = :attachGroup")})
public class Attributerelationshipattachgroups implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected AttributerelationshipattachgroupsPK attributerelationshipattachgroupsPK;
    @Size(max = 200)
    @Column(name = "attachGroup", length = 200)
    private String attachGroup;
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Attributerelationshiptype attributerelationshiptype;

    public Attributerelationshipattachgroups() {
    }

    public Attributerelationshipattachgroups(AttributerelationshipattachgroupsPK attributerelationshipattachgroupsPK) {
        this.attributerelationshipattachgroupsPK = attributerelationshipattachgroupsPK;
    }

    public Attributerelationshipattachgroups(long id, int index) {
        this.attributerelationshipattachgroupsPK = new AttributerelationshipattachgroupsPK(id, index);
    }

    public AttributerelationshipattachgroupsPK getAttributerelationshipattachgroupsPK() {
        return attributerelationshipattachgroupsPK;
    }

    public void setAttributerelationshipattachgroupsPK(AttributerelationshipattachgroupsPK attributerelationshipattachgroupsPK) {
        this.attributerelationshipattachgroupsPK = attributerelationshipattachgroupsPK;
    }

    public String getAttachGroup() {
        return attachGroup;
    }

    public void setAttachGroup(String attachGroup) {
        this.attachGroup = attachGroup;
    }

    public Attributerelationshiptype getAttributerelationshiptype() {
        return attributerelationshiptype;
    }

    public void setAttributerelationshiptype(Attributerelationshiptype attributerelationshiptype) {
        this.attributerelationshiptype = attributerelationshiptype;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attributerelationshipattachgroupsPK != null ? attributerelationshipattachgroupsPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attributerelationshipattachgroups)) {
            return false;
        }
        Attributerelationshipattachgroups other = (Attributerelationshipattachgroups) object;
        if ((this.attributerelationshipattachgroupsPK == null && other.attributerelationshipattachgroupsPK != null) || (this.attributerelationshipattachgroupsPK != null && !this.attributerelationshipattachgroupsPK.equals(other.attributerelationshipattachgroupsPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Attributerelationshipattachgroups[ attributerelationshipattachgroupsPK=" + attributerelationshipattachgroupsPK + " ]";
    }
    
}
