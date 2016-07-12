/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
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
@Table(name = "concept", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Concept.findAll", query = "SELECT c FROM Concept c"),
    @NamedQuery(name = "Concept.findByConceptSchemeAgencyID", query = "SELECT c FROM Concept c WHERE c.conceptPK.conceptSchemeAgencyID = :conceptSchemeAgencyID"),
    @NamedQuery(name = "Concept.findByConceptSchemeID", query = "SELECT c FROM Concept c WHERE c.conceptPK.conceptSchemeID = :conceptSchemeID"),
    @NamedQuery(name = "Concept.findByConceptSchemeVersion", query = "SELECT c FROM Concept c WHERE c.conceptPK.conceptSchemeVersion = :conceptSchemeVersion"),
    @NamedQuery(name = "Concept.findById", query = "SELECT c FROM Concept c WHERE c.conceptPK.id = :id")})
public class Concept implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected ConceptPK conceptPK;
    @JoinColumn(name = "annotations", referencedColumnName = "id")
    @ManyToOne
    private Annotated annotations;
    @JoinColumns({
        @JoinColumn(name = "ConceptSchemeAgencyID", referencedColumnName = "AgencyID", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "ConceptSchemeID", referencedColumnName = "ID", nullable = false, insertable = false, updatable = false),
        @JoinColumn(name = "ConceptSchemeVersion", referencedColumnName = "Version", nullable = false, insertable = false, updatable = false)})
    @ManyToOne(optional = false)
    private Conceptscheme conceptscheme;
    @JoinColumn(name = "name", referencedColumnName = "id")
    @ManyToOne
    private Name name;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "concept")
    private List<Conceptreference> conceptreferenceList;

    public Concept() {
    }

    public Concept(ConceptPK conceptPK) {
        this.conceptPK = conceptPK;
    }

    public Concept(String conceptSchemeAgencyID, String conceptSchemeID, String conceptSchemeVersion, String id) {
        this.conceptPK = new ConceptPK(conceptSchemeAgencyID, conceptSchemeID, conceptSchemeVersion, id);
    }

    public ConceptPK getConceptPK() {
        return conceptPK;
    }

    public void setConceptPK(ConceptPK conceptPK) {
        this.conceptPK = conceptPK;
    }

    public Annotated getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotated annotations) {
        this.annotations = annotations;
    }

    public Conceptscheme getConceptscheme() {
        return conceptscheme;
    }

    public void setConceptscheme(Conceptscheme conceptscheme) {
        this.conceptscheme = conceptscheme;
    }

    public Name getName() {
        return name;
    }

    public void setName(Name name) {
        this.name = name;
    }

    @XmlTransient
    public List<Conceptreference> getConceptreferenceList() {
        return conceptreferenceList;
    }

    public void setConceptreferenceList(List<Conceptreference> conceptreferenceList) {
        this.conceptreferenceList = conceptreferenceList;
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
