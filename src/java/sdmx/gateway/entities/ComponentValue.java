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
@Table(name = "ComponentValue", catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ComponentValue.findAll", query = "SELECT c FROM ComponentValue c")
    , @NamedQuery(name = "ComponentValue.findByAgencyID", query = "SELECT c FROM ComponentValue c WHERE c.componentValuePK.agencyID = :agencyID")
    , @NamedQuery(name = "ComponentValue.findById", query = "SELECT c FROM ComponentValue c WHERE c.componentValuePK.id = :id")
    , @NamedQuery(name = "ComponentValue.findByVersion", query = "SELECT c FROM ComponentValue c WHERE c.componentValuePK.version = :version")
    , @NamedQuery(name = "ComponentValue.findByValue", query = "SELECT c FROM ComponentValue c WHERE c.value = :value")})
public class ComponentValue implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ComponentValuePK componentValuePK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "value", nullable = false, length = 255)
    private String value;
    @JoinColumns({
        @JoinColumn(name = "agencyID", referencedColumnName = "agencyID", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "Id", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "version", referencedColumnName = "version", nullable = false, insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private Component component;

    public ComponentValue() {
    }

    public ComponentValue(ComponentValuePK componentValuePK) {
        this.componentValuePK = componentValuePK;
    }

    public ComponentValue(ComponentValuePK componentValuePK, String value) {
        this.componentValuePK = componentValuePK;
        this.value = value;
    }

    public ComponentValue(String agencyID, String id, String version) {
        this.componentValuePK = new ComponentValuePK(agencyID, id, version);
    }

    public ComponentValuePK getComponentValuePK() {
        return componentValuePK;
    }

    public void setComponentValuePK(ComponentValuePK componentValuePK) {
        this.componentValuePK = componentValuePK;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
