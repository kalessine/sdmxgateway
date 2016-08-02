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
public class NametextPK implements Serializable {
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private long id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "lang", nullable = false, length = 10)
    private String lang;

    public NametextPK() {
    }

    public NametextPK(long id, String lang) {
        this.id = id;
        this.lang = lang;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (lang != null ? lang.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NametextPK)) {
            return false;
        }
        NametextPK other = (NametextPK) object;
        if (this.id != other.id) {
            return false;
        }
        if ((this.lang == null && other.lang != null) || (this.lang != null && !this.lang.equals(other.lang))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.NametextPK[ id=" + id + ", lang=" + lang + " ]";
    }
    
}
