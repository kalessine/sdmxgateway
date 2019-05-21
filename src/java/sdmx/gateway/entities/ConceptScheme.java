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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.UniqueConstraint;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author James
 */
@Entity
@Table(name = "ConceptScheme", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"name"})
    , @UniqueConstraint(columnNames = {"annotations"})})
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
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "conceptScheme")
    private List<Concept> conceptList;
    @OneToMany(mappedBy = "conceptScheme")
    private List<ConceptSchemeReference> conceptSchemeReferenceList;
    @JoinColumn(name = "annotations", referencedColumnName = "annotations")
    @OneToOne
    private Annotations annotations;
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

    @XmlTransient
    public List<Concept> getConceptList() {
        return conceptList;
    }

    public void setConceptList(List<Concept> conceptList) {
        this.conceptList = conceptList;
    }

    @XmlTransient
    public List<ConceptSchemeReference> getConceptSchemeReferenceList() {
        return conceptSchemeReferenceList;
    }

    public void setConceptSchemeReferenceList(List<ConceptSchemeReference> conceptSchemeReferenceList) {
        this.conceptSchemeReferenceList = conceptSchemeReferenceList;
    }

    public Annotations getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotations annotations) {
        this.annotations = annotations;
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
