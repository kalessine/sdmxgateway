/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author James
 */
@Embeddable
public class AttributerelationshipattachgroupsPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private long id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "index", nullable = false)
    private int index;

    public AttributerelationshipattachgroupsPK() {
    }

    public AttributerelationshipattachgroupsPK(long id, int index) {
        this.id = id;
        this.index = index;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) index;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AttributerelationshipattachgroupsPK)) {
            return false;
        }
        AttributerelationshipattachgroupsPK other = (AttributerelationshipattachgroupsPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.index != other.index) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.AttributerelationshipattachgroupsPK[ id=" + id + ", index=" + index + " ]";
    }
    
}
