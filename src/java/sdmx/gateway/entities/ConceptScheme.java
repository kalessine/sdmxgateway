/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@Entity
@Table(catalog = "repository", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"annotated"})
    , @UniqueConstraint(columnNames = {"name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptScheme.findAll", query = "SELECT c FROM ConceptScheme c")
    , @NamedQuery(name = "ConceptScheme.findByAgencyId", query = "SELECT c FROM ConceptScheme c WHERE c.conceptSchemePK.agencyId = :agencyId")
    , @NamedQuery(name = "ConceptScheme.findById", query = "SELECT c FROM ConceptScheme c WHERE c.conceptSchemePK.id = :id")
    , @NamedQuery(name = "ConceptScheme.findByVersion", query = "SELECT c FROM ConceptScheme c WHERE c.conceptSchemePK.version = :version")})
public class ConceptScheme implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConceptSchemePK conceptSchemePK;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "conceptScheme")
    private Concept concept;
    @OneToOne(mappedBy = "conceptScheme1")
    private ConceptSchemeReference conceptSchemeReference;
    @JoinColumn(name = "annotated", referencedColumnName = "annotated")
    @OneToOne
    private Annotated annotated;
    @JoinColumn(name = "name", referencedColumnName = "name")
    @OneToOne
    private Name name;

    public ConceptScheme() {
    }

    public ConceptScheme(ConceptSchemePK conceptSchemePK) {
        this.conceptSchemePK = conceptSchemePK;
    }

    public ConceptScheme(String agencyId, String id, String version) {
        this.conceptSchemePK = new ConceptSchemePK(agencyId, id, version);
    }

    public ConceptSchemePK getConceptSchemePK() {
        return conceptSchemePK;
    }

    public void setConceptSchemePK(ConceptSchemePK conceptSchemePK) {
        this.conceptSchemePK = conceptSchemePK;
    }

    public Concept getConcept() {
        return concept;
    }

    public void setConcept(Concept concept) {
        this.concept = concept;
    }

    public ConceptSchemeReference getConceptSchemeReference() {
        return conceptSchemeReference;
    }

    public void setConceptSchemeReference(ConceptSchemeReference conceptSchemeReference) {
        this.conceptSchemeReference = conceptSchemeReference;
    }

    public Annotated getAnnotated() {
        return annotated;
    }

    public void setAnnotated(Annotated annotated) {
        this.annotated = annotated;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conceptSchemePK != null ? conceptSchemePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptScheme)) {
            return false;
        }
        ConceptScheme other = (ConceptScheme) object;
        if ((this.conceptSchemePK == null && other.conceptSchemePK != null) || (this.conceptSchemePK != null && !this.conceptSchemePK.equals(other.conceptSchemePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.ConceptScheme[ conceptSchemePK=" + conceptSchemePK + " ]";
    }
    
}
