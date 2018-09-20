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
@Table(catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DataStructureReference.findAll", query = "SELECT d FROM DataStructureReference d")
    , @NamedQuery(name = "DataStructureReference.findByStructure", query = "SELECT d FROM DataStructureReference d WHERE d.structure = :structure")})
public class DataStructureReference implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long structure;
    @JoinColumns({
        @JoinColumn(name = "agencyId", referencedColumnName = "agencyId")
        , @JoinColumn(name = "Id", referencedColumnName = "Id")
        , @JoinColumn(name = "version", referencedColumnName = "version")})
    @ManyToOne
    private DataStructure dataStructure;
    @OneToMany(mappedBy = "structure")
    private List<Dataflow> dataflowList;

    public DataStructureReference() {
    }

    public DataStructureReference(Long structure) {
        this.structure = structure;
    }

    public Long getStructure() {
        return structure;
    }

    public void setStructure(Long structure) {
        this.structure = structure;
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
        hash += (structure != null ? structure.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataStructureReference)) {
            return false;
        }
        DataStructureReference other = (DataStructureReference) object;
        if ((this.structure == null && other.structure != null) || (this.structure != null && !this.structure.equals(other.structure))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.DataStructureReference[ structure=" + structure + " ]";
    }
    
}
