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
import javax.persistence.UniqueConstraint;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Owner
 */
@Entity
@Table(name = "Concept", catalog = "repository", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"annotated"})
    , @UniqueConstraint(columnNames = {"name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Concept.findAll", query = "SELECT c FROM Concept c")
    , @NamedQuery(name = "Concept.findByConceptSchemeagencyId", query = "SELECT c FROM Concept c WHERE c.conceptPK.conceptSchemeagencyId = :conceptSchemeagencyId")
    , @NamedQuery(name = "Concept.findByConceptSchemeId", query = "SELECT c FROM Concept c WHERE c.conceptPK.conceptSchemeId = :conceptSchemeId")
    , @NamedQuery(name = "Concept.findByConceptSchemeversion", query = "SELECT c FROM Concept c WHERE c.conceptPK.conceptSchemeversion = :conceptSchemeversion")
    , @NamedQuery(name = "Concept.findByConceptId", query = "SELECT c FROM Concept c WHERE c.conceptId = :conceptId")})
public class Concept implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConceptPK conceptPK;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 255)
    @Column(name = "conceptId", nullable = false, length = 255)
    private String conceptId;
    @JoinColumn(name = "annotated", referencedColumnName = "annotated")
    @OneToOne
<<<<<<< HEAD
    private Annotated annotated;
=======
    private Annotation annotated;
>>>>>>> origin/master
    @JoinColumns({
        @JoinColumn(name = "ConceptScheme_agencyId", referencedColumnName = "agencyId", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "ConceptScheme_Id", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "ConceptScheme_version", referencedColumnName = "version", nullable = false, insertable = false, updatable = false)})
    @OneToOne(optional = false)
    private ConceptScheme conceptScheme;
    @JoinColumn(name = "name", referencedColumnName = "name")
    @OneToOne
    private Name name;
    @OneToOne(mappedBy = "concept1")
    private ConceptReference conceptReference;

    public Concept() {
    }

    public Concept(ConceptPK conceptPK) {
        this.conceptPK = conceptPK;
    }

    public Concept(ConceptPK conceptPK, String conceptId) {
        this.conceptPK = conceptPK;
        this.conceptId = conceptId;
    }

    public Concept(String conceptSchemeagencyId, String conceptSchemeId, String conceptSchemeversion) {
        this.conceptPK = new ConceptPK(conceptSchemeagencyId, conceptSchemeId, conceptSchemeversion);
    }

    public ConceptPK getConceptPK() {
        return conceptPK;
    }

    public void setConceptPK(ConceptPK conceptPK) {
        this.conceptPK = conceptPK;
    }

    public String getConceptId() {
        return conceptId;
    }

    public void setConceptId(String conceptId) {
        this.conceptId = conceptId;
    }

<<<<<<< HEAD
    public Annotated getAnnotated() {
=======
    public Annotation getAnnotated() {
>>>>>>> origin/master
        return annotated;
    }

<<<<<<< HEAD
    public void setAnnotated(Annotated annotated) {
=======
    public void setAnnotated(Annotation annotated) {
>>>>>>> origin/master
        this.annotated = annotated;
    }

    public ConceptScheme getConceptScheme() {
        return conceptScheme;
    }

    public void setConceptScheme(ConceptScheme conceptScheme) {
        this.conceptScheme = conceptScheme;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    public ConceptReference getConceptReference() {
        return conceptReference;
    }

    public void setConceptReference(ConceptReference conceptReference) {
        this.conceptReference = conceptReference;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (conceptPK != null ? conceptPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Concept)) {
            return false;
        }
        Concept other = (Concept) object;
        if ((this.conceptPK == null && other.conceptPK != null) || (this.conceptPK != null && !this.conceptPK.equals(other.conceptPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Concept[ conceptPK=" + conceptPK + " ]";
    }
    
}
