/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package negocio;

import dao.CambioJpaController;
import dao.ContenidoJpaController;
import dao.EstadoJpaController;
import dao.MateriaJpaController;
import dao.MateriaPeriodoGrupoJpaController;
import dao.MicrocurriculoJpaController;
import dao.ProgramaJpaController;
import dao.SeccionJpaController;
import dao.SeccionMicrocurriculoJpaController;
import dao.TablaInfoJpaController;
import dao.TablaSeccionJpaController;
import dao.TipoMateriaJpaController;
import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dto.Cambio;
import dto.Contenido;
import dto.Docente;
import dto.Estado;
import dto.Materia;
import dto.MateriaPK;
import dto.MateriaPeriodoGrupo;
import dto.Microcurriculo;
import dto.MicrocurriculoPK;
import dto.Pensum;
import dto.Programa;
import dto.Seccion;
import dto.SeccionCambio;
import dto.SeccionMicrocurriculo;
import dto.Tabla;
import dto.TablaInfo;
import dto.TablaSeccion;
import dto.TipoMateria;
import dto.Usuario;
import java.util.ArrayList;
import java.util.List;
import util.Conexion;

/**
 *
 * @author Manuel
 */
public class AdministrarMicrocurriculo {

    public AdministrarMicrocurriculo() {
    }

    public List<Materia> obtenerMateriasDocentes(Usuario user) {
        Docente docente = user.getDocente();
        Conexion con = Conexion.getConexion();
        MateriaPeriodoGrupoJpaController grupoDao = new MateriaPeriodoGrupoJpaController(con.getBd());
        List<MateriaPeriodoGrupo> grupos = grupoDao.findMateriaPeriodoGrupoEntities();
        List<Materia> materias = new ArrayList<>();
        for (MateriaPeriodoGrupo grupo : grupos) {
            if (grupo.getDocenteCodigo().equals(docente)) {
                materias.add(grupo.getMateriaPeriodoId().getMateria());
            }
        }
        return materias;
    }

    public ArrayList<Microcurriculo> obtenerMicrocurriculosPensum(int codigo, int programaCodigo) {
        AdministrarPensum administrarPensum = new AdministrarPensum();
        Pensum pensum = administrarPensum.obtenerPensum(codigo, programaCodigo);
        
        ArrayList<dto.Microcurriculo> microcurriculos = new ArrayList<>();
        for (Materia materia : pensum.getMateriaList()) {
            microcurriculos.add(materia.getMicrocurriculo());
        }
        return microcurriculos;
    }

    public List<SeccionCambio> obtenerSeccionesCambios() {
        Conexion con = Conexion.getConexion();
        dao.SeccionCambioJpaController as = new dao.SeccionCambioJpaController(con.getBd());
        return as.findSeccionCambioEntities();
    }

    public Microcurriculo obtenerMicrocurriculo(int codigoMateria, int codigoPensum) {
        Conexion con = Conexion.getConexion();
        MicrocurriculoJpaController microDao = new MicrocurriculoJpaController(con.getBd());
        Microcurriculo microcurriculo = microDao.findMicrocurriculo(new MicrocurriculoPK(codigoMateria, codigoPensum));
        return microcurriculo;
    }

    public List<String[][]> ordenarTablaInfo(Microcurriculo microcurriculo) {
        List<String[][]> tablas = new ArrayList<>();
        
        for (SeccionMicrocurriculo seccionMicrocurriculo : microcurriculo.getSeccionMicrocurriculoList()) {
            if (seccionMicrocurriculo.getSeccionId().getTipoSeccionId().getId() == 2) {
                TablaSeccion tablaSeccion = seccionMicrocurriculo.getTablaSeccion();
                Tabla table = tablaSeccion.getTablaId();
                List<TablaInfo> tablaInfos = tablaSeccion.getTablaInfoList();
                String tablaMatriz[][] = new String[tablaInfos.size()/table.getEncabezadoList().size()][table.getEncabezadoList().size()];
                
                for(TablaInfo tablaInfo: tablaInfos){
                    tablaMatriz[tablaInfo.getTablaInfoPK().getFila()][tablaInfo.getTablaInfoPK().getColumna()] = tablaInfo.getContenidoId().getTexto();
                }
                
                tablas.add(tablaMatriz);
            }
        }
        return tablas;
    }

    public List<Materia> obtenerTodasMateria(Programa programa) {
        List<Pensum> pensums = programa.getPensumList();
        
        List<Materia> listmaterias = new ArrayList<>();
        for (Pensum pensum : pensums) {
            listmaterias.addAll(pensum.getMateriaList());
        }
        return listmaterias;
    }
    
    public void actualizarAreaFormacionMicrocurriculo(Microcurriculo microcurriculo, int areaFormacion) throws NonexistentEntityException, Exception {
        Conexion con = Conexion.getConexion();
        MicrocurriculoJpaController daoMicrocurriculo = new MicrocurriculoJpaController(con.getBd());
        microcurriculo.setAreaDeFormacionId(new dto.AreaFormacion(areaFormacion));
        daoMicrocurriculo.edit(microcurriculo);
    }

