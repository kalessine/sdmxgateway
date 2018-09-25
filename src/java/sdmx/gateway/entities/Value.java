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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Owner
 */
@Entity
@Table(name = "Value", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Value.findAll", query = "SELECT v FROM Value v")
    , @NamedQuery(name = "Value.findByValue", query = "SELECT v FROM Value v WHERE v.value = :value")
    , @NamedQuery(name = "Value.findById", query = "SELECT v FROM Value v WHERE v.valuePK.id = :id")
    , @NamedQuery(name = "Value.findByVersion", query = "SELECT v FROM Value v WHERE v.valuePK.version = :version")
    , @NamedQuery(name = "Value.findByAgencyID", query = "SELECT v FROM Value v WHERE v.valuePK.agencyID = :agencyID")
    , @NamedQuery(name = "Value.findByColumnId", query = "SELECT v FROM Value v WHERE v.columnId = :columnId")})
public class Value implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ValuePK valuePK;
    @Size(max = 255)
    @Column(name = "value", length = 255)
    private String value;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "columnId", nullable = false, length = 255)
    private String columnId;
    @JoinColumns({
        @JoinColumn(name = "agencyID", referencedColumnName = "agencyID", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "Id", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "version", referencedColumnName = "version", nullable = false, insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Observation observation;

    public Value() {
    }

    public Value(ValuePK valuePK) {
        this.valuePK = valuePK;
    }

    public Value(ValuePK valuePK, String columnId) {
        this.valuePK = valuePK;
        this.columnId = columnId;
    }

    public Value(String id, String version, String agencyID) {
        this.valuePK = new ValuePK(id, version, agencyID);
    }

    public ValuePK getValuePK() {
        return valuePK;
    }

    public void setValuePK(ValuePK valuePK) {
        this.valuePK = valuePK;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public Observation getObservation() {
        return observation;
    }

    public void setObservation(Observation observation) {
        this.observation = observation;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (valuePK != null ? valuePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Value)) {
            return false;
        }
        Value other = (Value) object;
        if ((this.valuePK == null && other.valuePK != null) || (this.valuePK != null && !this.valuePK.equals(other.valuePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Value[ valuePK=" + valuePK + " ]";
    }
    
}
