/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sachikia
 */
@Entity
@Table(name = "tabla_info")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TablaInfo.findAll", query = "SELECT t FROM TablaInfo t")
    , @NamedQuery(name = "TablaInfo.findByFila", query = "SELECT t FROM TablaInfo t WHERE t.tablaInfoPK.fila = :fila")
    , @NamedQuery(name = "TablaInfo.findByColumna", query = "SELECT t FROM TablaInfo t WHERE t.tablaInfoPK.columna = :columna")
    , @NamedQuery(name = "TablaInfo.findByTablaSeccionSeccionMicrocurriculoId", query = "SELECT t FROM TablaInfo t WHERE t.tablaInfoPK.tablaSeccionSeccionMicrocurriculoId = :tablaSeccionSeccionMicrocurriculoId")})
public class TablaInfo implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TablaInfoPK tablaInfoPK;
    @JoinColumn(name = "tabla_seccion_seccion_microcurriculo_id", referencedColumnName = "seccion_microcurriculo_id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private TablaSeccion tablaSeccion;
    @JoinColumn(name = "contenido_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contenido contenidoId;

    public TablaInfo() {
    }

    public TablaInfo(TablaInfoPK tablaInfoPK) {
        this.tablaInfoPK = tablaInfoPK;
    }

    public TablaInfo(int fila, int columna, int tablaSeccionSeccionMicrocurriculoId) {
        this.tablaInfoPK = new TablaInfoPK(fila, columna, tablaSeccionSeccionMicrocurriculoId);
    }

    public TablaInfoPK getTablaInfoPK() {
        return tablaInfoPK;
    }

    public void setTablaInfoPK(TablaInfoPK tablaInfoPK) {
        this.tablaInfoPK = tablaInfoPK;
    }

    public TablaSeccion getTablaSeccion() {
        return tablaSeccion;
    }

    public void setTablaSeccion(TablaSeccion tablaSeccion) {
        this.tablaSeccion = tablaSeccion;
    }

    public Contenido getContenidoId() {
        return contenidoId;
    }

    public void setContenidoId(Contenido contenidoId) {
        this.contenidoId = contenidoId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tablaInfoPK != null ? tablaInfoPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TablaInfo)) {
            return false;
        }
        TablaInfo other = (TablaInfo) object;
        if ((this.tablaInfoPK == null && other.tablaInfoPK != null) || (this.tablaInfoPK != null && !this.tablaInfoPK.equals(other.tablaInfoPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.TablaInfo[ tablaInfoPK=" + tablaInfoPK + " ]";
    }
    
}
