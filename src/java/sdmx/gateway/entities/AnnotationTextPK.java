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
    @Column(nullable = false)
    private long id;
    @Basic(optional = false)
    @NotNull
    @Column(nullable = false)
    private short textIndex;

    public AnnotationTextPK() {
    }

    public AnnotationTextPK(long id, short textIndex) {
        this.id = id;
        this.textIndex = textIndex;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public short getTextIndex() {
        return textIndex;
    }

    public void setTextIndex(short textIndex) {
        this.textIndex = textIndex;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) id;
        hash += (int) textIndex;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof AnnotationTextPK)) {
            return false;
        }
        AnnotationTextPK other = (AnnotationTextPK) object;
        if (this.id != other.id) {
            return false;
        }
        if (this.textIndex != other.textIndex) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "sdmx.gateway.entities.AnnotationTextPK[ id=" + id + ", textIndex=" + textIndex + " ]";
    }
    
}
