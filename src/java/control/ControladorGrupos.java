/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.exceptions.IllegalOrphanException;
import dao.exceptions.NonexistentEntityException;
import dto.Docente;
import dto.MateriaPeriodo;
import dto.Programa;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocio.AdministrarDocentes;
import negocio.AdministrarGrupos;

/**
 *
 * @author Jhoser
 */
@WebServlet(name = "ControladorGrupos", urlPatterns = {"/ControladorGrupos"})
public class ControladorGrupos extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion.equalsIgnoreCase("listar")) {
            listar(request, response);
        }
        if (accion.equalsIgnoreCase("Registrar Grupo")) {
            try {
                crearGrupo(request, response);
            } catch (Exception ex) {
                Logger.getLogger(ControladorGrupos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        if (accion.equalsIgnoreCase("eliminar")) {
            try {
                eliminarMateriaPG(request, response);
            } catch (NonexistentEntityException | IllegalOrphanException ex) {
                Logger.getLogger(ControladorGrupos.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void eliminarMateriaPG(HttpServletRequest request, HttpServletResponse response) throws NonexistentEntityException, IOException, IllegalOrphanException {
        int id = Integer.parseInt(request.getParameter("id"));
        negocio.AdministrarGrupos admin = new AdministrarGrupos();
        admin.eliminarGrupo(id);
        listar(request, response);
    }

    public void crearGrupo(HttpServletRequest request, HttpServletResponse response) throws Exception {
        int codigoPensum = (Integer.parseInt(request.getParameter("optionPensum")));
        int optionMateria = (Integer.parseInt(request.getParameter("optionMateria")));
        int codigoDocente = (Integer.parseInt(request.getParameter("optionDocente")));
        int anio = Integer.parseInt(request.getParameter("anio"));
        int periodo = Integer.parseInt(request.getParameter("periodo"));
        MateriaPeriodo materiaPeriodo = validarMateriaPeriodo(request, response, anio, periodo, optionMateria, codigoPensum);
        validarMateriaPeriodoGrupo(request, response, materiaPeriodo, codigoDocente);
        response.sendRedirect("ControladorGrupos?accion=listar");
    }

    public void validarMateriaPeriodoGrupo(HttpServletRequest request, HttpServletResponse response, MateriaPeriodo materiaPeriodo, int codigoDocente) throws Exception {
        negocio.AdministrarGrupos admin = new negocio.AdministrarGrupos();
        admin.validarMateriaPeriodoGrupo(materiaPeriodo, codigoDocente);
    }

    public MateriaPeriodo validarMateriaPeriodo(HttpServletRequest request, HttpServletResponse response, int anio, int semestre, int codigoMateria, int codigoPensum) throws Exception {
        AdministrarGrupos admin = new AdministrarGrupos();
        return admin.validarMateriaPeriodo(anio, semestre, codigoMateria, codigoPensum);
    }

    public void listar(HttpServletRequest request, HttpServletResponse response) throws IOException {
        cargarDocentes(request, response);
        cargarGrupos(request, response);
        response.sendRedirect("CSM_Software/CSM/director/dashboard/grupos.jsp");
    }

    public void cargarGrupos(HttpServletRequest request, HttpServletResponse response) {
        AdministrarGrupos admin = new AdministrarGrupos();
        Programa programa = (Programa) request.getSession().getAttribute("programaSesion");
        
        List<dto.MateriaPeriodoGrupo> grupos = admin.obtenerMateriaPeriodoGrupo(programa);
        
        request.getSession().setAttribute("grupos", grupos);
    }

    public void cargarDocentes(HttpServletRequest request, HttpServletResponse response) {
        AdministrarGrupos adminGrupos = new AdministrarGrupos();
        Programa programa = (Programa) request.getSession().getAttribute("programaSesion");
        
        List<Docente> docentes = adminGrupos.obtenerDocentes(programa);
        
        request.getSession().setAttribute("docentesPrograma", docentes);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        try {
            switch (request.getParameter("action")) {
                case "optionDocente":
                    break;
                case "optionMateria":
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void optionDocente(HttpServletRequest request, HttpServletResponse response) throws IOException, Exception {
        AdministrarDocentes admin = new AdministrarDocentes();
        Programa programa = (Programa) admin.obtenerDocentesPrograma((Programa) (request.getSession().getAttribute("programaSesion")));
        
        List<Docente> docentes = admin.obtenerDocentesPrograma(programa);
        
        request.getSession().setAttribute("docentesPrograma", docentes);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
