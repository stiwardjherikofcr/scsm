    <%@page import="dto.ContenidoUnidad"%>
<%@page import="dto.Unidad"%>
<%@page import="dto.TablaSeccion"%>
<%@page import="dto.Tabla"%>
<%@page import="dto.SeccionMicrocurriculo"%>
<%@page import="dto.TipoMateria"%>
<%@page import="dto.AreaFormacion"%>
<%@page import="dto.Microcurriculo"%>
<%-- 
        Document   : ver-microcurriculo
        Created on : 15-jun-2021, 23:36:56
        Author     : Stiward
    --%>

<%@page import="dto.Usuario"%>
<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="es">

    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>CSM Software</title>
        <meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
        <link rel="icon" href="<%=request.getContextPath()%>/CSM_Software/assets/img/icon.ico" type="image/x-icon" />

        <!-- Fonts and icons -->
        <script src="<%=request.getContextPath()%>/CSM_Software/assets/js/plugin/webfont/webfont.min.js"></script>
        <script>
            WebFont.load({
                google: {
                    "families": ["Lato:300,400,700,900"]
                },
                custom: {
                    "families": ["Flaticon", "Font Awesome 5 Solid", "Font Awesome 5 Regular", "Font Awesome 5 Brands",
                        "simple-line-icons"
                    ],
                    urls: ['<%=request.getContextPath()%>/CSM_Software/assets/css/fonts.min.css']
                },
                active: function () {
                    sessionStorage.fonts = true;
                }
            });
        </script>

        <!-- CSS Files -->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/CSM_Software/assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/CSM_Software/assets/css/atlantis.min.css">
        <link rel="stylesheet" href="<%=request.getContextPath()%>/CSM_Software/assets/css/microcurriculo.css">
        <!-- CSS Just for demo purpose, don't include it in your project -->
        <link rel="stylesheet" href="<%=request.getContextPath()%>/CSM_Software/assets/css/demo.css">
    </head>

    <body>
        <%
            Usuario user = (Usuario) request.getSession().getAttribute("usuario");
        %>
        <div class="wrapper">
            <div class="main-header">

                <!-- Logo Header -->
                <jsp:include page="/CSM_Software/includes/header.jsp" />
                <!-- End Logo Header -->

                <!-- Navbar Header -->
                <jsp:include page="/CSM_Software/includes/navbar.jsp" />
                <!-- End Navbar -->

            </div>

            <!-- Sidebar -->
            <jsp:include page="/CSM_Software/includes/sidebar.jsp" />
            <!-- End Sidebar -->

            <div class="main-panel">
                <div class="content">
                    <div class="page-inner">
                        <div class="page-header">
                            <h4 class="page-title">Ver Microcurriculo</h4>
                            <div class="ml-md-auto">
                                <ul class="breadcrumbs">
                                    <li class="nav-home">
                                        <a href="<%=request.getContextPath()%>/CSM_Software/CSM/director/dashboard/dashboard.jsp">
                                            <i class="flaticon-home"></i>
                                        </a>
                                    </li>
                                    <li class="separator">
                                        <i class="flaticon-right-arrow"></i>
                                    </li>
                                    <li class="nav-item">
                                        <a href="<%=request.getContextPath()%>/ControladorMicrocurriculo?accion=listarTodos">Microcurriculo</a>
                                    </li>
                                    <li class="separator">
                                        <i class="flaticon-right-arrow"></i>
                                    </li>
                                    <li class="nav-item">
                                        <a href="<%=request.getContextPath()%>/ControladorMicrocurriculo?accion=listarTodos">Consultar</a>
                                    </li>
                                    <li class="separator">
                                        <i class="flaticon-right-arrow"></i>
                                    </li>
                                    <li class="nav-item">
                                        <a href="#">Ver</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <%
                            dto.Microcurriculo microcurriculo = (dto.Microcurriculo) request.getSession().getAttribute("microcurriculo");
                            List<dto.AreaFormacion> areasFormacion = (List<dto.AreaFormacion>) request.getSession().getAttribute("areasFormacion");
                            List<dto.TipoAsignatura> tiposAsignatura = (List<dto.TipoAsignatura>) request.getSession().getAttribute("tipoAsignatura");
                        %>
                        <div class="row">
                            <!-- Contenido de Microcurriculo -->
                            <div class="col-md-10">
                                <!--Microcurriculo-->
                                <div class="card">
                                    <div class="card-header d-flex justify-content-center">
                                        <h2 class="card-title mtittle "> Microcurriculo</h2>
                                    </div>
                                    <div class="card-body pb-0 ">
                                        <table class="table table-hover ">
                                            <tbody>
                                                <tr>
                                                    <td class="text-right f border-right mth2"> Asignatura </td>
                                                    <td class="mt17" colspan="4"><%=microcurriculo.getMateria().getNombre()%></td>
                                                </tr>
                                                <tr>
                                                    <td class="text-right border-right mth2">Codigo</td>
                                                    <td class="mt17" colspan="4"><%=microcurriculo.getMateria().getMateriaPK().getCodigoMateria()%></td>
                                                </tr>
                                                <tr>
                                                    <td class="text-right border-right mth2">Area de Formacion</td>
                                                    <td colspan="4" class="mt17">
                                                        <div class="selectgroup w-100 d-flex justify-content-around">
                                                            <%
                                                                for (dto.AreaFormacion elem : areasFormacion) {
                                                                    if (microcurriculo.getAreaDeFormacionId().getId() == elem.getId()) {
                                                            %>
                                                            <label class="selectgroup-item pr-3">
                                                                <input type="radio" name="Areadeforamacion" value="50"
                                                                       class="selectgroup-input" checked="" disabled>
                                                                <span class="selectgroup-button"><%=microcurriculo.getAreaDeFormacionId().getNombre()%></span>
                                                            </label>
                                                            <%
                                                            } else {
                                                            %>
                                                            <label class="selectgroup-item pr-3">
                                                                <input type="radio" name="Areadeforamacion" value="50"
                                                                       class="selectgroup-input" disabled>
                                                                <span class="selectgroup-button"><%=elem.getNombre()%></span>
                                                            </label>
                                                            <%
                                                                    }
                                                                }
                                                            %>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="text-right border-right mth2">Tipos de Asignatura</td>
                                                    <td colspan="4" class="mt17">
                                                        <div class="selectgroup w-100 d-flex justify-content-around ">
                                                            <%
                                                                for (dto.TipoAsignatura elem : tiposAsignatura) {
                                                                    if (microcurriculo.getMateria().getTipoAsignaturaId().getId() == elem.getId()) {
                                                            %>
                                                            <label class="selectgroup-item pr-3">
                                                                <input type="radio" name="Tiposdeasignatura" value="50"
                                                                       class="selectgroup-input" checked="" disabled>
                                                                <span class="selectgroup-button"><%=microcurriculo.getMateria().getTipoAsignaturaId().getTipo()%></span>
                                                            </label>
                                                            <%
                                                            } else {
                                                            %>
                                                            <label class="selectgroup-item pr-3">
                                                                <input type="radio" name="Tiposdeasignatura" value="150"
                                                                       class="selectgroup-input" disabled>
                                                                <span class="selectgroup-button"><%=elem.getTipo()%></span>
                                                            </label>
                                                            <%
                                                                    }
                                                                }
                                                            %>
                                                        </div>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td class="text-right border-right mth2">Numero de Creditos</td>
                                                    <td class="mt17" colspan="4"><%=microcurriculo.getMateria().getCreditos()%></td>
                                                </tr>
                                                <tr>
                                                    <td class="text-right border-right mth2">Prerrequisitos </td>
                                                    <td class="mt17" colspan="4">
                                                        <%
                                                            for (dto.PrerrequisitoMateria prerrequisito : microcurriculo.getMateria().getPrerrequisitoMateriaList()) {
                                                        %>
                                                        <%=prerrequisito.getMateria1().getNombre()%><br><%}%>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <%
                                    List<String[][]> tablas = (List<String[][]>) request.getSession().getAttribute("tablas");
                                    List<dto.SeccionMicrocurriculo> secciones = microcurriculo.getSeccionMicrocurriculoList();
                                    for (dto.SeccionMicrocurriculo seccion : secciones) {
                                %>
                                <!-- Seccion -->  
                                <% int tipo = seccion.getSeccionId().getTipoSeccionId().getId();
                                    if (tipo == 1) {
                                %>
                                <div class="card" id="<%=seccion.getSeccionId().getNombre()%>">
                                    <div class="card-header">
                                        <div class="d-flex justify-content-center">
                                            <h4 class="card-title mtittle"><%=seccion.getSeccionId().getNombre()%></h4>
                                        </div>
                                    </div>
                                    <div class="card-body pb-0">
                                        <div class="w-100 d-flex justify-content-center">
                                            <p class="w-75 text-justify "><%=seccion.getContenidoList().get(0).getTexto()%></p>
                                        </div>
                                    </div>
                                </div>
                                <!-- Fin Seccion -->
                                <%   } else {
                                    int canColum = seccion.getTablaMicrocurriculoList().get(0).getCantidadColumnas();
                                %>
                                <!-- Tabla -->
                                <div class="card" id="<%=seccion.getSeccionId().getNombre()%>">
                                    <div class="card-header">
                                        <div class="d-flex justify-content-center">
                                            <h4 class="card-title mtittle "><%=seccion.getSeccionId().getNombre()%></h4>
                                        </div>
                                    </div>
                                    <div class="card-body pb-0">
                                        <table class="table table-hover">
                                            <thead>
                                                <tr>
                                                    <% for (int i = 0; i < canColum; i++) {%>
                                                    <th class="text-center" scope="col"><%=seccion.getTablaMicrocurriculoList().get(0).getEncabezadoTablaList().get(i).getEncabezadoId().getNombre()%></th>
                                                        <%}%>
                                                </tr>
                                            </thead>
                                            <tbody>
                                                <%  List<dto.TablaMicrocurriculoInfo> tablainfo = seccion.getTablaMicrocurriculoList().get(0).getTablaMicrocurriculoInfoList();
                                                    for (int i = 0; i < seccion.getTablaMicrocurriculoList().get(0).getCantidadFilas(); i++) {
                                                %>
                                                <tr>
                                                    <%
                                                        for (int j = 0; j < seccion.getTablaMicrocurriculoList().get(0).getCantidadColumnas(); j++) {
                                                    %>
                                                    <td class="text-center "><%=tablainfo.size() == 0 ? "" : tablas.get(seccion.getTablaMicrocurriculoList().get(0).getTablaMicrocurriculoPK().getId() - 1)[i][j]%></td>
                                                    <%}%>
                                                </tr>
                                                <% }%>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                                <!-- Fin Tabla -->
                                <%
                                        }
                                    }
                                %>
                            </div>
                            <!-- Fin Contenido de Microcurriculo -->

                            <!-- Navegacion de Microcurriculo -->
                            <div class="col-md-2 position-relative">
                                <div class=" position-fixed ">
                                    <h2 class="d-flex justify-content-center font-weight-bold">Navegacion</h2>
                                    <%
                                        for (dto.SeccionMicrocurriculo seccion : secciones) {
                                    %>
                                    <a href="#<%=seccion.getSeccionId().getNombre()%>">
                                        <div class="card btn btn-light mb-2 p-2 d-flex justify-content-center">
                                            <h4 class="card-title "><%=seccion.getSeccionId().getNombre()%></h4>
                                        </div>
                                    </a>
                                    <%
                                        }
                                    %>      
                                </div>
                            </div>
                            <!-- Fin Navegacion de Microcurriculo -->
                        </div>
                    </div>
                </div>

                <!-- Footer -->
                <jsp:include page="/CSM_Software/includes/footer.jsp" />
                <!-- End Footer -->                 

            </div>
        </div>
        <!--   Core JS Files   -->
        <script src="<%=request.getContextPath()%>/CSM_Software/assets/js/core/jquery.3.2.1.min.js"></script>
        <script src="<%=request.getContextPath()%>/CSM_Software/assets/js/core/popper.min.js"></script>
        <script src="<%=request.getContextPath()%>/CSM_Software/assets/js/core/bootstrap.min.js"></script>
        <!-- jQuery UI -->
        <script src="<%=request.getContextPath()%>/CSM_Software/assets/js/plugin/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
        <script src="<%=request.getContextPath()%>/CSM_Software/assets/js/plugin/jquery-ui-touch-punch/jquery.ui.touch-punch.min.js"></script>

        <!-- jQuery Scrollbar -->
        <script src="<%=request.getContextPath()%>/CSM_Software/assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
        <!-- Atlantis JS -->
        <script src="<%=request.getContextPath()%>/CSM_Software/assets/js/atlantis.min.js"></script>
        <script>
            $(document).ready(function () {
                document.getElementById('consultar_microcurriculo').classList.toggle('active');
            });
        </script>
    </body>

</html>
