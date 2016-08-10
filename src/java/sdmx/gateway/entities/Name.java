/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author James
 */
@Entity
@Table(name = "name", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Name.findAll", query = "SELECT n FROM Name n"),
    @NamedQuery(name = "Name.findById", query = "SELECT n FROM Name n WHERE n.id = :id")})
public class Name implements Serializable {
    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id", nullable = false)
    private Long id;
    @Lob
    @Size(max = 65535)
    @Column(name = "en", length = 65535)
    private String en;
    @OneToMany(mappedBy = "name")
    private List<Code> codeList;
    @OneToMany(mappedBy = "name")
    private List<Codelist> codelistList;
    @OneToMany(mappedBy = "name")
    private List<Concept> conceptList;
    @OneToMany(mappedBy = "name")
    private List<Dataflow> dataflowList;
    @OneToMany(mappedBy = "name")
    private List<Conceptscheme> conceptschemeList;
    @OneToMany(mappedBy = "name")
    private List<Datastructure> datastructureList;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "name")
    private List<Nametext> nametextList;

    public Name() {
    }

    public Name(Long id) {
        this.id = id;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEn() {
        return en;
    }

    public void setEn(String en) {
        this.en = en;
    }

    @XmlTransient
    public List<Code> getCodeList() {
        return codeList;
    }

    public void setCodeList(List<Code> codeList) {
        this.codeList = codeList;
    }

    @XmlTransient
    public List<Codelist> getCodelistList() {
        return codelistList;
    }

    public void setCodelistList(List<Codelist> codelistList) {
        this.codelistList = codelistList;
    }

    @XmlTransient
    public List<Concept> getConceptList() {
        return conceptList;
    }

    public void setConceptList(List<Concept> conceptList) {
        this.conceptList = conceptList;
    }

    @XmlTransient
    public List<Dataflow> getDataflowList() {
        return dataflowList;
    }

    public void setDataflowList(List<Dataflow> dataflowList) {
        this.dataflowList = dataflowList;
    }

    @XmlTransient
    public List<Conceptscheme> getConceptschemeList() {
        return conceptschemeList;
    }

    public void setConceptschemeList(List<Conceptscheme> conceptschemeList) {
        this.conceptschemeList = conceptschemeList;
    }

    @XmlTransient
    public List<Datastructure> getDatastructureList() {
        return datastructureList;
    }

    public void setDatastructureList(List<Datastructure> datastructureList) {
        this.datastructureList = datastructureList;
    }

    @XmlTransient
    public List<Nametext> getNametextList() {
        return nametextList;
    }

    public void setNametextList(List<Nametext> nametextList) {
        this.nametextList = nametextList;
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
        if (!(object instanceof Name)) {
            return false;
        }
        Name other = (Name) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Name[ id=" + id + " ]";
    }
    
}
