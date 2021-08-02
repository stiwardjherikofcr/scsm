/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dto;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Sachikia
 */
@Entity
@Table(name = "cumplimiento")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Cumplimiento.findAll", query = "SELECT c FROM Cumplimiento c")
    , @NamedQuery(name = "Cumplimiento.findByMateriaPeriodoGrupoId", query = "SELECT c FROM Cumplimiento c WHERE c.materiaPeriodoGrupoId = :materiaPeriodoGrupoId")
    , @NamedQuery(name = "Cumplimiento.findByEstado", query = "SELECT c FROM Cumplimiento c WHERE c.estado = :estado")})
public class Cumplimiento implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @Column(name = "materia_periodo_grupo_id")
    private Integer materiaPeriodoGrupoId;
    @Column(name = "estado")
    private Short estado;
    @JoinColumn(name = "materia_periodo_grupo_id", referencedColumnName = "id", insertable = false, updatable = false)
    @OneToOne(optional = false)
    private MateriaPeriodoGrupo materiaPeriodoGrupo;
    @JoinColumn(name = "contenido_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contenido contenidoId;

    public Cumplimiento() {
    }

    public Cumplimiento(Integer materiaPeriodoGrupoId) {
        this.materiaPeriodoGrupoId = materiaPeriodoGrupoId;
    }

    public Integer getMateriaPeriodoGrupoId() {
        return materiaPeriodoGrupoId;
    }

    public void setMateriaPeriodoGrupoId(Integer materiaPeriodoGrupoId) {
        this.materiaPeriodoGrupoId = materiaPeriodoGrupoId;
    }

    public Short getEstado() {
        return estado;
    }

    public void setEstado(Short estado) {
        this.estado = estado;
    }

    public MateriaPeriodoGrupo getMateriaPeriodoGrupo() {
        return materiaPeriodoGrupo;
    }

    public void setMateriaPeriodoGrupo(MateriaPeriodoGrupo materiaPeriodoGrupo) {
        this.materiaPeriodoGrupo = materiaPeriodoGrupo;
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
        hash += (materiaPeriodoGrupoId != null ? materiaPeriodoGrupoId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Cumplimiento)) {
            return false;
        }
        Cumplimiento other = (Cumplimiento) object;
        if ((this.materiaPeriodoGrupoId == null && other.materiaPeriodoGrupoId != null) || (this.materiaPeriodoGrupoId != null && !this.materiaPeriodoGrupoId.equals(other.materiaPeriodoGrupoId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "dto.Cumplimiento[ materiaPeriodoGrupoId=" + materiaPeriodoGrupoId + " ]";
    }
    
}
