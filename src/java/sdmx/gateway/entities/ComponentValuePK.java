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
public class ComponentValuePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "dataflow", nullable = false)
    private long dataflow;
    @Basic(optional = false)
    @NotNull
    @Column(name = "index", nullable = false)
    private short index;

    public ComponentValuePK() {
    }

    public ComponentValuePK(long dataflow, short index) {
        this.dataflow = dataflow;
        this.index = index;
    }

    public long getDataflow() {
        return dataflow;
    }

    public void setDataflow(long dataflow) {
        this.dataflow = dataflow;
    }

    public short getIndex() {
        return index;
    }

    public void setIndex(short index) {
        this.index = index;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) dataflow;
        hash += (int) index;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComponentValuePK)) {
            return false;
        }
        ComponentValuePK other = (ComponentValuePK) object;
        if (this.dataflow != other.dataflow) {
            return false;
        }
        if (this.index != other.index) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.ComponentValuePK[ dataflow=" + dataflow + ", index=" + index + " ]";
    }
    
}
