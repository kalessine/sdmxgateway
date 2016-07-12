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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
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
@Table(name = "conceptschemereference", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conceptschemereference.findAll", query = "SELECT c FROM Conceptschemereference c"),
    @NamedQuery(name = "Conceptschemereference.findById", query = "SELECT c FROM Conceptschemereference c WHERE c.id = :id")})
public class Conceptschemereference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @JoinColumns({
        @JoinColumn(name = "ConceptSchemeAgencyID", referencedColumnName = "AgencyID", nullable = false),
        @JoinColumn(name = "ConceptSchemeID", referencedColumnName = "ID", nullable = false),
        @JoinColumn(name = "ConceptSchemeVersion", referencedColumnName = "Version", nullable = false)})
    @ManyToOne(optional = false)
    private Conceptscheme conceptscheme;
    @OneToMany(mappedBy = "conceptSchemeEnumeration")
    private List<Datastructurecomponents> datastructurecomponentsList;

    public Conceptschemereference() {
    }

    public Conceptschemereference(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Conceptscheme getConceptscheme() {
        return conceptscheme;
    }

    public void setConceptscheme(Conceptscheme conceptscheme) {
        this.conceptscheme = conceptscheme;
    }

    @XmlTransient
    public List<Datastructurecomponents> getDatastructurecomponentsList() {
        return datastructurecomponentsList;
    }

    public void setDatastructurecomponentsList(List<Datastructurecomponents> datastructurecomponentsList) {
        this.datastructurecomponentsList = datastructurecomponentsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Conceptschemereference)) {
            return false;
        }
        Conceptschemereference other = (Conceptschemereference) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Conceptschemereference[ id=" + id + " ]";
    }
    
}
