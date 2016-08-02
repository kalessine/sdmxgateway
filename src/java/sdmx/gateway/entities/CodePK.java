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
public class CodePK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "AgencyID", nullable = false, length = 100)
    private String agencyID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "CodelistID", nullable = false, length = 100)
    private String codelistID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "Version", nullable = false, length = 50)
    private String version;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "id", nullable = false, length = 100)
    private String id;

    public CodePK() {
    }

    public CodePK(String agencyID, String codelistID, String version, String id) {
        this.agencyID = agencyID;
        this.codelistID = codelistID;
        this.version = version;
        this.id = id;
    }

    public String getAgencyID() {
        return agencyID;
    }

    public void setAgencyID(String agencyID) {
        this.agencyID = agencyID;
    }

    public String getCodelistID() {
        return codelistID;
    }

    public void setCodelistID(String codelistID) {
        this.codelistID = codelistID;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agencyID != null ? agencyID.hashCode() : 0);
        hash += (codelistID != null ? codelistID.hashCode() : 0);
        hash += (version != null ? version.hashCode() : 0);
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodePK)) {
            return false;
        }
        CodePK other = (CodePK) object;
        if ((this.agencyID == null && other.agencyID != null) || (this.agencyID != null && !this.agencyID.equals(other.agencyID))) {
            return false;
        }
        if ((this.codelistID == null && other.codelistID != null) || (this.codelistID != null && !this.codelistID.equals(other.codelistID))) {
            return false;
        }
        if ((this.version == null && other.version != null) || (this.version != null && !this.version.equals(other.version))) {
            return false;
        }
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.CodePK[ agencyID=" + agencyID + ", codelistID=" + codelistID + ", version=" + version + ", id=" + id + " ]";
    }
    
}
