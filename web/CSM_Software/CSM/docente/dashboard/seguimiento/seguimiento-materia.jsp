<%-- 
    Document   : seguimiento-materia
    Created on : 17-jun-2021, 16:41:34
    Author     : Stiward
--%>

<%@page import="java.util.List"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>CSM Software</title>
        <meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
        <link rel="icon" href="../../../../assets/img/icon.ico" type="image/x-icon" />

        <!-- Fonts and icons -->
        <script src="../../../../assets/js/plugin/webfont/webfont.min.js"></script>
        <script>
            WebFont.load({
                google: {
                    "families": ["Lato:300,400,700,900"]
                },
                custom: {
                    "families": ["Flaticon", "Font Awesome 5 Solid", "Font Awesome 5 Regular", "Font Awesome 5 Brands",
                        "simple-line-icons"
                    ],
                    urls: ['../../../../assets/css/fonts.min.css']
                },
                active: function () {
                    sessionStorage.fonts = true;
                }
            });
        </script>

        <!-- CSS Files -->
        <link rel="stylesheet" href="../../../../assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="../../../../assets/css/atlantis.min.css">
        <!-- CSS Just for demo purpose, don't include it in your project -->
        <link rel="stylesheet" href="../../../../assets/css/demo.css">
    </head>

    <body>
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
                            <h4 class="page-title">Seguimiento Materia</h4>
                            <div class="ml-md-auto">
                                <ul class="breadcrumbs">
                                    <li class="nav-home">
                                        <a href="../dashboard.jsp">
                                            <i class="flaticon-home"></i>
                                        </a>
                                    </li>
                                    <li class="separator">
                                        <i class="flaticon-right-arrow"></i>
                                    </li>
                                    <li class="nav-item">
                                        <a href="../seguimiento.jsp">Seguimiento</a>
                                    </li>
                                    <li class="separator">
                                        <i class="flaticon-right-arrow"></i>
                                    </li>
                                    <li class="nav-item">
                                        <a href="#">Materia</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="row">
                            <!-- Tabla % Seguimiento -->
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header text-center">
                                        <div class="card-title">Porcentaje de Cumplimiento</div>
                                    </div>
                                    <div class="card-body">
                                        <div class="d-flex justify-content-center align-items-center"
                                             id="columnchart_values"></div>
                                    </div>
                                </div>
                            </div>
                            <!-- Fin Tabla % Seguimiento -->

                            <!-- Tabla Seguimiento -->
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex align-items-center">
                                            <a href="../seguimiento.jsp" class="collapsed mr-auto" aria-expanded="false">
                                                <i class="fas fa-arrow-left" style="font-size: 25px;"></i>
                                            </a>
                                            <h4 class="card-title mr-auto">ADMINISTRACION DE PROYECTOS INFORMATICOS -
                                                1155708</h4>
                                        </div>
                                    </div>
                                    <div class="card-body">
                                        <div class="table-responsive">
                                            <table id="basic-datatables"
                                                   class="display table table-striped table-hover text-center">
                                                <thead>
                                                    <tr>
                                                        <th>#</th>
                                                        <th>Código Docente</th>
                                                        <th>Nombre Docente</th>
                                                        <th>Grupo</th>
                                                        <th>Progreso</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tfoot>
                                                    <tr>
                                                        <th>#</th>
                                                        <th>Código Docente</th>
                                                        <th>Nombre Docente</th>
                                                        <th>Grupo</th>
                                                        <th>Progreso</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </tfoot>
                                                <tbody>
                                                    <%
                                                        List<Object[]> data = (List<Object[]>) request.getSession().getAttribute("listSeguimientoMateria");
                                                        for (Object row[] : data) {
                                                    %>
                                                    <tr>
                                                        <td><%=row[0] %></td>
                                                        <td><%=row[1] %></td>
                                                        <td><%=row[2] %></td>
                                                        <td><%=row[3] %></td>
                                                        <td>
                                                            <div class="progress" style="height: 6px;">
                                                                <div class="progress-bar bg-primary" role="progressbar"
                                                                     style="width: <%=row[4] %>%" aria-valuenow="70" aria-valuemin="0"
                                                                     aria-valuemax="100" data-toggle="tooltip"
                                                                     data-placement="top" title="" data-original-title="<%=row[4] %>%">
                                                                </div>
                                                            </div>
                                                        </td>
                                                        <td>
                                                            <div class="form-button-action">
                                                                <a href="<%=request.getContextPath()%>/ControladorSeguimiento?accion=ver-cumplimiento&mat_per_gr=<%=row[5] %>">
                                                                <!--a href="ver-cumplimiento.jsp"-->
                                                                    <button id="pensum" type="button" data-toggle="tooltip"
                                                                            title="" class="btn btn-link btn-dark"
                                                                            data-original-title="Check" style="color: black;">
                                                                        <i class="fas fa-clipboard-check"></i>
                                                                    </button>
                                                                </a>
                                                                <a href="programar-evaluacion.jsp">
                                                                    <button id="pensum" type="button" data-toggle="tooltip"
                                                                            title="" class="btn btn-link btn-dark"
                                                                            data-original-title="Evaluacion"
                                                                            style="color: black;">
                                                                        <i class="fas fa-clock"></i>
                                                                    </button>
                                                                </a>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <%}%>
                                                </tbody>
                                            </table>
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- Find Tabla Seguimiento -->
                        </div>
                    </div>
                </div>

                <footer class="footer">
                    <div class="container-fluid">
                        <nav class="pull-left">
                            <ul class="nav">
                                <li class="nav-item">
                                    <a class="nav-link" href="#">
                                        Nigth Devs
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">
                                        Help
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a class="nav-link" href="#">
                                        Licenses
                                    </a>
                                </li>
                            </ul>
                        </nav>
                        <div class="copyright ml-auto">
                            2021 &copy; All Rights Reserved.
                            Desarrollado por: <a href="#">Nigth Devs</a>
                        </div>
                    </div>
                </footer>
            </div>
        </div>
        <!--   Core JS Files   -->
        <script src="../../../../assets/js/core/jquery.3.2.1.min.js"></script>
        <script src="../../../../assets/js/core/popper.min.js"></script>
        <script src="../../../../assets/js/core/bootstrap.min.js"></script>
        <!-- jQuery UI -->
        <script src="../../../../assets/js/plugin/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
        <script src="../../../../assets/js/plugin/jquery-ui-touch-punch/jquery.ui.touch-punch.min.js"></script>
        <!-- Chart JS -->
        <script src="https://www.gstatic.com/charts/loader.js"></script>
        <!-- jQuery Scrollbar -->
        <script src="../../../../assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
        <!-- Datatables -->
        <script src="../../../../assets/js/plugin/datatables/datatables.min.js"></script>
        <!-- Atlantis JS -->
        <script src="../../../../assets/js/atlantis.min.js"></script>
        <!-- Atlantis DEMO methods, don't include it in your project! -->
        <script src="../../../../assets/js/setting-demo2.js"></script>
        <!-- Chart -->
        <script type="text/javascript">
            google.charts.load("current", {
                packages: ['corechart']
            });
            google.charts.setOnLoadCallback(drawChart);

            function drawChart() {
                var data = google.visualization.arrayToDataTable([
                    ["Materia", "Porcentaje", {
                            role: "style"
                        }],
                    ["Calculo Diferencial", 50, "#a81f2b"],
                    ["Matematicas Discretas", 10, "#a81f2b"],
                    ["Fundamentos de Programacion", 25, "#a81f2b"],
                    ["Introduccion Ing. Sistemas", 30, "#a81f2b"],
                    ["Comunicacion I", 50, "#a81f2b"],
                    ["Intro Vida Universitaria", 25, "#a81f2b"],
                    ["Calculo Integral", 89, "#a81f2b"],
                    ["Algebra Lineal", 100, "#a81f2b"],
                    ["Fisica Mecanica", 19, "#a81f2b"],
                    ["Programacion Orientada a Objectos", 30, "#a81f2b"],
                    ["Comunicacion II", 75, "#a81f2b"],
                    ["Seminario Integrador I", 50, "#a81f2b"],
                    ["Calculo Vectorial", 50, "#a81f2b"],
                    ["Fisica Electromagnetica", 27, "#a81f2b"],
                    ["Estructuras de Datos", 50, "#a81f2b"],
                    ["Programacion Orientada a Objectos II", 10, "#a81f2b"],
                    ["Seminario de Investigacion I", 25, "#a81f2b"],
                    ["Ecuaciones Diferenciales", 30, "#a81f2b"],
                    ["Probabilidad y Estadistica", 50, "#a81f2b"],
                    ["Ondas y Particulas", 25, "#a81f2b"],
                    ["Analisis de Algoritmos", 89, "#a81f2b"],
                    ["Teoria de la Computación", 100, "#a81f2b"],
                    ["Analisis Numerico", 19, "#a81f2b"],
                    ["Investigacion de Operaciones", 30, "#a81f2b"],
                    ["Electronica", 75, "#a81f2b"],
                    ["Arquitectura de Computadores", 50, "#a81f2b"],
                    ["Seminario de Investigacion II", 50, "#a81f2b"],
                    ["Sistemas Operativos", 27, "#a81f2b"],
                    ["Bases de Datos", 50, "#a81f2b"],
                    ["Programacion Web", 10, "#a81f2b"],
                    ["Constitucion y Civismo", 25, "#a81f2b"],
                    ["Planeacion Estrategica de Sistemas Informacion", 30, "#a81f2b"],
                    ["Teoria General de las Comunicaciones", 50, "#a81f2b"],
                    ["Analisis y Diseño de Sistemas", 25, "#a81f2b"],
                    ["Seminario de Investigacion III", 89, "#a81f2b"],
                    ["Etica Profesional", 100, "#a81f2b"],
                    ["Administracion de Proyectos Informaticos", 19, "#a81f2b"],
                    ["Programacion Orientada a Objectos", 30, "#a81f2b"],
                    ["Redes de Computadores", 75, "#a81f2b"],
                    ["Ingenieria de Software", 50, "#a81f2b"],
                    ["Formulacion y Evaluacion de Proyectos de Sistemas", 50, "#a81f2b"],
                    ["Seminario Integrador III", 27, "#a81f2b"],
                    ["Arquitectura de Software", 37, "#a81f2b"],
                    ["Gestion de Tics", 77, "#a81f2b"],
                    ["Practica en Ing. Sistemas", 87, "#a81f2b"]
                ]);


                var view = new google.visualization.DataView(data);
                view.setColumns([0, 1,
                    {
                        calc: "stringify",
                        sourceColumn: 1,
                        type: "string",
                        role: "annotation"
                    },
                    2
                ]);

                var options = {
                    width: 1500,
                    height: 500,
                    bar: {
                        groupWidth: "50%"
                    },
                    legend: {
                        position: "none"
                    },
                };
                var chart = new google.visualization.ColumnChart(document.getElementById("columnchart_values"));
                chart.draw(view, options);
            }
        </script>
        <!-- Filtros Tablas JS -->
        <script>
            $(document).ready(function () {
                // Basic
                $('#basic-datatables').DataTable({
                    "pageLength": 5,
                });
            });
        </script>
    </body>

</html>
