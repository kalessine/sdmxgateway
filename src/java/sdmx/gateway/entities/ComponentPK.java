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
public class ComponentPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "dataflow")
    private long dataflow;
    @Basic(optional = false)
    @NotNull
    @Column(name = "position")
    private short position;

    public ComponentPK() {
    }

    public ComponentPK(long dataflow, short position) {
        this.dataflow = dataflow;
        this.position = position;
    }

    public long getDataflow() {
        return dataflow;
    }

    public void setDataflow(long dataflow) {
        this.dataflow = dataflow;
    }

    public short getPosition() {
        return position;
    }

    public void setPosition(short position) {
        this.position = position;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) dataflow;
        hash += (int) position;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComponentPK)) {
            return false;
        }
        ComponentPK other = (ComponentPK) object;
        if (this.dataflow != other.dataflow) {
            return false;
        }
        if (this.position != other.position) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.ComponentPK[ dataflow=" + dataflow + ", position=" + position + " ]";
    }
    
}
