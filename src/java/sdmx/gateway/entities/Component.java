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
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
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
    , @NamedQuery(name = "Component.findByAgencyID", query = "SELECT c FROM Component c WHERE c.componentPK.agencyID = :agencyID")
    , @NamedQuery(name = "Component.findById", query = "SELECT c FROM Component c WHERE c.componentPK.id = :id")
    , @NamedQuery(name = "Component.findByVersion", query = "SELECT c FROM Component c WHERE c.componentPK.version = :version")
    , @NamedQuery(name = "Component.findByColumnId", query = "SELECT c FROM Component c WHERE c.componentPK.columnId = :columnId")
    , @NamedQuery(name = "Component.findByPosition", query = "SELECT c FROM Component c WHERE c.position = :position")
    , @NamedQuery(name = "Component.findByType", query = "SELECT c FROM Component c WHERE c.type = :type")})
public class Component implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComponentPK componentPK;
    @Basic(optional = false)
    @NotNull
    @Column(name = "position", nullable = false)
    private int position;
    @Column(name = "type")
    private Integer type;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "component")
    private List<ComponentValue> componentValueList;
    @JoinColumns({
        @JoinColumn(name = "agencyID", referencedColumnName = "agencyID", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "Id", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "version", referencedColumnName = "version", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Dataflow dataflow;

    public Component() {
    }

    public Component(ComponentPK componentPK) {
        this.componentPK = componentPK;
    }

    public Component(ComponentPK componentPK, int position) {
        this.componentPK = componentPK;
        this.position = position;
    }

    public Component(String agencyID, String id, String version, String columnId) {
        this.componentPK = new ComponentPK(agencyID, id, version, columnId);
    }

    public ComponentPK getComponentPK() {
        return componentPK;
    }

    public void setComponentPK(ComponentPK componentPK) {
        this.componentPK = componentPK;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
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

    public Dataflow getDataflow() {
        return dataflow;
    }

    public void setDataflow(Dataflow dataflow) {
        this.dataflow = dataflow;
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
