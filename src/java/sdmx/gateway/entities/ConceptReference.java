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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@Entity
@Table(name = "ConceptReference", catalog = "repository", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"agencyID", "Id", "version", "conceptID"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptReference.findAll", query = "SELECT c FROM ConceptReference c")
    , @NamedQuery(name = "ConceptReference.findByReference", query = "SELECT c FROM ConceptReference c WHERE c.reference = :reference")})
public class ConceptReference implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "reference", nullable = false)
    private Long reference;
    @JoinColumns({
        @JoinColumn(name = "agencyID", referencedColumnName = "agencyID", nullable = false)
        , @JoinColumn(name = "Id", referencedColumnName = "Id", nullable = false)
        , @JoinColumn(name = "version", referencedColumnName = "version", nullable = false)
        , @JoinColumn(name = "conceptID", referencedColumnName = "conceptID", nullable = false)})
    @OneToOne(optional = false)
    private Concept concept;
    @OneToOne(mappedBy = "conceptIdentity")
    private DataStructureComponent dataStructureComponent;

    public ConceptReference() {
    }

    public ConceptReference(Long reference) {
        this.reference = reference;
    }

    public Long getReference() {
        return reference;
    }

    public void setReference(Long reference) {
        this.reference = reference;
    }

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
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
        if (!(object instanceof ConceptReference)) {
            return false;
        }
        ConceptReference other = (ConceptReference) object;
        if ((this.reference == null && other.reference != null) || (this.reference != null && !this.reference.equals(other.reference))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.ConceptReference[ reference=" + reference + " ]";
    }
    
}
