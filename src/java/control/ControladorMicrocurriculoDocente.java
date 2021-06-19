/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dto.Docente;
import dto.Usuario;
import java.io.IOException;
import java.io.PrintWriter;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocio.Login;
import negocio.RolDocente;

/**
 *
 * @author Jhoser
 */
@WebServlet(name = "ControladorMicrocurriculoDocente", urlPatterns = {"/ControladorMicrocurriculoDocente"})
public class ControladorMicrocurriculoDocente extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");

    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        PrintWriter pw = response.getWriter();
        try {
            switch (request.getParameter("accion")) {
                case "listarTodos":
                    microDocentes(request, response);
                    break;
            }
            pw.println("<h1>Hizo algo</h1>");
        } catch (Exception e) {
            pw.println("<h1>Error</h1>");
            e.printStackTrace();
            System.err.println(e);
        }
        pw.flush();
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    public void microDocentes(HttpServletRequest request, HttpServletResponse response) throws IOException {
        RolDocente rd = new RolDocente();
        Usuario u = (Usuario) request.getSession().getAttribute("usuario");
        Usuario nuevoUser = rd.buscarUsuario(u.getDocente().getCodigoDocente());
        Login l = new Login();
        Docente d = l.obtenerDocente(nuevoUser.getDocente().getCodigoDocente());
        int codigo = nuevoUser.getDocente().getCodigoDocente();
        request.getSession().setAttribute("misMicrocurriculos", rd.microcurriculosDocentes(codigo));
        response.sendRedirect("jspTest/microcurriculoDocente.jsp");
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
