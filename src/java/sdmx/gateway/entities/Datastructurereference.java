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
@Table(name = "datastructurereference", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Datastructurereference.findAll", query = "SELECT d FROM Datastructurereference d"),
    @NamedQuery(name = "Datastructurereference.findById", query = "SELECT d FROM Datastructurereference d WHERE d.id = :id")})
public class Datastructurereference implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id", nullable = false)
    private Long id;
    @OneToMany(mappedBy = "structure")
    private List<Dataflow> dataflowList;
    @JoinColumns({
        @JoinColumn(name = "DataStructureAgencyID", referencedColumnName = "AgencyID", nullable = false),
        @JoinColumn(name = "DataStructureID", referencedColumnName = "ID", nullable = false),
        @JoinColumn(name = "DataStructureVersion", referencedColumnName = "Version", nullable = false)})
    @ManyToOne(optional = false)
    private Datastructure datastructure;

    public Datastructurereference() {
    }

    public Datastructurereference(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @XmlTransient
    public List<Dataflow> getDataflowList() {
        return dataflowList;
    }

    public void setDataflowList(List<Dataflow> dataflowList) {
        this.dataflowList = dataflowList;
    }

    public Datastructure getDatastructure() {
        return datastructure;
    }

    public void setDatastructure(Datastructure datastructure) {
        this.datastructure = datastructure;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Datastructurereference)) {
            return false;
        }
        Datastructurereference other = (Datastructurereference) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Datastructurereference[ id=" + id + " ]";
    }
    
}
