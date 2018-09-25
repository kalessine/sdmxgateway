/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package sdmx.gateway.entities;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.validation.constraints.NotNull;

/**
 *
 * @author Owner
 */
@Embeddable
public class AnnotationPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "Annotations", nullable = false)
    private long annotations;
    @Basic(optional = false)
    @NotNull
    @Column(name = "index", nullable = false)
    private int index;

    public AnnotationPK() {
    }

    public AnnotationPK(long annotations, int index) {
        this.annotations = annotations;
        this.index = index;
    }

    public long getAnnotations() {
        return annotations;
    }

    public void setAnnotations(long annotations) {
        this.annotations = annotations;
    }

    public int getIndex() {
        return index;
    }

    public void setIndex(int index) {
        this.index = index;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) annotations;
        hash += (int) index;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnnotationPK)) {
            return false;
        }
        AnnotationPK other = (AnnotationPK) object;
        if (this.annotations != other.annotations) {
            return false;
        }
        if (this.index != other.index) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.AnnotationPK[ annotations=" + annotations + ", index=" + index + " ]";
    }
    
}
