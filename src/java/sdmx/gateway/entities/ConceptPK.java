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
public class ConceptPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ConceptSchemeAgencyID", nullable = false, length = 100)
    private String conceptSchemeAgencyID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ConceptSchemeID", nullable = false, length = 100)
    private String conceptSchemeID;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 50)
    @Column(name = "ConceptSchemeVersion", nullable = false, length = 50)
    private String conceptSchemeVersion;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "ID", nullable = false, length = 100)
    private String id;

    public ConceptPK() {
    }

    public ConceptPK(String conceptSchemeAgencyID, String conceptSchemeID, String conceptSchemeVersion, String id) {
        this.conceptSchemeAgencyID = conceptSchemeAgencyID;
        this.conceptSchemeID = conceptSchemeID;
        this.conceptSchemeVersion = conceptSchemeVersion;
        this.id = id;
    }

    public String getConceptSchemeAgencyID() {
        return conceptSchemeAgencyID;
    }

    public void setConceptSchemeAgencyID(String conceptSchemeAgencyID) {
        this.conceptSchemeAgencyID = conceptSchemeAgencyID;
    }

    public String getConceptSchemeID() {
        return conceptSchemeID;
    }

    public void setConceptSchemeID(String conceptSchemeID) {
        this.conceptSchemeID = conceptSchemeID;
    }

    public String getConceptSchemeVersion() {
        return conceptSchemeVersion;
    }

    public void setConceptSchemeVersion(String conceptSchemeVersion) {
        this.conceptSchemeVersion = conceptSchemeVersion;
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
        hash += (conceptSchemeAgencyID != null ? conceptSchemeAgencyID.hashCode() : 0);
        hash += (conceptSchemeID != null ? conceptSchemeID.hashCode() : 0);
        hash += (conceptSchemeVersion != null ? conceptSchemeVersion.hashCode() : 0);
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptPK)) {
            return false;
        }
        ConceptPK other = (ConceptPK) object;
        if ((this.conceptSchemeAgencyID == null && other.conceptSchemeAgencyID != null) || (this.conceptSchemeAgencyID != null && !this.conceptSchemeAgencyID.equals(other.conceptSchemeAgencyID))) {
            return false;
        }
        if ((this.conceptSchemeID == null && other.conceptSchemeID != null) || (this.conceptSchemeID != null && !this.conceptSchemeID.equals(other.conceptSchemeID))) {
            return false;
        }
        if ((this.conceptSchemeVersion == null && other.conceptSchemeVersion != null) || (this.conceptSchemeVersion != null && !this.conceptSchemeVersion.equals(other.conceptSchemeVersion))) {
            return false;
        }
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.ConceptPK[ conceptSchemeAgencyID=" + conceptSchemeAgencyID + ", conceptSchemeID=" + conceptSchemeID + ", conceptSchemeVersion=" + conceptSchemeVersion + ", id=" + id + " ]";
    }
    
}
