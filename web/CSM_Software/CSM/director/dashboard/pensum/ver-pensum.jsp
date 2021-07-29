<%-- 
    Document   : ver-pensum
    Created on : 15-jun-2021, 23:45:57
    Author     : Stiward
--%>

<%@page import="dto.Materia"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <!-- Estilos CSS -->
        <link rel="stylesheet" href="../../../../assets/css/ver-pensum.css">
    </head>

    <body>

        <!-- Modal -->
        <div class="modal fade" id="calculodiferencial" tabindex="-1" role="dialog" aria-labelledby="exampleModalLongTitle"
             aria-hidden="true">
            <div class="modal-dialog" role="document">
                <div class="modal-content">
                    <div class="modal-header no-bd">
                        <h4 class="modal-title">
                            <b>SEMINARIO INVESTIGATIVO I - 115 5306-B</b>
                        </h4>
                        <button type="button" class="close" data-dismiss="modal" aria-label="Close">
                            <span aria-hidden="true">&times;</span>
                        </button>
                    </div>
                    <div class="modal-body">
                        <form>
                            <div class="row">
                                <div class="col-12">
                                    <div class="form-group form-group-default">
                                        <h6><b>CONTENIDO</b></h6>

                                        <p>UNIDAD 1. Medios, instrumentos, técnicas y
                                            método en la recolección de datos e
                                            información.</p>

                                        <p>UNIDAD 2. Tabulación, análisis e interpretación
                                            de datos.</p>


                                        <p>UNIDAD 3. Informe y presentación de los
                                            datos de una investigación.</p>
                                    </div>
                                </div>
                                <div class="col-12">
                                    <div class="form-group form-group-default">
                                        <h6><b>BIBLIOGRAFIA</b></h6>

                                        <p>- CERDA HUGO. Los elementos de la Investigación,
                                            como reconocerlos, diseñarlos y construirlos.
                                            Tercera edición, 2008. Editorial El Buho.</p>

                                        <p>- MUÑOZ G. Jose, QUINTERO C. Josefina. Como
                                            desarrollar competencias investigativas en
                                            educación. 4 edición. Editorial Magisterio.</p>

                                        <p>- HERNANDEZ Sampieri; FERNANDEZ Collado; BAPTISTA
                                            Lucio. Metodología de la investigación. McGraw
                                            Hill. 2006.</p>

                                        <p>- MÉNDEZ A. Carlos E. Metodología. McGraw Hill.
                                            2005.</p>
                                    </div>
                                </div>
                            </div>
                        </form>
                    </div>
                    <div class="modal-footer no-bd">
                        <button type="button" class="btn btn-danger" data-dismiss="modal">Close</button>
                    </div>
                </div>
            </div>
        </div>
        <%
            List<Materia> materiasSemestre[] = (List<Materia>[])request.getSession().getAttribute("materiasSemestre");
            if(materiasSemestre!=null && materiasSemestre.length>0){
                String semestre = "SEMESTRE";
                String titles[] = {"PRIMER "+semestre, "SEGUNDO "+semestre, "TERCERO "+semestre, "CUARTO "+semestre, "QUINTO "+semestre, 
                                   "SEXTO "+semestre, "SEPTIMO "+semestre, "OCTAVO "+semestre, "NOVENO "+semestre, "DECIMO "+semestre};
        %>
        <div class="container">
        <%
                int i=0;
                for(List<Materia> materias: materiasSemestre){
        %>
            <div class="card">
                <div class="table-responsive">
                    <table class="table">
                        <thead>
                            <tr>
                                <th>
                                    <h3 class="title"><%= titles[i++]%></h3>
                                </th>
                            </tr>
                        </thead>
                        <tbody>
                            <%
                                for(Materia materia: materias){
                            %>
                            <tr>
                                <td>
                                    <button class="btn btn-light btn-round ml-auto" data-toggle="modal"
                                            data-target="#calculodiferencial">
                                        <b><%=materia.getMateriaPK().getCodigoMateria()%></b><br />
                                        <%=materia.getNombre()%><br />
                                        Horas:<%=materia.getHt()%> Cred:<%=materia.getCr()%>
                                    </button>
                                </td>
                            </tr>
                            <%}%>
                            </tbody>
                    </table>
                </div>
            </div>
        <%
                }
            }
        %>
        
        <!--   Core JS Files   -->
        <script src="../../../../assets/js/core/jquery.3.2.1.min.js"></script>
        <script src="../../../../assets/js/core/popper.min.js"></script>
        <script src="../../../../assets/js/core/bootstrap.min.js"></script>
        <!-- jQuery UI -->
        <script src="../../../../assets/js/plugin/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
        <script src="../../../../assets/js/plugin/jquery-ui-touch-punch/jquery.ui.touch-punch.min.js"></script>

        <!-- jQuery Scrollbar -->
        <script src="../../../../assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
        <!-- Datatables -->
        <script src="../../../../assets/js/plugin/datatables/datatables.min.js"></script>
        <!-- Atlantis JS -->
        <script src="../../../../assets/js/atlantis.min.js"></script>
        <!-- Atlantis DEMO methods, don't include it in your project! -->
        <script src="../../../../assets/js/setting-demo2.js"></script>
    </body>

</html>