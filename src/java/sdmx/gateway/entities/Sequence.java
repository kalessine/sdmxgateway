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
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author James
 */
@Entity
@Table(name = "sequence", catalog = "sdmxgateway", schema = "")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Sequence.findAll", query = "SELECT s FROM Sequence s"),
    @NamedQuery(name = "Sequence.findBySeqCount", query = "SELECT s FROM Sequence s WHERE s.seqCount = :seqCount"),
    @NamedQuery(name = "Sequence.findBySeqName", query = "SELECT s FROM Sequence s WHERE s.seqName = :seqName")})
public class Sequence implements Serializable {
    private static final long serialVersionUID = 1L;
    @Basic(optional = false)
    @NotNull
    @Column(name = "SEQ_COUNT", nullable = false)
    private long seqCount;
    @Id
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "SEQ_NAME", nullable = false, length = 100)
    private String seqName;

    public Sequence() {
    }

    public Sequence(String seqName) {
        this.seqName = seqName;
    }

    public Sequence(String seqName, long seqCount) {
        this.seqName = seqName;
        this.seqCount = seqCount;
    }

    public long getSeqCount() {
        return seqCount;
    }

    public void setSeqCount(long seqCount) {
        this.seqCount = seqCount;
    }

    public String getSeqName() {
        return seqName;
    }

    public void setSeqName(String seqName) {
        this.seqName = seqName;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (seqName != null ? seqName.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Sequence)) {
            return false;
        }
        Sequence other = (Sequence) object;
        if ((this.seqName == null && other.seqName != null) || (this.seqName != null && !this.seqName.equals(other.seqName))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.Sequence[ seqName=" + seqName + " ]";
    }
    
}
