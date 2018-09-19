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
public class ConceptPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ConceptScheme_agencyId", nullable = false, length = 255)
    private String conceptSchemeagencyId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ConceptScheme_Id", nullable = false, length = 255)
    private String conceptSchemeId;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "ConceptScheme_version", nullable = false, length = 255)
    private String conceptSchemeversion;

    public ConceptPK() {
    }

    public ConceptPK(String conceptSchemeagencyId, String conceptSchemeId, String conceptSchemeversion) {
        this.conceptSchemeagencyId = conceptSchemeagencyId;
        this.conceptSchemeId = conceptSchemeId;
        this.conceptSchemeversion = conceptSchemeversion;
    }

    public String getConceptSchemeagencyId() {
        return conceptSchemeagencyId;
    }

    public void setConceptSchemeagencyId(String conceptSchemeagencyId) {
        this.conceptSchemeagencyId = conceptSchemeagencyId;
    }

    public String getConceptSchemeId() {
        return conceptSchemeId;
    }

    public void setConceptSchemeId(String conceptSchemeId) {
        this.conceptSchemeId = conceptSchemeId;
    }

    public String getConceptSchemeversion() {
        return conceptSchemeversion;
    }

    public void setConceptSchemeversion(String conceptSchemeversion) {
        this.conceptSchemeversion = conceptSchemeversion;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conceptSchemeagencyId != null ? conceptSchemeagencyId.hashCode() : 0);
        hash += (conceptSchemeId != null ? conceptSchemeId.hashCode() : 0);
        hash += (conceptSchemeversion != null ? conceptSchemeversion.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptPK)) {
            return false;
        }
        ConceptPK other = (ConceptPK) object;
        if ((this.conceptSchemeagencyId == null && other.conceptSchemeagencyId != null) || (this.conceptSchemeagencyId != null && !this.conceptSchemeagencyId.equals(other.conceptSchemeagencyId))) {
            return false;
        }
        if ((this.conceptSchemeId == null && other.conceptSchemeId != null) || (this.conceptSchemeId != null && !this.conceptSchemeId.equals(other.conceptSchemeId))) {
            return false;
        }
        if ((this.conceptSchemeversion == null && other.conceptSchemeversion != null) || (this.conceptSchemeversion != null && !this.conceptSchemeversion.equals(other.conceptSchemeversion))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.ConceptPK[ conceptSchemeagencyId=" + conceptSchemeagencyId + ", conceptSchemeId=" + conceptSchemeId + ", conceptSchemeversion=" + conceptSchemeversion + " ]";
    }
    
}
