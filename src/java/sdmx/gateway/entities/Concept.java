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
import javax.persistence.JoinColumns;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Owner
 */
@Entity
@Table(name = "Concept", catalog = "repository", schema = "public", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"Annotations"})
    , @UniqueConstraint(columnNames = {"Name"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Concept.findAll", query = "SELECT c FROM Concept c")
    , @NamedQuery(name = "Concept.findByAgencyID", query = "SELECT c FROM Concept c WHERE c.conceptPK.agencyID = :agencyID")
    , @NamedQuery(name = "Concept.findById", query = "SELECT c FROM Concept c WHERE c.conceptPK.id = :id")
    , @NamedQuery(name = "Concept.findByVersion", query = "SELECT c FROM Concept c WHERE c.conceptPK.version = :version")
    , @NamedQuery(name = "Concept.findByConceptID", query = "SELECT c FROM Concept c WHERE c.conceptPK.conceptID = :conceptID")})
public class Concept implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConceptPK conceptPK;
    @JoinColumn(name = "Annotations", referencedColumnName = "Annotations")
    @OneToOne
    private Annotations annotations;
    @JoinColumns({
        @JoinColumn(name = "agencyID", referencedColumnName = "agencyID", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "Id", referencedColumnName = "Id", nullable = false, insertable = false, updatable = false)
        , @JoinColumn(name = "version", referencedColumnName = "version", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private ConceptScheme conceptScheme;
    @JoinColumn(name = "Name", referencedColumnName = "Name")
    @OneToOne
    private Name name;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "concept")
    private ConceptReference conceptReference;

    public Concept() {
    }

    public Concept(ConceptPK conceptPK) {
        this.conceptPK = conceptPK;
    }

    public Concept(String agencyID, String id, String version, String conceptID) {
        this.conceptPK = new ConceptPK(agencyID, id, version, conceptID);
    }

    public ConceptPK getConceptPK() {
        return conceptPK;
    }

    public void setConceptPK(ConceptPK conceptPK) {
        this.conceptPK = conceptPK;
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotations annotations) {
        this.annotations = annotations;
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
