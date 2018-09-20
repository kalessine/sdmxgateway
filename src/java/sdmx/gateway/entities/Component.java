<<<<<<< HEAD
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
@Table(name = "Component", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Component.findAll", query = "SELECT c FROM Component c")
    , @NamedQuery(name = "Component.findByPosition", query = "SELECT c FROM Component c WHERE c.position = :position")
    , @NamedQuery(name = "Component.findByTableStructure", query = "SELECT c FROM Component c WHERE c.tableStructure = :tableStructure")
    , @NamedQuery(name = "Component.findByName", query = "SELECT c FROM Component c WHERE c.name = :name")})
public class Component implements Serializable {

    private static final long serialVersionUID = 1L;
    @Column(name = "position")
    private Short position;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tableStructure", nullable = false)
    private Long tableStructure;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "name", nullable = false, length = 255)
    private String name;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "component")
    private ColumnValue columnValue;
    @JoinColumn(name = "tableStructure", referencedColumnName = "tableStructure", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private TableStructure tableStructure1;

    public Component() {
    }

    public Component(Long tableStructure) {
        this.tableStructure = tableStructure;
    }

    public Component(Long tableStructure, String name) {
        this.tableStructure = tableStructure;
        this.name = name;
    }

    public Short getPosition() {
        return position;
    }

    public void setPosition(Short position) {
        this.position = position;
    }

    public Long getTableStructure() {
        return tableStructure;
    }

    public void setTableStructure(Long tableStructure) {
        this.tableStructure = tableStructure;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ColumnValue getColumnValue() {
        return columnValue;
    }

    public void setColumnValue(ColumnValue columnValue) {
        this.columnValue = columnValue;
    }

    public TableStructure getTableStructure1() {
        return tableStructure1;
    }

    public void setTableStructure1(TableStructure tableStructure1) {
        this.tableStructure1 = tableStructure1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tableStructure != null ? tableStructure.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Component)) {
            return false;
        }
        Component other = (Component) object;
        if ((this.tableStructure == null && other.tableStructure != null) || (this.tableStructure != null && !this.tableStructure.equals(other.tableStructure))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Component[ tableStructure=" + tableStructure + " ]";
    }
    
}
=======
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author James
 */
@Entity
@Table(name = "Component", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Component.findAll", query = "SELECT c FROM Component c")
    , @NamedQuery(name = "Component.findByPosition", query = "SELECT c FROM Component c WHERE c.position = :position")
    , @NamedQuery(name = "Component.findByTableStructure", query = "SELECT c FROM Component c WHERE c.componentPK.tableStructure = :tableStructure")
    , @NamedQuery(name = "Component.findByName", query = "SELECT c FROM Component c WHERE c.componentPK.name = :name")})
public class Component implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComponentPK componentPK;
    @Column(name = "position")
    private Short position;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "component")
    private List<ColumnValue> columnValueList;
    @JoinColumn(name = "tableStructure", referencedColumnName = "tableStructure", nullable = false, insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TableStructure tableStructure1;

    public Component() {
    }

    public Component(ComponentPK componentPK) {
        this.componentPK = componentPK;
    }

    public Component(long tableStructure, String name) {
        this.componentPK = new ComponentPK(tableStructure, name);
    }

    public ComponentPK getComponentPK() {
        return componentPK;
    }

    public void setComponentPK(ComponentPK componentPK) {
        this.componentPK = componentPK;
    }

    public Short getPosition() {
        return position;
    }

    public void setPosition(Short position) {
        this.position = position;
    }

    @XmlTransient
    public List<ColumnValue> getColumnValueList() {
        return columnValueList;
    }

    public void setColumnValueList(List<ColumnValue> columnValueList) {
        this.columnValueList = columnValueList;
    }

    public TableStructure getTableStructure1() {
        return tableStructure1;
    }

    public void setTableStructure1(TableStructure tableStructure1) {
        this.tableStructure1 = tableStructure1;
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
>>>>>>> origin/master