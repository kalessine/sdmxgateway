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
 * @author Owner
 */
@Embeddable
public class CodePK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "agencyId", nullable = false, length = 255)
    private String agencyId;
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
<<<<<<< HEAD
=======
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "codeId", nullable = false, length = 255)
    private String codeId;
>>>>>>> origin/master

    public CodePK() {
    }

<<<<<<< HEAD
    public CodePK(String agencyId, String id, String version) {
=======
    public CodePK(String agencyId, String id, String version, String codeId) {
>>>>>>> origin/master
        this.agencyId = agencyId;
        this.id = id;
        this.version = version;
<<<<<<< HEAD
=======
        this.codeId = codeId;
>>>>>>> origin/master
    }

    public String getAgencyId() {
        return agencyId;
    }

    public void setAgencyId(String agencyId) {
        this.agencyId = agencyId;
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

<<<<<<< HEAD
=======
    public String getCodeId() {
        return codeId;
    }

    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

>>>>>>> origin/master
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (agencyId != null ? agencyId.hashCode() : 0);
        hash += (id != null ? id.hashCode() : 0);
        hash += (version != null ? version.hashCode() : 0);
<<<<<<< HEAD
=======
        hash += (codeId != null ? codeId.hashCode() : 0);
>>>>>>> origin/master
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodePK)) {
            return false;
        }
        CodePK other = (CodePK) object;
        if ((this.agencyId == null && other.agencyId != null) || (this.agencyId != null && !this.agencyId.equals(other.agencyId))) {
<<<<<<< HEAD
=======
            return false;
        }
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if ((this.version == null && other.version != null) || (this.version != null && !this.version.equals(other.version))) {
>>>>>>> origin/master
            return false;
        }
<<<<<<< HEAD
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        if ((this.version == null && other.version != null) || (this.version != null && !this.version.equals(other.version))) {
=======
        if ((this.codeId == null && other.codeId != null) || (this.codeId != null && !this.codeId.equals(other.codeId))) {
>>>>>>> origin/master
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
<<<<<<< HEAD
        return "sdmx.gateway.entities.CodePK[ agencyId=" + agencyId + ", id=" + id + ", version=" + version + " ]";
=======
        return "sdmx.gateway.entities.CodePK[ agencyId=" + agencyId + ", id=" + id + ", version=" + version + ", codeId=" + codeId + " ]";
>>>>>>> origin/master
    }
    
}
