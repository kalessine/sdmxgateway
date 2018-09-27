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
    @Size(min = 1, max = 255)
    @Column(name = "agencyID", nullable = false, length = 255)
    private String agencyID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "Id", nullable = false, length = 255)
    private String id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "version", nullable = false, length = 255)
    private String version;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "columnId", nullable = false, length = 255)
    private String columnId;

    public ComponentPK() {
    }

    public ComponentPK(String agencyID, String id, String version, String columnId) {
        this.agencyID = agencyID;
        this.id = id;
        this.version = version;
        this.columnId = columnId;
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

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agencyID != null ? agencyID.hashCode() : 0);
        hash += (id != null ? id.hashCode() : 0);
        hash += (version != null ? version.hashCode() : 0);
        hash += (columnId != null ? columnId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComponentPK)) {
            return false;
        }
        ComponentPK other = (ComponentPK) object;
        if ((this.agencyID == null && other.agencyID != null) || (this.agencyID != null && !this.agencyID.equals(other.agencyID))) {
            return false;
        }
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if ((this.version == null && other.version != null) || (this.version != null && !this.version.equals(other.version))) {
            return false;
        }
        if ((this.columnId == null && other.columnId != null) || (this.columnId != null && !this.columnId.equals(other.columnId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.ComponentPK[ agencyID=" + agencyID + ", id=" + id + ", version=" + version + ", columnId=" + columnId + " ]";
    }
    
}
