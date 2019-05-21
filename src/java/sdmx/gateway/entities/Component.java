/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author James
 */
@Entity
@Table(name = "Component", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"dataflow", "columnId"})
    , @UniqueConstraint(columnNames = {"dataflow", "position"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Component.findAll", query = "SELECT c FROM Component c")
    , @NamedQuery(name = "Component.findByDataflow", query = "SELECT c FROM Component c WHERE c.dataflow = :dataflow")
    , @NamedQuery(name = "Component.findByPosition", query = "SELECT c FROM Component c WHERE c.position = :position")
    , @NamedQuery(name = "Component.findByColumnId", query = "SELECT c FROM Component c WHERE c.columnId = :columnId")
    , @NamedQuery(name = "Component.findByType", query = "SELECT c FROM Component c WHERE c.type = :type")})
public class Component implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "dataflow", nullable = false)
    private Long dataflow;
    @Basic(optional = false)
    @NotNull
    @Column(name = "position", nullable = false)
    private short position;
    @Size(max = 255)
    @Column(name = "columnId", length = 255)
    private String columnId;
    @Column(name = "type")
    private Integer type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "component")
    private List<ComponentValue> componentValueList;
    @JoinColumn(name = "dataflow", referencedColumnName = "dataflow", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Dataflow dataflow1;

    public Component() {
    }

    public Component(Long dataflow) {
        this.dataflow = dataflow;
    }

    public Component(Long dataflow, short position) {
        this.dataflow = dataflow;
        this.position = position;
    }

    public Long getDataflow() {
        return dataflow;
    }

    public void setDataflow(Long dataflow) {
        this.dataflow = dataflow;
    }

    public short getPosition() {
        return position;
    }

    public void setPosition(short position) {
        this.position = position;
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

    @XmlTransient
    public List<ComponentValue> getComponentValueList() {
        return componentValueList;
    }

    public void setComponentValueList(List<ComponentValue> componentValueList) {
        this.componentValueList = componentValueList;
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
        hash += (dataflow != null ? dataflow.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Component)) {
            return false;
        }
        Component other = (Component) object;
        if ((this.dataflow == null && other.dataflow != null) || (this.dataflow != null && !this.dataflow.equals(other.dataflow))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Component[ dataflow=" + dataflow + " ]";
    }
    
}
