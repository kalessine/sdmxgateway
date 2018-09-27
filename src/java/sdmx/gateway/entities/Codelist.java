/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author James
 */
@Entity
@Table(name = "Codelist", catalog = "repository", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"Annotations"})
    , @UniqueConstraint(columnNames = {"Name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Codelist.findAll", query = "SELECT c FROM Codelist c")
    , @NamedQuery(name = "Codelist.findByAgencyID", query = "SELECT c FROM Codelist c WHERE c.codelistPK.agencyID = :agencyID")
    , @NamedQuery(name = "Codelist.findById", query = "SELECT c FROM Codelist c WHERE c.codelistPK.id = :id")
    , @NamedQuery(name = "Codelist.findByVersion", query = "SELECT c FROM Codelist c WHERE c.codelistPK.version = :version")})
public class Codelist implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CodelistPK codelistPK;
    @JoinColumn(name = "Annotations", referencedColumnName = "Annotations")
    @OneToOne
    private Annotations annotations;
    @JoinColumn(name = "Name", referencedColumnName = "Name")
    @OneToOne
    private Name name;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "codelist")
    private Code code;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "codelist")
    private List<CodelistReference> codelistReferenceList;

    public Codelist() {
    }

    public Codelist(CodelistPK codelistPK) {
        this.codelistPK = codelistPK;
    }

    public Codelist(String agencyID, String id, String version) {
        this.codelistPK = new CodelistPK(agencyID, id, version);
    }

    public CodelistPK getCodelistPK() {
        return codelistPK;
    }

    public void setCodelistPK(CodelistPK codelistPK) {
        this.codelistPK = codelistPK;
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotations annotations) {
        this.annotations = annotations;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public Code getCode() {
        return code;
    }

    public void setCode(Code code) {
        this.code = code;
    }

    @XmlTransient
    public List<CodelistReference> getCodelistReferenceList() {
        return codelistReferenceList;
    }

    public void setCodelistReferenceList(List<CodelistReference> codelistReferenceList) {
        this.codelistReferenceList = codelistReferenceList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codelistPK != null ? codelistPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Codelist)) {
            return false;
        }
        Codelist other = (Codelist) object;
        if ((this.codelistPK == null && other.codelistPK != null) || (this.codelistPK != null && !this.codelistPK.equals(other.codelistPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Codelist[ codelistPK=" + codelistPK + " ]";
    }
    
}
