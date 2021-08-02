/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package control;

import dto.Materia;
import dto.Pensum;
import dto.Programa;
import dto.Usuario;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import negocio.AdministrarPensum;
import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 *
 * @author Dunke
 */
@WebServlet(name = "ControladorPensum", urlPatterns = {"/ControladorPensum"})
public class ControladorPensum extends HttpServlet {

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
        switch (request.getParameter("accion")) {
            case "registrar":
                this.registrar(request, response);
                break;
            case "obtenerPensums":
                listarPensums(request, response);
                break;
            case "listarMaterias":
                listarMaterias(request, response);
                break;
            case "listarPensum":
                this.listarPensum2(request, response);
                break;
            case "listarPensumDocente":
                this.listarPensum3(request, response);
                break;
            case "ver":
                this.verPensum(request, response);
                break;
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
        this.processRequest(request, response);
    }

    public void listarPensum2(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //listar pensum
        AdministrarPensum admin = new AdministrarPensum();
        
        Usuario u = (Usuario) request.getSession().getAttribute("usuario");
        Programa programa = (Programa) request.getSession().getAttribute("programaSesion");
        List<Pensum> pensum = programa.getPensumList();
        
        request.getSession().setAttribute("listaPensum2", pensum);
        //lista materias del pensum
        int pensumCodigo = pensum.get(0).getPensumPK().getCodigo();
        List<dto.Materia> materias = admin.obtenerPensum(pensumCodigo, programa.getCodigo()).getMateriaList();
        request.getSession().setAttribute("listaMateriasTodas", materias);
        response.sendRedirect("CSM_Software/CSM/director/dashboard/pensum.jsp");
    }

    public void listarPensum3(HttpServletRequest request, HttpServletResponse response) throws IOException {
        //listar pensum
        AdministrarPensum admin = new AdministrarPensum();
        Usuario u = (Usuario) request.getSession().getAttribute("usuario");
        
        Programa programa = (Programa) request.getSession().getAttribute("programaSesion");
        List<Pensum> pensum = programa.getPensumList();
        request.getSession().setAttribute("listaPensum3", pensum);
        
        //lista materias del pensum
        int pensumCodigo = pensum.get(0).getPensumPK().getCodigo();
        List<dto.Materia> materias = admin.obtenerPensum(pensumCodigo, programa.getCodigo()).getMateriaList();
        request.getSession().setAttribute("listaMateriasTodas", materias);
        response.sendRedirect("CSM_Software/CSM/docente/dashboard/pensum.jsp");
    }

    private void registrar(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        try {
            Integer id_programa = 0;
            InputStream pensumFile = null;
            List<FileItem> items = new ServletFileUpload(new DiskFileItemFactory()).parseRequest(request);
            for (FileItem item : items) {
                if (item.isFormField()) {
                    switch (item.getFieldName()) {
                        case "programa":
                            id_programa = Integer.parseInt(item.getString());
                    }
                } else {
                    pensumFile = item.getInputStream();
                }
            }
            AdministrarPensum ap = new AdministrarPensum(request.getServletContext().getRealPath("/"));
            request.getSession().setAttribute("pensum", ap.registrar(id_programa, pensumFile));
            response.sendRedirect("ControladorMicrocurriculo?accion=registrar");
        } catch (FileUploadException e) {
            throw new ServletException("Cannot parse multipart request.", e);
        } catch (Exception e) {
            e.printStackTrace();
        }
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
        this.processRequest(request, response);
    }

    private void listarMaterias(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = new PrintWriter(response.getOutputStream());
        response.setContentType("text/html;charset=UTF-8");
        AdministrarPensum admin = new AdministrarPensum();
        Programa programa = (Programa) request.getSession().getAttribute("programaSesion");
        int pensumCodigo = Integer.parseInt(request.getParameter("pensumCodigo"));
        List<dto.Materia> materias = admin.obtenerPensum(pensumCodigo, programa.getCodigo()).getMateriaList();
        for (dto.Materia m : materias) {
            pw.println("<option value=" + m.getMateriaPK().getCodigo()+ ">" + m.getMateriaPK().getCodigo() + "-" + m.getNombre() + "</option>");
        }
        pw.flush();
    }

    private void listarPensums(HttpServletRequest request, HttpServletResponse response) throws IOException {
        PrintWriter pw = new PrintWriter(response.getOutputStream());
        response.setContentType("text/html; charset=UTF-8");
        dto.Programa programa = (dto.Programa) request.getSession().getAttribute("programaSesion");
        List<Pensum> pensums = programa.getPensumList();
        for (Pensum p : pensums) {
            pw.println("<option value=" + p.getPensumPK().getCodigo() + ">" + p.getPrograma().getCodigo() + "-" + p.getPensumPK().getCodigo() + "</option>");
        }
        pw.flush();
    }

    private void verPensum(HttpServletRequest request, HttpServletResponse response) throws IOException {
        Usuario user = (Usuario) request.getSession().getAttribute("usuario");
        Integer cod = Integer.parseInt(request.getParameter("cod"));
        AdministrarPensum adminPemsum = new AdministrarPensum();
        List<Materia> materias[] = adminPemsum.cargarMateriasPorSemestre(this.getPensum(user.getDocente().getProgramaCodigo().getPensumList(), cod));
        request.getSession().setAttribute("materiasSemestre", materias);
        response.sendRedirect("CSM_Software/CSM/director/dashboard/pensum.jsp");
    }

    private Pensum getPensum(List<Pensum> pensums, int cod) {
        for (Pensum pensum : pensums) {
            if (pensum.getPensumPK().getCodigo() == cod) {
                return pensum;
            }
        }
        return null;
    }

//    public static void cargarMateriasPorSemestre(HttpServletRequest request, Usuario user, int cod){
//        List<Materia> materiasSemestre[] = new List[10];
//        for(Pensum pensum : user.getDocente().getProgramaList().get(0).getPensumList()){
//            if(pensum.getPensumPK().getCodigo()!=cod) continue;
//            for(Materia m: pensum.getMateriaList()){
//                int semestre = m.getSemestre()-1;
//                if(materiasSemestre[semestre] == null)
//                    materiasSemestre[semestre] = new ArrayList<>();
//                materiasSemestre[semestre].add(m);
//            }
//            break;
//        }
//        
//    }
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
