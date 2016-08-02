/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author James
 */
@Entity
@Table(name = "languages", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Languages.findAll", query = "SELECT l FROM Languages l"),
    @NamedQuery(name = "Languages.findByLang", query = "SELECT l FROM Languages l WHERE l.lang = :lang"),
    @NamedQuery(name = "Languages.findByName", query = "SELECT l FROM Languages l WHERE l.name = :name")})
public class Languages implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 10)
    @Column(name = "lang", nullable = false, length = 10)
    private String lang;
    @Size(max = 2000)
    @Column(name = "Name", length = 2000)
    private String name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "languages")
    private List<Nametext> nametextList;

    public Languages() {
    }

    public Languages(String lang) {
        this.lang = lang;
    }

    public String getLang() {
        return lang;
    }

    public void setLang(String lang) {
        this.lang = lang;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @XmlTransient
    public List<Nametext> getNametextList() {
        return nametextList;
    }

    public void setNametextList(List<Nametext> nametextList) {
        this.nametextList = nametextList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lang != null ? lang.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Languages)) {
            return false;
        }
        Languages other = (Languages) object;
        if ((this.lang == null && other.lang != null) || (this.lang != null && !this.lang.equals(other.lang))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Languages[ lang=" + lang + " ]";
    }
    
}
