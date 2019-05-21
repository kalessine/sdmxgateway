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
 * @author James
 */
@Embeddable
public class AnnotationTextPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "annotations", nullable = false)
    private long annotations;
    @Basic(optional = false)
    @NotNull
    @Column(name = "textIndex", nullable = false)
    private short textIndex;
    @Basic(optional = false)
    @NotNull
    @Column(name = "index", nullable = false)
    private short index;

    public AnnotationTextPK() {
    }

    public AnnotationTextPK(long annotations, short textIndex, short index) {
        this.annotations = annotations;
        this.textIndex = textIndex;
        this.index = index;
    }

    public long getAnnotations() {
        return annotations;
    }

    public void setAnnotations(long annotations) {
        this.annotations = annotations;
    }

    public short getTextIndex() {
        return textIndex;
    }

    public void setTextIndex(short textIndex) {
        this.textIndex = textIndex;
    }

    public short getIndex() {
        return index;
    }

    public void setIndex(short index) {
        this.index = index;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) annotations;
        hash += (int) textIndex;
        hash += (int) index;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnnotationTextPK)) {
            return false;
        }
        AnnotationTextPK other = (AnnotationTextPK) object;
        if (this.annotations != other.annotations) {
            return false;
        }
        if (this.textIndex != other.textIndex) {
            return false;
        }
        if (this.index != other.index) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.AnnotationTextPK[ annotations=" + annotations + ", textIndex=" + textIndex + ", index=" + index + " ]";
    }
    
}
