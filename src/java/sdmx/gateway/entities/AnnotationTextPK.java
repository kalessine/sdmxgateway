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
    @Column(name = "textIndex", nullable = false)
    private int textIndex;
    @Basic(optional = false)
    @NotNull
    @Column(name = "Annotations", nullable = false)
    private long annotations;
    @Basic(optional = false)
    @NotNull
    @Column(name = "index", nullable = false)
    private int index;

    public AnnotationTextPK() {
    }

    public AnnotationTextPK(int textIndex, long annotations, int index) {
        this.textIndex = textIndex;
        this.annotations = annotations;
        this.index = index;
    }

    public int getTextIndex() {
        return textIndex;
    }

    public void setTextIndex(int textIndex) {
        this.textIndex = textIndex;
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
        hash += (int) textIndex;
        hash += (int) annotations;
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
        if (this.textIndex != other.textIndex) {
            return false;
        }
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
        return "sdmx.gateway.entities.AnnotationTextPK[ textIndex=" + textIndex + ", annotations=" + annotations + ", index=" + index + " ]";
    }
    
}