    public void deleteDataTable(TablaSeccion tabla) throws NonexistentEntityException, IllegalOrphanException {
        Conexion con = Conexion.getConexion();
        TablaInfoJpaController infoDao = new TablaInfoJpaController(con.getBd());
        ContenidoJpaController contDao = new ContenidoJpaController(con.getBd());
        
        List<TablaInfo> tablaInfos = tabla.getTablaInfoList();
        for (TablaInfo tablaInfo : tablaInfos) {
            infoDao.destroy(tablaInfo.getTablaInfoPK());
            contDao.destroy(tablaInfo.getContenidoId().getId());
        }
    }

    public void registrarContenidoTablas(String[][] contenidos, SeccionMicrocurriculo seccion) throws Exception {
        Conexion con = Conexion.getConexion();
        TablaSeccion tabla = seccion.getTablaSeccion();
        ContenidoJpaController contenidoDao = new ContenidoJpaController(con.getBd());
        TablaInfoJpaController tablaDao = new TablaInfoJpaController(con.getBd());
        
        for (int i = 0; i < contenidos.length; i++) {
            for (int j = 0; j < contenidos[i].length; j++) {
                Contenido contenido = new Contenido();
                contenido.setTexto(contenidos[i][j]);
                contenido.setCantidadItemsLista(0);
                contenido.setSeccionMicrocurriculoId(seccion);
                contenidoDao.create(contenido);
                
                TablaInfo tablainfo = new TablaInfo(i, j, tabla.getSeccionMicrocurriculoId());
                tablainfo.setTablaSeccion(tabla);
                tablainfo.setContenidoId(contenido);
                tablaDao.create(tablainfo);
            }
        }
    }

    public void ingresarContenidoSeccion(String info, SeccionMicrocurriculo seccionMicrocurriculo) throws NonexistentEntityException, Exception {
        Conexion con = Conexion.getConexion();
        ContenidoJpaController contDao = new ContenidoJpaController(con.getBd());
                
        Contenido contenido = seccionMicrocurriculo.getContenidoList().get(0);
        contenido.setSeccionMicrocurriculoId(seccionMicrocurriculo);
        contenido.setCantidadItemsLista(0);
        contenido.setTexto(info);
        contDao.edit(contenido);
    }

    public List<Seccion> obtenerSecciones() {
        Conexion con = Conexion.getConexion();
        SeccionJpaController daoSeccion = new SeccionJpaController(con.getBd());
        return daoSeccion.findSeccionEntities();

    }

    public void registrarMicrocurriculos(Pensum pensum) {
        new RegistroMicrocurriculoBackground(pensum).start();
    }

    public List<dto.AreaFormacion> obtenerAreasFormacion() {
        Conexion con = Conexion.getConexion();
        dao.AreaFormacionJpaController daoAreasFormacion = new dao.AreaFormacionJpaController(con.getBd());

        return daoAreasFormacion.findAreaFormacionEntities();
    }

    public List<TipoMateria> obtenerTiposAisgnatura() {
        Conexion con = Conexion.getConexion();
        TipoMateriaJpaController tipoMateriaJpa = new TipoMateriaJpaController(con.getBd());
        return tipoMateriaJpa.findTipoMateriaEntities();
    }

    public void realizarSolicitudCambio(int idSeccionMicrocurriculo, String texto) {
        Conexion con = Conexion.getConexion();
        dao.SeccionMicrocurriculoJpaController daoSeccionMicrocurriculo = new dao.SeccionMicrocurriculoJpaController(con.getBd());
        SeccionMicrocurriculo smAntigua = daoSeccionMicrocurriculo.findSeccionMicrocurriculo(idSeccionMicrocurriculo);

        List<SeccionMicrocurriculo> lista = daoSeccionMicrocurriculo.findSeccionMicrocurriculoEntities();
        SeccionMicrocurriculo ultimoregistro = lista.get(lista.size() - 1);

        SeccionMicrocurriculo SMCNueva = new SeccionMicrocurriculo();
        SMCNueva.setMicrocurriculo(smAntigua.getMicrocurriculo());
        SMCNueva.setSeccionId(smAntigua.getSeccionId());
        SMCNueva.setEditable(smAntigua.getEditable());
        SMCNueva.setTablaSeccion(smAntigua.getTablaSeccion());

        daoSeccionMicrocurriculo.create(SMCNueva);

        EstadoJpaController estadodao = new EstadoJpaController(con.getBd());
        Estado estado = estadodao.findEstado(1);

        Cambio c = new Cambio();
        c.setEstadoId(estado);

        CambioJpaController cdao = new CambioJpaController(con.getBd());
        cdao.create(c);

        SeccionCambio sc = new SeccionCambio();
        sc.setSeccionMicrocurriculoIdAntigua(smAntigua);
        sc.setSeccionMicrocurriculoIdNuevo(SMCNueva);
        sc.setCambioId(c);

        dao.SeccionCambioJpaController scdao = new dao.SeccionCambioJpaController(con.getBd());
        scdao.create(sc);

        Contenido cont = new Contenido();
        cont.setCantidadItemsLista(0);
        cont.setTexto(texto);
        cont.setSeccionMicrocurriculoId(SMCNueva);

        dao.ContenidoJpaController codao = new dao.ContenidoJpaController(con.getBd());
        codao.create(cont);
    }

    public static void main(String args[]) {
        AdministrarMicrocurriculo a = new AdministrarMicrocurriculo();
        a.realizarSolicitudCambio(17045, "Estos son los 80");
    }

}
