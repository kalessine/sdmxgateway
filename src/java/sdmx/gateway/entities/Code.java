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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@Entity
@Table(name = "Code", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})
    , @UniqueConstraint(columnNames = {"annotations"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Code.findAll", query = "SELECT c FROM Code c")
    , @NamedQuery(name = "Code.findByAgencyId", query = "SELECT c FROM Code c WHERE c.codePK.agencyId = :agencyId")
    , @NamedQuery(name = "Code.findById", query = "SELECT c FROM Code c WHERE c.codePK.id = :id")
    , @NamedQuery(name = "Code.findByVersion", query = "SELECT c FROM Code c WHERE c.codePK.version = :version")
    , @NamedQuery(name = "Code.findByParentCode", query = "SELECT c FROM Code c WHERE c.parentCode = :parentCode")
    , @NamedQuery(name = "Code.findByCodeId", query = "SELECT c FROM Code c WHERE c.codePK.codeId = :codeId")})
public class Code implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CodePK codePK;
    @Size(max = 255)
    @Column(name = "parentCode", length = 255)
    private String parentCode;
    @JoinColumn(name = "annotations", referencedColumnName = "annotations")
    @OneToOne
    private Annotations annotations;
    @JoinColumns({
        @JoinColumn(name = "agencyId", referencedColumnName = "agencyId", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "id", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "version", referencedColumnName = "version", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Codelist codelist;
    @JoinColumn(name = "name", referencedColumnName = "name")
    @OneToOne
    private Name name;

    public Code() {
    }

    public Code(CodePK codePK) {
        this.codePK = codePK;
    }

    public Code(String agencyId, String id, String version, String codeId) {
        this.codePK = new CodePK(agencyId, id, version, codeId);
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

    public Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotations annotations) {
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
