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
    @UniqueConstraint(columnNames = {"agencyId", "Id", "version"})})
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "ConceptSchemeReference.findAll", query = "SELECT c FROM ConceptSchemeReference c")
    , @NamedQuery(name = "ConceptSchemeReference.findByConceptScheme", query = "SELECT c FROM ConceptSchemeReference c WHERE c.conceptScheme = :conceptScheme")})
public class ConceptSchemeReference implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private Long conceptScheme;
    @JoinColumns({
        @JoinColumn(name = "agencyId", referencedColumnName = "agencyId")
        , @JoinColumn(name = "Id", referencedColumnName = "Id")
        , @JoinColumn(name = "version", referencedColumnName = "version")})
    @OneToOne
    private ConceptScheme conceptScheme1;
    @OneToOne(mappedBy = "conceptSchemeEnumeration")
    private DataStructureComponent dataStructureComponent;

    public ConceptSchemeReference() {
    }

    public ConceptSchemeReference(Long conceptScheme) {
        this.conceptScheme = conceptScheme;
    }

    public Long getConceptScheme() {
        return conceptScheme;
    }

    public void setConceptScheme(Long conceptScheme) {
        this.conceptScheme = conceptScheme;
    }

    public ConceptScheme getConceptScheme1() {
        return conceptScheme1;
    }

    public void setConceptScheme1(ConceptScheme conceptScheme1) {
        this.conceptScheme1 = conceptScheme1;
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
        hash += (conceptScheme != null ? conceptScheme.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof ConceptSchemeReference)) {
            return false;
        }
        ConceptSchemeReference other = (ConceptSchemeReference) object;
        if ((this.conceptScheme == null && other.conceptScheme != null) || (this.conceptScheme != null && !this.conceptScheme.equals(other.conceptScheme))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.ConceptSchemeReference[ conceptScheme=" + conceptScheme + " ]";
    }
    
}
