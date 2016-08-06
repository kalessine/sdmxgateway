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
public class AnnotationtextPK implements Serializable {

    @Basic(optional = false)
    @NotNull
    @Column(name = "Annotated", nullable = false)
    private long annotated;
    @Basic(optional = false)
    @NotNull
    @Column(name = "index", nullable = false)
    private long index;
    @Basic(optional = false)
    @NotNull
    @Column(name = "textIndex", nullable = false)
    private long textIndex;

    public AnnotationtextPK() {
    }

    public AnnotationtextPK(long annotated, long index, long textIndex) {
        this.annotated = annotated;
        this.index = index;
        this.textIndex = textIndex;
    }

    public long getAnnotated() {
        return annotated;
    }

    public void setAnnotated(long annotated) {
        this.annotated = annotated;
    }

    public long getIndex() {
        return index;
    }

    public void setIndex(long index) {
        this.index = index;
    }

    public long getTextIndex() {
        return textIndex;
    }

    public void setTextIndex(long textIndex) {
        this.textIndex = textIndex;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) annotated;
        hash += (int) index;
        hash += (int) textIndex;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnnotationtextPK)) {
            return false;
        }
        AnnotationtextPK other = (AnnotationtextPK) object;
        if (this.annotated != other.annotated) {
            return false;
        }
        if (this.index != other.index) {
            return false;
        }
        if (this.textIndex != other.textIndex) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.AnnotationtextPK[ annotated=" + annotated + ", index=" + index + ", textIndex=" + textIndex + " ]";
    }
    
}
