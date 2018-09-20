/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import java.math.BigInteger;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
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
    @NamedQuery(name = "Observation.findAll", query = "SELECT o FROM Observation o")
    , @NamedQuery(name = "Observation.findById", query = "SELECT o FROM Observation o WHERE o.id = :id")
    , @NamedQuery(name = "Observation.findByDateUpdated", query = "SELECT o FROM Observation o WHERE o.dateUpdated = :dateUpdated")
    , @NamedQuery(name = "Observation.findById1", query = "SELECT o FROM Observation o WHERE o.id1 = :id1")})
public class Observation implements Serializable {

    private static final long serialVersionUID = 1L;
    private BigInteger id;
    @Temporal(TemporalType.DATE)
    private Date dateUpdated;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long id1;
    @ManyToMany(mappedBy = "observationList")
    private List<ColumnValue> columnValueList;
    @JoinColumns({
        @JoinColumn(name = "id1", referencedColumnName = "id", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "agencyId", referencedColumnName = "agencyId")
        , @JoinColumn(name = "Id", referencedColumnName = "Id")
        , @JoinColumn(name = "version", referencedColumnName = "version")})
    @ManyToOne(optional = false)
    private Dataflow dataflow;

    public Observation() {
    }

    public Observation(Long id1) {
        this.id1 = id1;
    }

    public BigInteger getId() {
        return id;
    }

    public void setId(BigInteger id) {
        this.id = id;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public Long getId1() {
        return id1;
    }

    public void setId1(Long id1) {
        this.id1 = id1;
    }

    @XmlTransient
    public List<ColumnValue> getColumnValueList() {
        return columnValueList;
    }

    public void setColumnValueList(List<ColumnValue> columnValueList) {
        this.columnValueList = columnValueList;
    }

    public Dataflow getDataflow() {
        return dataflow;
    }

    public void setDataflow(Dataflow dataflow) {
        this.dataflow = dataflow;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id1 != null ? id1.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Observation)) {
            return false;
        }
        Observation other = (Observation) object;
        if ((this.id1 == null && other.id1 != null) || (this.id1 != null && !this.id1.equals(other.id1))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Observation[ id1=" + id1 + " ]";
    }
    
}
