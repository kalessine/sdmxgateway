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
public class NameTextPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "lang", nullable = false, length = 255)
    private String lang;
    @Basic(optional = false)
    @NotNull
    @Column(name = "name", nullable = false)
    private long name;

    public NameTextPK() {
    }

    public NameTextPK(String lang, long name) {
        this.lang = lang;
        this.name = name;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public long getName() {
        return name;
    }

    public void setName(long name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lang != null ? lang.hashCode() : 0);
        hash += (int) name;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NameTextPK)) {
            return false;
        }
        NameTextPK other = (NameTextPK) object;
        if ((this.lang == null && other.lang != null) || (this.lang != null && !this.lang.equals(other.lang))) {
            return false;
        }
        if (this.name != other.name) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.NameTextPK[ lang=" + lang + ", name=" + name + " ]";
    }
    
}
