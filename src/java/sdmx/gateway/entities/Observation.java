/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "Observation")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Observation.findAll", query = "SELECT o FROM Observation o")
    , @NamedQuery(name = "Observation.findByObservation", query = "SELECT o FROM Observation o WHERE o.observation = :observation")
    , @NamedQuery(name = "Observation.findByDateUpdated", query = "SELECT o FROM Observation o WHERE o.dateUpdated = :dateUpdated")})
public class Observation implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "observation")
    private Long observation;
    @Column(name = "dateUpdated")
    @Temporal(TemporalType.DATE)
    private Date dateUpdated;
    @ManyToMany(mappedBy = "observationList")
    private List<ComponentValue> componentValueList;
    @JoinColumn(name = "dataflow", referencedColumnName = "dataflow")
    @ManyToOne
    private Dataflow dataflow;

    public Observation() {
    }

    public Observation(Long observation) {
        this.observation = observation;
    }

    public Long getObservation() {
        return observation;
    }

    public void setObservation(Long observation) {
        this.observation = observation;
    }

    public Date getDateUpdated() {
        return dateUpdated;
    }

    public void setDateUpdated(Date dateUpdated) {
        this.dateUpdated = dateUpdated;
    }

    @XmlTransient
    public List<ComponentValue> getComponentValueList() {
        return componentValueList;
    }

    public void setComponentValueList(List<ComponentValue> componentValueList) {
        this.componentValueList = componentValueList;
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
        hash += (observation != null ? observation.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Observation)) {
            return false;
        }
        Observation other = (Observation) object;
        if ((this.observation == null && other.observation != null) || (this.observation != null && !this.observation.equals(other.observation))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Observation[ observation=" + observation + " ]";
    }
    
}
