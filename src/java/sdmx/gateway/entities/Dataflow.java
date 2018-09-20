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
import javax.persistence.ManyToOne;
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
@Table(catalog = "repository", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dataflow.findAll", query = "SELECT d FROM Dataflow d")
    , @NamedQuery(name = "Dataflow.findById", query = "SELECT d FROM Dataflow d WHERE d.dataflowPK.id = :id")
    , @NamedQuery(name = "Dataflow.findByAgencyId", query = "SELECT d FROM Dataflow d WHERE d.dataflowPK.agencyId = :agencyId")
    , @NamedQuery(name = "Dataflow.findById1", query = "SELECT d FROM Dataflow d WHERE d.dataflowPK.id1 = :id1")
    , @NamedQuery(name = "Dataflow.findByVersion", query = "SELECT d FROM Dataflow d WHERE d.dataflowPK.version = :version")})
public class Dataflow implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DataflowPK dataflowPK;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "dataflow")
    private List<Observation> observationList;
    @JoinColumn(name = "structure", referencedColumnName = "structure")
    @ManyToOne
    private DataStructureReference structure;
    @JoinColumn(name = "name", referencedColumnName = "name")
    @OneToOne
    private Name name;
    @JoinColumn(name = "tableStructure", referencedColumnName = "tableStructure")
    @ManyToOne
    private TableStructure tableStructure;

    public Dataflow() {
    }

    public Dataflow(DataflowPK dataflowPK) {
        this.dataflowPK = dataflowPK;
    }

    public Dataflow(long id, String agencyId, String id1, String version) {
        this.dataflowPK = new DataflowPK(id, agencyId, id1, version);
    }

    public DataflowPK getDataflowPK() {
        return dataflowPK;
    }

    public void setDataflowPK(DataflowPK dataflowPK) {
        this.dataflowPK = dataflowPK;
    }

    @XmlTransient
    public List<Observation> getObservationList() {
        return observationList;
    }

    public void setObservationList(List<Observation> observationList) {
        this.observationList = observationList;
    }

    public DataStructureReference getStructure() {
        return structure;
    }

    public void setStructure(DataStructureReference structure) {
        this.structure = structure;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public TableStructure getTableStructure() {
        return tableStructure;
    }

    public void setTableStructure(TableStructure tableStructure) {
        this.tableStructure = tableStructure;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (dataflowPK != null ? dataflowPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dataflow)) {
            return false;
        }
        Dataflow other = (Dataflow) object;
        if ((this.dataflowPK == null && other.dataflowPK != null) || (this.dataflowPK != null && !this.dataflowPK.equals(other.dataflowPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Dataflow[ dataflowPK=" + dataflowPK + " ]";
    }
    
}
