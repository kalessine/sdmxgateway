/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@Entity
@Table(name = "Component")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Component.findAll", query = "SELECT c FROM Component c")
    , @NamedQuery(name = "Component.findByDataflow", query = "SELECT c FROM Component c WHERE c.componentPK.dataflow = :dataflow")
    , @NamedQuery(name = "Component.findByPosition", query = "SELECT c FROM Component c WHERE c.componentPK.position = :position")
    , @NamedQuery(name = "Component.findByColumnId", query = "SELECT c FROM Component c WHERE c.columnId = :columnId")
    , @NamedQuery(name = "Component.findByType", query = "SELECT c FROM Component c WHERE c.type = :type")})
public class Component implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComponentPK componentPK;
    @Size(max = 255)
    @Column(name = "columnId")
    private String columnId;
    @Column(name = "type")
    private Integer type;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "component")
    private ComponentValue componentValue;
    @JoinColumn(name = "dataflow", referencedColumnName = "dataflow", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Dataflow dataflow1;

    public Component() {
    }

    public Component(ComponentPK componentPK) {
        this.componentPK = componentPK;
    }

    public Component(long dataflow, short position) {
        this.componentPK = new ComponentPK(dataflow, position);
    }

    public ComponentPK getComponentPK() {
        return componentPK;
    }

    public void setComponentPK(ComponentPK componentPK) {
        this.componentPK = componentPK;
    }

    public String getColumnId() {
        return columnId;
    }

    public void setColumnId(String columnId) {
        this.columnId = columnId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public ComponentValue getComponentValue() {
        return componentValue;
    }

    public void setComponentValue(ComponentValue componentValue) {
        this.componentValue = componentValue;
    }

    public Dataflow getDataflow1() {
        return dataflow1;
    }

    public void setDataflow1(Dataflow dataflow1) {
        this.dataflow1 = dataflow1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (componentPK != null ? componentPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Component)) {
            return false;
        }
        Component other = (Component) object;
        if ((this.componentPK == null && other.componentPK != null) || (this.componentPK != null && !this.componentPK.equals(other.componentPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Component[ componentPK=" + componentPK + " ]";
    }
    
}
