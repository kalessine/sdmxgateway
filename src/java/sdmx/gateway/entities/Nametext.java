/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@Entity
@Table(name = "nametext", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Nametext.findAll", query = "SELECT n FROM Nametext n"),
    @NamedQuery(name = "Nametext.findById", query = "SELECT n FROM Nametext n WHERE n.nametextPK.id = :id"),
    @NamedQuery(name = "Nametext.findByLang", query = "SELECT n FROM Nametext n WHERE n.nametextPK.lang = :lang")})
public class Nametext implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NametextPK nametextPK;
    @Lob
    @Size(max = 65535)
    @Column(name = "text", length = 65535)
    private String text;
    @JoinColumn(name = "id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Name name;
    @JoinColumn(name = "lang", referencedColumnName = "lang", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Languages languages;

    public Nametext() {
    }

    public Nametext(NametextPK nametextPK) {
        this.nametextPK = nametextPK;
    }

    public Nametext(long id, String lang) {
        this.nametextPK = new NametextPK(id, lang);
    }

    public NametextPK getNametextPK() {
        return nametextPK;
    }

    public void setNametextPK(NametextPK nametextPK) {
        this.nametextPK = nametextPK;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Languages getLanguages() {
        return languages;
    }

    public void setLanguages(Languages languages) {
        this.languages = languages;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nametextPK != null ? nametextPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Nametext)) {
            return false;
        }
        Nametext other = (Nametext) object;
        if ((this.nametextPK == null && other.nametextPK != null) || (this.nametextPK != null && !this.nametextPK.equals(other.nametextPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Nametext[ nametextPK=" + nametextPK + " ]";
    }
    
}
