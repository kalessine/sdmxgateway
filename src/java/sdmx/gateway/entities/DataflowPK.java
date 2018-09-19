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
public class DataflowPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "agencyId", nullable = false, length = 255)
    private String agencyId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Id", nullable = false, length = 255)
    private String id1;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "version", nullable = false, length = 255)
    private String version;

    public DataflowPK() {
    }

    public DataflowPK(long id, String agencyId, String id1, String version) {
        this.id = id;
        this.agencyId = agencyId;
        this.id1 = id1;
        this.version = version;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
    }

    public String getId1() {
        return id1;
    }

    public void setId1(String id1) {
        this.id1 = id1;
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
        hash += (int) id;
        hash += (agencyId != null ? agencyId.hashCode() : 0);
        hash += (id1 != null ? id1.hashCode() : 0);
        hash += (version != null ? version.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataflowPK)) {
            return false;
        }
        DataflowPK other = (DataflowPK) object;
        if (this.id != other.id) {
            return false;
        }
        if ((this.agencyId == null && other.agencyId != null) || (this.agencyId != null && !this.agencyId.equals(other.agencyId))) {
            return false;
        }
        if ((this.id1 == null && other.id1 != null) || (this.id1 != null && !this.id1.equals(other.id1))) {
            return false;
        }
        if ((this.version == null && other.version != null) || (this.version != null && !this.version.equals(other.version))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.DataflowPK[ id=" + id + ", agencyId=" + agencyId + ", id1=" + id1 + ", version=" + version + " ]";
    }
    
}
