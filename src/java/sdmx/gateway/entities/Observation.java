/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@Entity
@Table(name = "Observation", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Observation.findAll", query = "SELECT o FROM Observation o")
    , @NamedQuery(name = "Observation.findByDateUpdated", query = "SELECT o FROM Observation o WHERE o.dateUpdated = :dateUpdated")
    , @NamedQuery(name = "Observation.findByAgencyID", query = "SELECT o FROM Observation o WHERE o.observationPK.agencyID = :agencyID")
    , @NamedQuery(name = "Observation.findById", query = "SELECT o FROM Observation o WHERE o.observationPK.id = :id")
    , @NamedQuery(name = "Observation.findByVersion", query = "SELECT o FROM Observation o WHERE o.observationPK.version = :version")
    , @NamedQuery(name = "Observation.findByObservation", query = "SELECT o FROM Observation o WHERE o.observation = :observation")})
public class Observation implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ObservationPK observationPK;
    @Column(name = "dateUpdated")
    @Temporal(TemporalType.DATE)
    private Date dateUpdated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "observation", nullable = false)
    private long observation;
    @JoinColumns({
        @JoinColumn(name = "agencyID", referencedColumnName = "agencyID", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "Id", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "version", referencedColumnName = "version", nullable = false, insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Dataflow dataflow;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "observation")
    private Value value;

    public Observation() {
    }

    public Observation(ObservationPK observationPK) {
        this.observationPK = observationPK;
    }

    public Observation(ObservationPK observationPK, long observation) {
        this.observationPK = observationPK;
        this.observation = observation;
    }

    public Observation(String agencyID, String id, String version) {
        this.observationPK = new ObservationPK(agencyID, id, version);
    }

    public ObservationPK getObservationPK() {
        return observationPK;
    }

    public void setObservationPK(ObservationPK observationPK) {
        this.observationPK = observationPK;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    public long getObservation() {
        return observation;
    }

    public void setObservation(long observation) {
        this.observation = observation;
    }

    public Dataflow getDataflow() {
        return dataflow;
    }

    public void setDataflow(Dataflow dataflow) {
        this.dataflow = dataflow;
    }

    public Value getValue() {
        return value;
    }

    public void setValue(Value value) {
        this.value = value;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (observationPK != null ? observationPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Observation)) {
            return false;
        }
        Observation other = (Observation) object;
        if ((this.observationPK == null && other.observationPK != null) || (this.observationPK != null && !this.observationPK.equals(other.observationPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Observation[ observationPK=" + observationPK + " ]";
    }
    
}
