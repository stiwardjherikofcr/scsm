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
public class TablaInfoPK implements Serializable {

    @Basic(optional = false)
    @Column(name = "fila")
    private int fila;
    @Basic(optional = false)
    @Column(name = "columna")
    private int columna;
    @Basic(optional = false)
    @Column(name = "tabla_seccion_seccion_microcurriculo_id")
    private int tablaSeccionSeccionMicrocurriculoId;

    public TablaInfoPK() {
    }

    public TablaInfoPK(int fila, int columna, int tablaSeccionSeccionMicrocurriculoId) {
        this.fila = fila;
        this.columna = columna;
        this.tablaSeccionSeccionMicrocurriculoId = tablaSeccionSeccionMicrocurriculoId;
    }

    public int getFila() {
        return fila;
    }

    public void setFila(int fila) {
        this.fila = fila;
    }

    public int getColumna() {
        return columna;
    }

    public void setColumna(int columna) {
        this.columna = columna;
    }

    public int getTablaSeccionSeccionMicrocurriculoId() {
        return tablaSeccionSeccionMicrocurriculoId;
    }

    public void setTablaSeccionSeccionMicrocurriculoId(int tablaSeccionSeccionMicrocurriculoId) {
        this.tablaSeccionSeccionMicrocurriculoId = tablaSeccionSeccionMicrocurriculoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (int) fila;
        hash += (int) columna;
        hash += (int) tablaSeccionSeccionMicrocurriculoId;
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TablaInfoPK)) {
            return false;
        }
        TablaInfoPK other = (TablaInfoPK) object;
        if (this.fila != other.fila) {
            return false;
        }
        if (this.columna != other.columna) {
            return false;
        }
        if (this.tablaSeccionSeccionMicrocurriculoId != other.tablaSeccionSeccionMicrocurriculoId) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.TablaInfoPK[ fila=" + fila + ", columna=" + columna + ", tablaSeccionSeccionMicrocurriculoId=" + tablaSeccionSeccionMicrocurriculoId + " ]";
    }
    
}
