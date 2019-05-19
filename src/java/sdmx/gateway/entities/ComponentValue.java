/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author James
 */
@Entity
@Table(name = "ComponentValue")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComponentValue.findAll", query = "SELECT c FROM ComponentValue c")
    , @NamedQuery(name = "ComponentValue.findByDataflow", query = "SELECT c FROM ComponentValue c WHERE c.componentValuePK.dataflow = :dataflow")
    , @NamedQuery(name = "ComponentValue.findByPosition", query = "SELECT c FROM ComponentValue c WHERE c.componentValuePK.position = :position")
    , @NamedQuery(name = "ComponentValue.findByIndex", query = "SELECT c FROM ComponentValue c WHERE c.index = :index")
    , @NamedQuery(name = "ComponentValue.findByValue", query = "SELECT c FROM ComponentValue c WHERE c.value = :value")})
public class ComponentValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComponentValuePK componentValuePK;
    @Column(name = "index")
    private Short index;
    @Size(max = 255)
    @Column(name = "value")
    private String value;
    @JoinTable(name = "Value", joinColumns = {
        @JoinColumn(name = "dataflow", referencedColumnName = "dataflow")
        , @JoinColumn(name = "position", referencedColumnName = "position")}, inverseJoinColumns = {
        @JoinColumn(name = "observation", referencedColumnName = "observation")})
    @ManyToMany
    private List<Observation> observationList;
    @JoinColumns({
        @JoinColumn(name = "dataflow", referencedColumnName = "dataflow", insertable = false, updatable = false)
        , @JoinColumn(name = "position", referencedColumnName = "position", insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Component component;

    public ComponentValue() {
    }

    public ComponentValue(ComponentValuePK componentValuePK) {
        this.componentValuePK = componentValuePK;
    }

    public ComponentValue(long dataflow, short position) {
        this.componentValuePK = new ComponentValuePK(dataflow, position);
    }

    public ComponentValuePK getComponentValuePK() {
        return componentValuePK;
    }

    public void setComponentValuePK(ComponentValuePK componentValuePK) {
        this.componentValuePK = componentValuePK;
    }

    public Short getIndex() {
        return index;
    }

    public void setIndex(Short index) {
        this.index = index;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    @XmlTransient
    public List<Observation> getObservationList() {
        return observationList;
    }

    public void setObservationList(List<Observation> observationList) {
        this.observationList = observationList;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (componentValuePK != null ? componentValuePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ComponentValue)) {
            return false;
        }
        ComponentValue other = (ComponentValue) object;
        if ((this.componentValuePK == null && other.componentValuePK != null) || (this.componentValuePK != null && !this.componentValuePK.equals(other.componentValuePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.ComponentValue[ componentValuePK=" + componentValuePK + " ]";
    }
    
}
