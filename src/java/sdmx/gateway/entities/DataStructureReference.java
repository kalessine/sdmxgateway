/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author James
 */
@Entity
@Table(name = "DataStructureReference", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DataStructureReference.findAll", query = "SELECT d FROM DataStructureReference d")
    , @NamedQuery(name = "DataStructureReference.findByReference", query = "SELECT d FROM DataStructureReference d WHERE d.reference = :reference")})
public class DataStructureReference implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "reference", nullable = false)
    private Long reference;
    @JoinColumns({
        @JoinColumn(name = "agencyID", referencedColumnName = "agencyID")
        , @JoinColumn(name = "Id", referencedColumnName = "Id")
        , @JoinColumn(name = "version", referencedColumnName = "version")})
    @ManyToOne
    private DataStructure dataStructure;
    @OneToMany(mappedBy = "structure")
    private List<Dataflow> dataflowList;

    public DataStructureReference() {
    }

    public DataStructureReference(Long reference) {
        this.reference = reference;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public DataStructure getDataStructure() {
        return dataStructure;
    }

    public void setDataStructure(DataStructure dataStructure) {
        this.dataStructure = dataStructure;
    }

    @XmlTransient
    public List<Dataflow> getDataflowList() {
        return dataflowList;
    }

    public void setDataflowList(List<Dataflow> dataflowList) {
        this.dataflowList = dataflowList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reference != null ? reference.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataStructureReference)) {
            return false;
        }
        DataStructureReference other = (DataStructureReference) object;
        if ((this.reference == null && other.reference != null) || (this.reference != null && !this.reference.equals(other.reference))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.DataStructureReference[ reference=" + reference + " ]";
    }
    
}
