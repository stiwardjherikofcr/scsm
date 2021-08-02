/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.EmbeddedId;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author Sachikia
 */
@Entity
@Table(name = "tabla_seccion")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "TablaSeccion.findAll", query = "SELECT t FROM TablaSeccion t")
    , @NamedQuery(name = "TablaSeccion.findByTablaId", query = "SELECT t FROM TablaSeccion t WHERE t.tablaSeccionPK.tablaId = :tablaId")
    , @NamedQuery(name = "TablaSeccion.findBySeccionMicrocurriculoId", query = "SELECT t FROM TablaSeccion t WHERE t.tablaSeccionPK.seccionMicrocurriculoId = :seccionMicrocurriculoId")
    , @NamedQuery(name = "TablaSeccion.findByNumFilas", query = "SELECT t FROM TablaSeccion t WHERE t.numFilas = :numFilas")})
public class TablaSeccion implements Serializable {

    private static final long serialVersionUID = 1L;
    @EmbeddedId
    protected TablaSeccionPK tablaSeccionPK;
    @Basic(optional = false)
    @Column(name = "num_filas")
    private int numFilas;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "tablaSeccion")
    private List<TablaInfo> tablaInfoList;
    @JoinColumn(name = "seccion_microcurriculo_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private SeccionMicrocurriculo seccionMicrocurriculo;
    @JoinColumn(name = "tabla_id", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Tabla tabla;

    public TablaSeccion() {
    }

    public TablaSeccion(TablaSeccionPK tablaSeccionPK) {
        this.tablaSeccionPK = tablaSeccionPK;
    }

    public TablaSeccion(TablaSeccionPK tablaSeccionPK, int numFilas) {
        this.tablaSeccionPK = tablaSeccionPK;
        this.numFilas = numFilas;
    }

    public TablaSeccion(int tablaId, int seccionMicrocurriculoId) {
        this.tablaSeccionPK = new TablaSeccionPK(tablaId, seccionMicrocurriculoId);
    }

    public TablaSeccionPK getTablaSeccionPK() {
        return tablaSeccionPK;
    }

    public void setTablaSeccionPK(TablaSeccionPK tablaSeccionPK) {
        this.tablaSeccionPK = tablaSeccionPK;
    }

    public int getNumFilas() {
        return numFilas;
    }

    public void setNumFilas(int numFilas) {
        this.numFilas = numFilas;
    }

    @XmlTransient
    public List<TablaInfo> getTablaInfoList() {
        return tablaInfoList;
    }

    public void setTablaInfoList(List<TablaInfo> tablaInfoList) {
        this.tablaInfoList = tablaInfoList;
    }

    public SeccionMicrocurriculo getSeccionMicrocurriculo() {
        return seccionMicrocurriculo;
    }

    public void setSeccionMicrocurriculo(SeccionMicrocurriculo seccionMicrocurriculo) {
        this.seccionMicrocurriculo = seccionMicrocurriculo;
    }

    public Tabla getTabla() {
        return tabla;
    }

    public void setTabla(Tabla tabla) {
        this.tabla = tabla;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tablaSeccionPK != null ? tablaSeccionPK.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof TablaSeccion)) {
            return false;
        }
        TablaSeccion other = (TablaSeccion) object;
        if ((this.tablaSeccionPK == null && other.tablaSeccionPK != null) || (this.tablaSeccionPK != null && !this.tablaSeccionPK.equals(other.tablaSeccionPK))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.TablaSeccion[ tablaSeccionPK=" + tablaSeccionPK + " ]";
    }
    
}
