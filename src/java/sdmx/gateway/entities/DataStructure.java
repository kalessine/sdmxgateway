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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author James
 */
@Entity
@Table(catalog = "repository", schema = "public")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "DataStructure.findAll", query = "SELECT d FROM DataStructure d")
    , @NamedQuery(name = "DataStructure.findByAgencyId", query = "SELECT d FROM DataStructure d WHERE d.dataStructurePK.agencyId = :agencyId")
    , @NamedQuery(name = "DataStructure.findById", query = "SELECT d FROM DataStructure d WHERE d.dataStructurePK.id = :id")
    , @NamedQuery(name = "DataStructure.findByVersion", query = "SELECT d FROM DataStructure d WHERE d.dataStructurePK.version = :version")})
public class DataStructure implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DataStructurePK dataStructurePK;
    @OneToMany(mappedBy = "dataStructure")
    private List<DataStructureReference> dataStructureReferenceList;
    @OneToOne(cascade = CascadeType.ALL, mappedBy = "dataStructure")
    private DataStructureComponent dataStructureComponent;

    public DataStructure() {
    }

    public DataStructure(DataStructurePK dataStructurePK) {
        this.dataStructurePK = dataStructurePK;
    }

    public DataStructure(String agencyId, String id, String version) {
        this.dataStructurePK = new DataStructurePK(agencyId, id, version);
    }

    public DataStructurePK getDataStructurePK() {
        return dataStructurePK;
    }

    public void setDataStructurePK(DataStructurePK dataStructurePK) {
        this.dataStructurePK = dataStructurePK;
    }

    @XmlTransient
    public List<DataStructureReference> getDataStructureReferenceList() {
        return dataStructureReferenceList;
    }

    public void setDataStructureReferenceList(List<DataStructureReference> dataStructureReferenceList) {
        this.dataStructureReferenceList = dataStructureReferenceList;
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
        hash += (dataStructurePK != null ? dataStructurePK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof DataStructure)) {
            return false;
        }
        DataStructure other = (DataStructure) object;
        if ((this.dataStructurePK == null && other.dataStructurePK != null) || (this.dataStructurePK != null && !this.dataStructurePK.equals(other.dataStructurePK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.DataStructure[ dataStructurePK=" + dataStructurePK + " ]";
    }
    
}
