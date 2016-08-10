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
@Table(name = "conceptreference", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Conceptreference.findAll", query = "SELECT c FROM Conceptreference c"),
    @NamedQuery(name = "Conceptreference.findById", query = "SELECT c FROM Conceptreference c WHERE c.id = :id")})
public class Conceptreference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @JoinColumns({
        @JoinColumn(name = "ConceptSchemeAgencyID", referencedColumnName = "ConceptSchemeAgencyID", nullable = false),
        @JoinColumn(name = "ConceptSchemeID", referencedColumnName = "ConceptSchemeID", nullable = false),
        @JoinColumn(name = "ConceptSchemeVersion", referencedColumnName = "ConceptSchemeVersion", nullable = false),
        @JoinColumn(name = "ConceptID", referencedColumnName = "ID", nullable = false)})
    @ManyToOne(optional = false)
    private Concept concept;
    @OneToMany(mappedBy = "conceptIdentity")
    private List<Datastructurecomponent> datastructurecomponentList;

    public Conceptreference() {
    }

    public Conceptreference(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    @XmlTransient
    public List<Datastructurecomponent> getDatastructurecomponentList() {
        return datastructurecomponentList;
    }

    public void setDatastructurecomponentList(List<Datastructurecomponent> datastructurecomponentList) {
        this.datastructurecomponentList = datastructurecomponentList;
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
        if (!(object instanceof Conceptreference)) {
            return false;
        }
        Conceptreference other = (Conceptreference) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Conceptreference[ id=" + id + " ]";
    }
    
}
