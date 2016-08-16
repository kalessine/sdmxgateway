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
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "code", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Code.findAll", query = "SELECT c FROM Code c"),
    @NamedQuery(name = "Code.findByAgencyID", query = "SELECT c FROM Code c WHERE c.codePK.agencyID = :agencyID"),
    @NamedQuery(name = "Code.findByCodelistID", query = "SELECT c FROM Code c WHERE c.codePK.codelistID = :codelistID"),
    @NamedQuery(name = "Code.findByVersion", query = "SELECT c FROM Code c WHERE c.codePK.version = :version"),
    @NamedQuery(name = "Code.findById", query = "SELECT c FROM Code c WHERE c.codePK.id = :id"),
    @NamedQuery(name = "Code.findByParentCode", query = "SELECT c FROM Code c WHERE c.parentCode = :parentCode")})
public class Code implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CodePK codePK;
    @Size(max = 100)
    @Column(name = "parentCode", length = 100)
    private String parentCode;
    @JoinColumn(name = "annotations", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Annotated annotations;
    @JoinColumns({
        @JoinColumn(name = "AgencyID", referencedColumnName = "AgencyID", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "CodelistID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "Version", referencedColumnName = "Version", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false, fetch = FetchType.LAZY)
    private Codelist codelist;
    @JoinColumn(name = "name", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Name name;

    public Code() {
    }

    public Code(CodePK codePK) {
        this.codePK = codePK;
    }

    public Code(String agencyID, String codelistID, String version, String id) {
        this.codePK = new CodePK(agencyID, codelistID, version, id);
    }

    public CodePK getCodePK() {
        return codePK;
    }

    public void setCodePK(CodePK codePK) {
        this.codePK = codePK;
    }

    public String getParentCode() {
        return parentCode;
    }

    public void setParentCode(String parentCode) {
        this.parentCode = parentCode;
    }

    public Annotated getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotated annotations) {
        this.annotations = annotations;
    }

    public Codelist getCodelist() {
        return codelist;
    }

    public void setCodelist(Codelist codelist) {
        this.codelist = codelist;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codePK != null ? codePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Code)) {
            return false;
        }
        Code other = (Code) object;
        if ((this.codePK == null && other.codePK != null) || (this.codePK != null && !this.codePK.equals(other.codePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Code[ codePK=" + codePK + " ]";
    }
    
}
