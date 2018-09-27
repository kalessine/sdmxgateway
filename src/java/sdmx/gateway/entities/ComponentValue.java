/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@Entity
@Table(name = "ComponentValue", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComponentValue.findAll", query = "SELECT c FROM ComponentValue c")
    , @NamedQuery(name = "ComponentValue.findByAgencyID", query = "SELECT c FROM ComponentValue c WHERE c.componentValuePK.agencyID = :agencyID")
    , @NamedQuery(name = "ComponentValue.findById", query = "SELECT c FROM ComponentValue c WHERE c.componentValuePK.id = :id")
    , @NamedQuery(name = "ComponentValue.findByVersion", query = "SELECT c FROM ComponentValue c WHERE c.componentValuePK.version = :version")
    , @NamedQuery(name = "ComponentValue.findByValue", query = "SELECT c FROM ComponentValue c WHERE c.componentValuePK.value = :value")
    , @NamedQuery(name = "ComponentValue.findByColumnId", query = "SELECT c FROM ComponentValue c WHERE c.componentValuePK.columnId = :columnId")})
public class ComponentValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComponentValuePK componentValuePK;
    @JoinColumns({
        @JoinColumn(name = "agencyID", referencedColumnName = "agencyID", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "Id", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "version", referencedColumnName = "version", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "columnId", referencedColumnName = "columnId", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Component component;

    public ComponentValue() {
    }

    public ComponentValue(ComponentValuePK componentValuePK) {
        this.componentValuePK = componentValuePK;
    }

    public ComponentValue(String agencyID, String id, String version, String value, String columnId) {
        this.componentValuePK = new ComponentValuePK(agencyID, id, version, value, columnId);
    }

    public ComponentValuePK getComponentValuePK() {
        return componentValuePK;
    }

    public void setComponentValuePK(ComponentValuePK componentValuePK) {
        this.componentValuePK = componentValuePK;
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
