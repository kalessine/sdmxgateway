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
public class DatastructurecomponentsPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DataStructureAgencyID", nullable = false, length = 100)
    private String dataStructureAgencyID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "DataStructureID", nullable = false, length = 100)
    private String dataStructureID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "DataStructureVersion", nullable = false, length = 50)
    private String dataStructureVersion;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Position", nullable = false)
    private int position;

    public DatastructurecomponentsPK() {
    }

    public DatastructurecomponentsPK(String dataStructureAgencyID, String dataStructureID, String dataStructureVersion, int position) {
        this.dataStructureAgencyID = dataStructureAgencyID;
        this.dataStructureID = dataStructureID;
        this.dataStructureVersion = dataStructureVersion;
        this.position = position;
    }

    public String getDataStructureAgencyID() {
        return dataStructureAgencyID;
    }

    public void setDataStructureAgencyID(String dataStructureAgencyID) {
        this.dataStructureAgencyID = dataStructureAgencyID;
    }

    public String getDataStructureID() {
        return dataStructureID;
    }

    public void setDataStructureID(String dataStructureID) {
        this.dataStructureID = dataStructureID;
    }

    public String getDataStructureVersion() {
        return dataStructureVersion;
    }

    public void setDataStructureVersion(String dataStructureVersion) {
        this.dataStructureVersion = dataStructureVersion;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataStructureAgencyID != null ? dataStructureAgencyID.hashCode() : 0);
        hash += (dataStructureID != null ? dataStructureID.hashCode() : 0);
        hash += (dataStructureVersion != null ? dataStructureVersion.hashCode() : 0);
        hash += (int) position;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatastructurecomponentsPK)) {
            return false;
        }
        DatastructurecomponentsPK other = (DatastructurecomponentsPK) object;
        if ((this.dataStructureAgencyID == null && other.dataStructureAgencyID != null) || (this.dataStructureAgencyID != null && !this.dataStructureAgencyID.equals(other.dataStructureAgencyID))) {
            return false;
        }
        if ((this.dataStructureID == null && other.dataStructureID != null) || (this.dataStructureID != null && !this.dataStructureID.equals(other.dataStructureID))) {
            return false;
        }
        if ((this.dataStructureVersion == null && other.dataStructureVersion != null) || (this.dataStructureVersion != null && !this.dataStructureVersion.equals(other.dataStructureVersion))) {
            return false;
        }
        if (this.position != other.position) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.DatastructurecomponentsPK[ dataStructureAgencyID=" + dataStructureAgencyID + ", dataStructureID=" + dataStructureID + ", dataStructureVersion=" + dataStructureVersion + ", position=" + position + " ]";
    }
    
}
