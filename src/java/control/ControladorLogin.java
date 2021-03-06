/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dto.Pensum;
import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocio.AdministrarDocentes;
import negocio.AdministrarPensum;
import negocio.Login;

/**
 *
 * @author Manuel
 */
@WebServlet(name = "ControladorLogin", urlPatterns = {"/ControladorLogin"})
public class ControladorLogin extends HttpServlet {

    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
    }

    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        String accion = request.getParameter("accion");
        if (accion.equals("Iniciar Sesion")) {
            verificarUsuario(request, response);
        }
    }

    public static void verificarUsuario(HttpServletRequest request, HttpServletResponse response) throws IOException {
        int codigo = Integer.parseInt(request.getParameter("codigo"));
        String contrasena = request.getParameter("clave");
        int rol = Integer.parseInt(request.getParameter("rol"));
        Login login = new Login();
        boolean valido = login.validarUsuario(codigo, contrasena, rol);
        if (valido) {
            cargarInformacion(request, response, codigo, login, rol);
            response.sendRedirect("CSM_Software/CSM/"+(rol==1 ? "director" : "docente")+"/dashboard/dashboard.jsp");
        } else {
            response.sendRedirect("CSM_Software/CSM/sign-in/singin.jsp");
        }
    }

    public static void cargarInformacion(HttpServletRequest request, HttpServletResponse response, int codigo, Login login, int rol) {
        dto.Usuario usuario = login.obtenerUsuario(codigo, rol);
        request.getSession().setAttribute("usuario", usuario);
        if (rol == 1) {
            cargarPrograma(request, response, usuario);
            cargarLastPensum(request, response, usuario);
            request.getSession().setAttribute("numDocActivos", new AdministrarDocentes().getNumDocentesActivos());
        } else {
            cargarDepartamento(request, response, usuario);
        }
    }

    public static void cargarDepartamento(HttpServletRequest request, HttpServletResponse response, dto.Usuario usuario) {
        request.getSession().setAttribute("departamentoSesion", usuario.getDocente().getDepartamentoId().getNombreDepartamento());
    }

    public static void cargarPrograma(HttpServletRequest request, HttpServletResponse response, dto.Usuario usuario) {
        request.getSession().setAttribute("programaSesion", usuario.getDocente().getProgramaList().get(0));
    }
    
    public static void cargarLastPensum(HttpServletRequest request, HttpServletResponse response, dto.Usuario usuario) {
        AdministrarPensum adminPensum = new AdministrarPensum();
        Pensum pensum = adminPensum.getLastPensum(usuario);
        request.getSession().setAttribute("pensum", pensum);
        request.getSession().setAttribute("materiasSemestre", adminPensum.cargarMateriasPorSemestre(pensum));
    }

    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
