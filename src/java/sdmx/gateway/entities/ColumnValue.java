<<<<<<< HEAD
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
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Owner
 */
@Entity
@Table(name = "ColumnValue", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ColumnValue.findAll", query = "SELECT c FROM ColumnValue c")
    , @NamedQuery(name = "ColumnValue.findById", query = "SELECT c FROM ColumnValue c WHERE c.id = :id")
    , @NamedQuery(name = "ColumnValue.findByValue", query = "SELECT c FROM ColumnValue c WHERE c.value = :value")
    , @NamedQuery(name = "ColumnValue.findByTableStructure", query = "SELECT c FROM ColumnValue c WHERE c.tableStructure = :tableStructure")})
public class ColumnValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(name = "id", length = 255)
    private String id;
    @Size(max = 512)
    @Column(name = "value", length = 512)
    private String value;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tableStructure", nullable = false)
    private Long tableStructure;
    @JoinTable(name = "Value", joinColumns = {
        @JoinColumn(name = "tableStructure", referencedColumnName = "tableStructure", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "id1", referencedColumnName = "id1", nullable = false)})
    @ManyToMany
    private List<Observation> observationList;
    @JoinColumn(name = "tableStructure", referencedColumnName = "tableStructure", nullable = false, insertable = false, updatable = false)
    @OneToOne(optional = false)
    private Component component;

    public ColumnValue() {
    }

    public ColumnValue(Long tableStructure) {
        this.tableStructure = tableStructure;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTableStructure() {
        return tableStructure;
    }

    public void setTableStructure(Long tableStructure) {
        this.tableStructure = tableStructure;
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
        hash += (tableStructure != null ? tableStructure.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ColumnValue)) {
            return false;
        }
        ColumnValue other = (ColumnValue) object;
        if ((this.tableStructure == null && other.tableStructure != null) || (this.tableStructure != null && !this.tableStructure.equals(other.tableStructure))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.ColumnValue[ tableStructure=" + tableStructure + " ]";
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
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author James
 */
@Entity
@Table(name = "ColumnValue", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ColumnValue.findAll", query = "SELECT c FROM ColumnValue c")
    , @NamedQuery(name = "ColumnValue.findById", query = "SELECT c FROM ColumnValue c WHERE c.id = :id")
    , @NamedQuery(name = "ColumnValue.findByValue", query = "SELECT c FROM ColumnValue c WHERE c.value = :value")
    , @NamedQuery(name = "ColumnValue.findByTableStructure", query = "SELECT c FROM ColumnValue c WHERE c.tableStructure = :tableStructure")})
public class ColumnValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Size(max = 255)
    @Column(name = "id", length = 255)
    private String id;
    @Size(max = 512)
    @Column(name = "value", length = 512)
    private String value;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tableStructure", nullable = false)
    private Long tableStructure;
    @JoinTable(name = "Value", joinColumns = {
        @JoinColumn(name = "tableStructure", referencedColumnName = "tableStructure", nullable = false)}, inverseJoinColumns = {
        @JoinColumn(name = "id1", referencedColumnName = "id1", nullable = false)})
    @ManyToMany
    private List<Observation> observationList;
    @JoinColumns({
        @JoinColumn(name = "tableStructure", referencedColumnName = "tableStructure", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "name", referencedColumnName = "name")})
    @ManyToOne(optional = false)
    private Component component;

    public ColumnValue() {
    }

    public ColumnValue(Long tableStructure) {
        this.tableStructure = tableStructure;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Long getTableStructure() {
        return tableStructure;
    }

    public void setTableStructure(Long tableStructure) {
        this.tableStructure = tableStructure;
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
        hash += (tableStructure != null ? tableStructure.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ColumnValue)) {
            return false;
        }
        ColumnValue other = (ColumnValue) object;
        if ((this.tableStructure == null && other.tableStructure != null) || (this.tableStructure != null && !this.tableStructure.equals(other.tableStructure))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.ColumnValue[ tableStructure=" + tableStructure + " ]";
    }
    
}
>>>>>>> origin/master
