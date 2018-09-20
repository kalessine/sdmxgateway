/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
<<<<<<< HEAD
import javax.validation.constraints.NotNull;
=======
>>>>>>> origin/master
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Owner
 */
@Entity
@Table(name = "Code", catalog = "repository", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"annotated"})
    , @UniqueConstraint(columnNames = {"name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Code.findAll", query = "SELECT c FROM Code c")
    , @NamedQuery(name = "Code.findByAgencyId", query = "SELECT c FROM Code c WHERE c.codePK.agencyId = :agencyId")
    , @NamedQuery(name = "Code.findById", query = "SELECT c FROM Code c WHERE c.codePK.id = :id")
    , @NamedQuery(name = "Code.findByVersion", query = "SELECT c FROM Code c WHERE c.codePK.version = :version")
    , @NamedQuery(name = "Code.findByParentCode", query = "SELECT c FROM Code c WHERE c.parentCode = :parentCode")
<<<<<<< HEAD
    , @NamedQuery(name = "Code.findByCodeId", query = "SELECT c FROM Code c WHERE c.codeId = :codeId")})
=======
    , @NamedQuery(name = "Code.findByCodeId", query = "SELECT c FROM Code c WHERE c.codePK.codeId = :codeId")})
>>>>>>> origin/master
public class Code implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected CodePK codePK;
    @Size(max = 255)
    @Column(name = "parentCode", length = 255)
    private String parentCode;
<<<<<<< HEAD
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "codeId", nullable = false, length = 255)
    private String codeId;
    @JoinColumn(name = "annotated", referencedColumnName = "annotated")
    @OneToOne
    private Annotated annotated;
=======
    @JoinColumn(name = "annotated", referencedColumnName = "annotated")
    @OneToOne
    private Annotation annotated;
>>>>>>> origin/master
    @JoinColumns({
        @JoinColumn(name = "agencyId", referencedColumnName = "agencyId", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "Id", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "version", referencedColumnName = "version", nullable = false, insertable = false, updatable = false)})
<<<<<<< HEAD
    @OneToOne(optional = false)
=======
    @ManyToOne(optional = false)
>>>>>>> origin/master
    private Codelist codelist;
    @JoinColumn(name = "name", referencedColumnName = "name")
    @OneToOne
    private Name name;

    public Code() {
    }

    public Code(CodePK codePK) {
        this.codePK = codePK;
    }

<<<<<<< HEAD
    public Code(CodePK codePK, String codeId) {
        this.codePK = codePK;
        this.codeId = codeId;
    }

    public Code(String agencyId, String id, String version) {
        this.codePK = new CodePK(agencyId, id, version);
=======
    public Code(String agencyId, String id, String version, String codeId) {
        this.codePK = new CodePK(agencyId, id, version, codeId);
>>>>>>> origin/master
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

<<<<<<< HEAD
    public String getCodeId() {
        return codeId;
=======
    public Annotation getAnnotated() {
        return annotated;
>>>>>>> origin/master
    }

<<<<<<< HEAD
    public void setCodeId(String codeId) {
        this.codeId = codeId;
    }

    public Annotated getAnnotated() {
        return annotated;
    }

    public void setAnnotated(Annotated annotated) {
=======
    public void setAnnotated(Annotation annotated) {
>>>>>>> origin/master
        this.annotated = annotated;
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
