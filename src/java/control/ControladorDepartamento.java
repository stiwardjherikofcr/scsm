/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dao.DepartamentoJpaController;
import dto.Departamento;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import util.Conexion;

/**
 *
 * @author Jhoser
 */
@WebServlet(name = "ControladorDepartamento", urlPatterns = {"/ControladorDepartamento"})
public class ControladorDepartamento extends HttpServlet {

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
        try (PrintWriter out = response.getWriter()) {
            /* TODO output your page here. You may use following sample code. */
            out.println("<!DOCTYPE html>");
            out.println("<html>");
            out.println("<head>");
            out.println("<title>Servlet ControladorDepartamento</title>");
            out.println("</head>");
            out.println("<body>");
            out.println("<h1>Servlet ControladorDepartamento at " + request.getContextPath() + "</h1>");
            out.println("</body>");
            out.println("</html>");
        }
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
        switch (request.getParameter("accion")) {
            case "listar":
                listarDepartamento(request, response);
                break;
        }
    }

    private void listarDepartamento(HttpServletRequest request, HttpServletResponse response) throws IOException {
        response.setContentType("text/html;charset=ISO-8859-1");
        PrintWriter pw = new PrintWriter(response.getOutputStream());
        int id = Integer.parseInt(request.getParameter("query"));
        DepartamentoJpaController djpa = new DepartamentoJpaController(Conexion.getConexion().getBd());
        List<Departamento> depar = djpa.findDepartamentoEntities();
        for (Departamento d : depar) {
            if (d.getFacultadId().getId().equals(id)) {
                pw.println("<option value=" + d.getId() + ">" + d.getNombre()+ "</option>");
            }
        }
        pw.flush();
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
