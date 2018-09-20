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
@Table(catalog = "repository", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"ConceptScheme_agencyId", "ConceptScheme_Id", "ConceptScheme_version"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptReference.findAll", query = "SELECT c FROM ConceptReference c")
    , @NamedQuery(name = "ConceptReference.findByConcept", query = "SELECT c FROM ConceptReference c WHERE c.concept = :concept")})
public class ConceptReference implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long concept;
    @JoinColumns({
        @JoinColumn(name = "ConceptScheme_agencyId", referencedColumnName = "ConceptScheme_agencyId")
        , @JoinColumn(name = "ConceptScheme_Id", referencedColumnName = "ConceptScheme_Id")
        , @JoinColumn(name = "ConceptScheme_version", referencedColumnName = "ConceptScheme_version")})
    @OneToOne
    private Concept concept1;

    public ConceptReference() {
    }

    public ConceptReference(Long concept) {
        this.concept = concept;
    }

    public Long getConcept() {
        return concept;
    }

    public void setConcept(Long concept) {
        this.concept = concept;
    }

    public Concept getConcept1() {
        return concept1;
    }

    public void setConcept1(Concept concept1) {
        this.concept1 = concept1;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (concept != null ? concept.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptReference)) {
            return false;
        }
        ConceptReference other = (ConceptReference) object;
        if ((this.concept == null && other.concept != null) || (this.concept != null && !this.concept.equals(other.concept))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.ConceptReference[ concept=" + concept + " ]";
    }
    
}
