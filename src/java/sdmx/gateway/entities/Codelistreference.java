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
@Table(name = "codelistreference", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Codelistreference.findAll", query = "SELECT c FROM Codelistreference c"),
    @NamedQuery(name = "Codelistreference.findById", query = "SELECT c FROM Codelistreference c WHERE c.id = :id")})
public class Codelistreference implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @JoinColumns({
        @JoinColumn(name = "CodelistAgencyID", referencedColumnName = "AgencyID", nullable = false),
        @JoinColumn(name = "CodelistID", referencedColumnName = "ID", nullable = false),
        @JoinColumn(name = "CodelistVersion", referencedColumnName = "Version", nullable = false)})
    @ManyToOne(optional = false)
    private Codelist codelist;
    @OneToMany(mappedBy = "codelistEnumeration")
    private List<Datastructurecomponents> datastructurecomponentsList;

    public Codelistreference() {
    }

    public Codelistreference(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Codelist getCodelist() {
        return codelist;
    }

    public void setCodelist(Codelist codelist) {
        this.codelist = codelist;
    }

    @XmlTransient
    public List<Datastructurecomponents> getDatastructurecomponentsList() {
        return datastructurecomponentsList;
    }

    public void setDatastructurecomponentsList(List<Datastructurecomponents> datastructurecomponentsList) {
        this.datastructurecomponentsList = datastructurecomponentsList;
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
        if (!(object instanceof Codelistreference)) {
            return false;
        }
        Codelistreference other = (Codelistreference) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Codelistreference[ id=" + id + " ]";
    }
    
}
