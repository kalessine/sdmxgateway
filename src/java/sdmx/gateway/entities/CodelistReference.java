/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@Entity
@Table(name = "CodelistReference", catalog = "repository", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"agencyId", "Id", "version"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "CodelistReference.findAll", query = "SELECT c FROM CodelistReference c")
    , @NamedQuery(name = "CodelistReference.findByCodelist", query = "SELECT c FROM CodelistReference c WHERE c.codelist = :codelist")})
public class CodelistReference implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "codelist", nullable = false)
    private Long codelist;
    @OneToOne(mappedBy = "codelistEnumeration")
    private DataStructureComponent dataStructureComponent;
    @JoinColumns({
        @JoinColumn(name = "agencyId", referencedColumnName = "agencyId")
        , @JoinColumn(name = "Id", referencedColumnName = "Id")
        , @JoinColumn(name = "version", referencedColumnName = "version")})
    @OneToOne
    private Codelist codelist1;

    public CodelistReference() {
    }

    public CodelistReference(Long codelist) {
        this.codelist = codelist;
    }

    public Long getCodelist() {
        return codelist;
    }

    public void setCodelist(Long codelist) {
        this.codelist = codelist;
    }

    public DataStructureComponent getDataStructureComponent() {
        return dataStructureComponent;
    }

    public void setDataStructureComponent(DataStructureComponent dataStructureComponent) {
        this.dataStructureComponent = dataStructureComponent;
    }

    public Codelist getCodelist1() {
        return codelist1;
    }

    public void setCodelist1(Codelist codelist1) {
        this.codelist1 = codelist1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (codelist != null ? codelist.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CodelistReference)) {
            return false;
        }
        CodelistReference other = (CodelistReference) object;
        if ((this.codelist == null && other.codelist != null) || (this.codelist != null && !this.codelist.equals(other.codelist))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.CodelistReference[ codelist=" + codelist + " ]";
    }
    
}
