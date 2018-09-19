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
import javax.validation.constraints.Size;

/**
 *
 * @author James
 */
@Embeddable
public class ComponentPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "tableStructure", nullable = false)
    private long tableStructure;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false, length = 255)
    private String name;

    public ComponentPK() {
    }

    public ComponentPK(long tableStructure, String name) {
        this.tableStructure = tableStructure;
        this.name = name;
    }

    public long getTableStructure() {
        return tableStructure;
    }

    public void setTableStructure(long tableStructure) {
        this.tableStructure = tableStructure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) tableStructure;
        hash += (name != null ? name.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComponentPK)) {
            return false;
        }
        ComponentPK other = (ComponentPK) object;
        if (this.tableStructure != other.tableStructure) {
            return false;
        }
        if ((this.name == null && other.name != null) || (this.name != null && !this.name.equals(other.name))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.ComponentPK[ tableStructure=" + tableStructure + ", name=" + name + " ]";
    }
    
}
