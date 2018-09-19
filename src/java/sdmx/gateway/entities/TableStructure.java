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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Owner
 */
@Entity
@Table(name = "tableStructure", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TableStructure.findAll", query = "SELECT t FROM TableStructure t")
    , @NamedQuery(name = "TableStructure.findByTableStructure", query = "SELECT t FROM TableStructure t WHERE t.tableStructure = :tableStructure")})
public class TableStructure implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "tableStructure", nullable = false)
    private Long tableStructure;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "tableStructure1")
    private Component component;
    @OneToMany(mappedBy = "tableStructure")
    private List<Dataflow> dataflowList;

    public TableStructure() {
    }

    public TableStructure(Long tableStructure) {
        this.tableStructure = tableStructure;
    }

    public Long getTableStructure() {
        return tableStructure;
    }

    public void setTableStructure(Long tableStructure) {
        this.tableStructure = tableStructure;
    }

    public Component getComponent() {
        return component;
    }

    public void setComponent(Component component) {
        this.component = component;
    }

    @XmlTransient
    public List<Dataflow> getDataflowList() {
        return dataflowList;
    }

    public void setDataflowList(List<Dataflow> dataflowList) {
        this.dataflowList = dataflowList;
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
        if (!(object instanceof TableStructure)) {
            return false;
        }
        TableStructure other = (TableStructure) object;
        if ((this.tableStructure == null && other.tableStructure != null) || (this.tableStructure != null && !this.tableStructure.equals(other.tableStructure))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.TableStructure[ tableStructure=" + tableStructure + " ]";
    }
    
}
