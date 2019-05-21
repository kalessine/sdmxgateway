/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@Entity
@Table(name = "ConceptSchemeReference")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptSchemeReference.findAll", query = "SELECT c FROM ConceptSchemeReference c")
    , @NamedQuery(name = "ConceptSchemeReference.findByReference", query = "SELECT c FROM ConceptSchemeReference c WHERE c.reference = :reference")})
public class ConceptSchemeReference implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "reference", nullable = false)
    private Long reference;
    @JoinColumns({
        @JoinColumn(name = "agencyId", referencedColumnName = "agencyId")
        , @JoinColumn(name = "id", referencedColumnName = "id")
        , @JoinColumn(name = "version", referencedColumnName = "version")})
    @ManyToOne
    private ConceptScheme conceptScheme;
    @OneToOne(mappedBy = "conceptSchemeEnumeration")
    private DataStructureComponent dataStructureComponent;

    public ConceptSchemeReference() {
    }

    public ConceptSchemeReference(Long reference) {
        this.reference = reference;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public ConceptScheme getConceptScheme() {
        return conceptScheme;
    }

    public void setConceptScheme(ConceptScheme conceptScheme) {
        this.conceptScheme = conceptScheme;
    }

    public DataStructureComponent getDataStructureComponent() {
        return dataStructureComponent;
    }

    public void setDataStructureComponent(DataStructureComponent dataStructureComponent) {
        this.dataStructureComponent = dataStructureComponent;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (reference != null ? reference.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptSchemeReference)) {
            return false;
        }
        ConceptSchemeReference other = (ConceptSchemeReference) object;
        if ((this.reference == null && other.reference != null) || (this.reference != null && !this.reference.equals(other.reference))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.ConceptSchemeReference[ reference=" + reference + " ]";
    }
    
}
