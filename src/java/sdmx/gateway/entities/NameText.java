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
@Table(name = "NameText", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "NameText.findAll", query = "SELECT n FROM NameText n")
    , @NamedQuery(name = "NameText.findByLang", query = "SELECT n FROM NameText n WHERE n.nameTextPK.lang = :lang")
    , @NamedQuery(name = "NameText.findByText", query = "SELECT n FROM NameText n WHERE n.text = :text")
    , @NamedQuery(name = "NameText.findByName", query = "SELECT n FROM NameText n WHERE n.nameTextPK.name = :name")})
public class NameText implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected NameTextPK nameTextPK;
    @Size(max = 2147483647)
    @Column(name = "text", length = 2147483647)
    private String text;
    @JoinColumn(name = "lang", referencedColumnName = "lang", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Language language;
    @JoinColumn(name = "name", referencedColumnName = "name", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Name name1;

    public NameText() {
    }

    public NameText(NameTextPK nameTextPK) {
        this.nameTextPK = nameTextPK;
    }

    public NameText(String lang, long name) {
        this.nameTextPK = new NameTextPK(lang, name);
    }

    public NameTextPK getNameTextPK() {
        return nameTextPK;
    }

    public void setNameTextPK(NameTextPK nameTextPK) {
        this.nameTextPK = nameTextPK;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public Language getLanguage() {
        return language;
    }

    public void setLanguage(Language language) {
        this.language = language;
    }

    public Name getName1() {
        return name1;
    }

    public void setName1(Name name1) {
        this.name1 = name1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (nameTextPK != null ? nameTextPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof NameText)) {
            return false;
        }
        NameText other = (NameText) object;
        if ((this.nameTextPK == null && other.nameTextPK != null) || (this.nameTextPK != null && !this.nameTextPK.equals(other.nameTextPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.NameText[ nameTextPK=" + nameTextPK + " ]";
    }
    
}
