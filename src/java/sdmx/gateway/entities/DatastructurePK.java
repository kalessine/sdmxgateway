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
public class DatastructurePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "AgencyID", nullable = false, length = 100)
    private String agencyID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ID", nullable = false, length = 100)
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Version", nullable = false, length = 50)
    private String version;

    public DatastructurePK() {
    }

    public DatastructurePK(String agencyID, String id, String version) {
        this.agencyID = agencyID;
        this.id = id;
        this.version = version;
    }

    public String getAgencyID() {
        return agencyID;
    }

    public void setAgencyID(String agencyID) {
        this.agencyID = agencyID;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agencyID != null ? agencyID.hashCode() : 0);
        hash += (id != null ? id.hashCode() : 0);
        hash += (version != null ? version.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DatastructurePK)) {
            return false;
        }
        DatastructurePK other = (DatastructurePK) object;
        if ((this.agencyID == null && other.agencyID != null) || (this.agencyID != null && !this.agencyID.equals(other.agencyID))) {
            return false;
        }
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if ((this.version == null && other.version != null) || (this.version != null && !this.version.equals(other.version))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.DatastructurePK[ agencyID=" + agencyID + ", id=" + id + ", version=" + version + " ]";
    }
    
}
