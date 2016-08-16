/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@Entity
@Table(name = "dataflow", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Dataflow.findAll", query = "SELECT d FROM Dataflow d"),
    @NamedQuery(name = "Dataflow.findById", query = "SELECT d FROM Dataflow d WHERE d.dataflowPK.id = :id"),
    @NamedQuery(name = "Dataflow.findByAgencyID", query = "SELECT d FROM Dataflow d WHERE d.dataflowPK.agencyID = :agencyID"),
    @NamedQuery(name = "Dataflow.findByVersion", query = "SELECT d FROM Dataflow d WHERE d.dataflowPK.version = :version")})
public class Dataflow implements Serializable {
    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected DataflowPK dataflowPK;
    @JoinColumn(name = "annotations", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Annotated annotations;
    @JoinColumn(name = "structure", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Datastructurereference structure;
    @JoinColumn(name = "name", referencedColumnName = "id")
    @ManyToOne(fetch = FetchType.LAZY)
    private Name name;

    public Dataflow() {
    }

    public Dataflow(DataflowPK dataflowPK) {
        this.dataflowPK = dataflowPK;
    }

    public Dataflow(String id, String agencyID, String version) {
        this.dataflowPK = new DataflowPK(id, agencyID, version);
    }

    public DataflowPK getDataflowPK() {
        return dataflowPK;
    }

    public void setDataflowPK(DataflowPK dataflowPK) {
        this.dataflowPK = dataflowPK;
    }

    public Annotated getAnnotations() {
        return annotations;
    }

    public void setAnnotations(Annotated annotations) {
        this.annotations = annotations;
    }

    public Datastructurereference getStructure() {
        return structure;
    }

    public void setStructure(Datastructurereference structure) {
        this.structure = structure;
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
        hash += (dataflowPK != null ? dataflowPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Dataflow)) {
            return false;
        }
        Dataflow other = (Dataflow) object;
        if ((this.dataflowPK == null && other.dataflowPK != null) || (this.dataflowPK != null && !this.dataflowPK.equals(other.dataflowPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Dataflow[ dataflowPK=" + dataflowPK + " ]";
    }
    
}
