/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Embeddable;

/**
 *
 * @author Sachikia
 */
@Embeddable
public class TablaSeccionPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "tabla_id")
    private int tablaId;
    @Basic(optional = false)
    @Column(name = "seccion_microcurriculo_id")
    private int seccionMicrocurriculoId;

    public TablaSeccionPK() {
    }

    public TablaSeccionPK(int tablaId, int seccionMicrocurriculoId) {
        this.tablaId = tablaId;
        this.seccionMicrocurriculoId = seccionMicrocurriculoId;
    }

    public int getTablaId() {
        return tablaId;
    }

    public void setTablaId(int tablaId) {
        this.tablaId = tablaId;
    }

    public int getSeccionMicrocurriculoId() {
        return seccionMicrocurriculoId;
    }

    public void setSeccionMicrocurriculoId(int seccionMicrocurriculoId) {
        this.seccionMicrocurriculoId = seccionMicrocurriculoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) tablaId;
        hash += (int) seccionMicrocurriculoId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TablaSeccionPK)) {
            return false;
        }
        TablaSeccionPK other = (TablaSeccionPK) object;
        if (this.tablaId != other.tablaId) {
            return false;
        }
        if (this.seccionMicrocurriculoId != other.seccionMicrocurriculoId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.TablaSeccionPK[ tablaId=" + tablaId + ", seccionMicrocurriculoId=" + seccionMicrocurriculoId + " ]";
    }
    
}
