<%-- 
    Document   : edit-profile
    Created on : 15-jun-2021, 23:38:18
    Author     : Stiward
--%>

<%@page import="dto.Usuario"%>
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
                "families": ["Flaticon", "Font Awesome 5 Solid", "Font Awesome 5 Regular",
                    "Font Awesome 5 Brands",
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
        <div class="h-100 sidebar sidebar-style-2">
            <div class="h-100 sidebar-wrapper scrollbar scrollbar-inner">
                <div class="sidebar-content">
                    <div>
                        <div class="user">
                            <div class="avatar-sm float-left mr-2">
                                <img src="<%=request.getContextPath()%>/CSM_Software/assets/img/profile.jfif" alt="..." class="avatar-img rounded-circle">
                            </div>
                            <div class="info">
                                <a data-toggle="collapse" href="#tables">
                                    <span>
                                        <%=user.getDocente().getNombre()%>
                                        <span class="user-level"><%=user.getRol().getRol()%></span>
                                        <span class="caret"></span>
                                    </span>
                                </a>
                                <div class="clearfix"></div>
                                <div class="collapse show" id="tables">
                                    <ul class="nav">
                                        <li>
                                            <a href="<%=request.getContextPath()%>/CSM_Software/CSM/director/dashboard/perfil/profile.jsp">
                                                <span class="link-collapse">Perfil</span>
                                            </a>
                                        </li>
                                        <li class="active">
                                            <a href="<%=request.getContextPath()%>/CSM_Software/CSM/director/dashboard/perfil/edit-profile.jsp">
                                                <span class="link-collapse">Configuraci??n</span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="<%=request.getContextPath()%>/CSM_Software/CSM/sign-in/singin.jsp">
                                                <span class="link-collapse">Salir</span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </div>
                        </div>
                        <ul class="nav nav-primary">
                            <li class="nav-item">
                                <a href="<%=request.getContextPath()%>/CSM_Software/CSM/director/dashboard/dashboard.jsp"
                                    class="collapsed" aria-expanded="false">
                                    <i class="fas fa-home"></i>
                                    <p>Dashboard</p>
                                </a>
                            </li>
                            <li class="nav-section">
                                <span class="sidebar-mini-icon">
                                    <i class="fa fa-ellipsis-h"></i>
                                </span>
                                <h4 class="text-section">Components</h4>
                            </li>
                            <li class="nav-item">
                                <a href="<%=request.getContextPath()%>/ControladorPensum?accion=listarPensum">
                                    <i class="fas fa-layer-group"></i>
                                    <p>Pensum</p>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a data-toggle="collapse" href="#sidebarLayouts">
                                    <i class="fas fa-th-list"></i>
                                    <p>Microcurriculo</p>
                                    <span class="caret"></span>
                                </a>
                                <div class="collapse" id="sidebarLayouts">
                                    <ul class="nav nav-collapse">
                                        <li>
                                            <a href="<%=request.getContextPath()%>/ControladorMicrocurriculo?accion=listarTodos">
                                                <span class="sub-item">Consultar Microcurriculo</span>
                                            </a>
                                        </li>
                                        <li>
                                            <a href="<%=request.getContextPath()%>/CSM_Software/CSM/director/dashboard/microcurriculo/solicitudes-microcurriculo.jsp">
                                                <span class="sub-item">Solicitudes Microcurriculo</span>
                                            </a>
                                        </li>
                                    </ul>
                                </div>
                            </li>
                            <li class="nav-item">
                                <a href="<%=request.getContextPath()%>/CSM_Software/CSM/director/dashboard/seguimiento.jsp">
                                    <i class="fas fa-chart-bar"></i>
                                    <p>Seguimiento</p>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a href="<%=request.getContextPath()%>/ControladorGrupos?accion=listar">
                                    <i class="fas fa-table"></i>
                                    <p>Grupos</p>
                                </a>
                            </li>
                            <li class="nav-item">
                                <a href="<%=request.getContextPath()%>/ControladorDocente?action=listarDocente">
                                    <i class="fas fa-user-friends"></i>
                                    <p>Docentes</p>
                                </a>
                            </li>
                        </ul>
                        <div class=" d-flex justify-content-center align-items-end w-100 logoUFPS">
                            <img src="<%=request.getContextPath()%>/CSM_Software/assets/img/Logo-nuevo-vertical.png" alt="..." class="avatar-img w-75">
                        </div>
                    </div>
                </div>
            </div>
        </div>
        <!-- End Sidebar -->

        <div class="main-panel">
            <div class="content">
                <div class="page-inner">
                    <div class="page-header">
                        <h4 class="page-title">Perfil</h4>
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
                                    <a href="#">Perfil</a>
                                </li>
                            </ul>
                        </div>
                    </div>
                    <div class="row">
                        <!-- Perfil -->
                        <div class="col-md-3">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title"><b>Perfil</b></h4>
                                </div>
                                <div class="card-body d-flex justify-content-center">
                                    <p class="demo">
                                        <div class="avatar avatar-xxl">
                                            <img src="<%=request.getContextPath()%>/CSM_Software/assets/img/profile.jfif" alt="..." class="avatar-img rounded-circle">
                                        </div>
                                    </p>
                                </div>
                            </div>
                        </div>
                        <!-- End Perfil -->

                        <!-- Detalles Usuario -->
                        <div class="col-md-9">
                            <div class="card">
                                <div class="card-header">
                                    <h4 class="card-title"><b>Detalles del Usuario</b></h4>
                                </div>
                                <div class="card-body">
                                    <div class="table-responsive">
                                        <table class="table table-hover">
                                            <tbody>
                                                <tr>
                                                    <th><b>Codigo</b></th>
                                                    <th><%=user.getDocente().getCodigoDocente()%></th>
                                                </tr>
                                                <tr>
                                                    <td><b>Nombres</b></td>
                                                    <td><%=user.getDocente().getNombre()%></td>
                                                </tr>
                                                <tr>
                                                    <td><b>Apellidos</b></td>
                                                    <td><%=user.getDocente().getApellido()%></td>
                                                </tr>
                                                <tr>
                                                    <td><b>Facultad</b></td>
                                                    <td>Ingenieria</td>
                                                </tr>
                                                <tr>
                                                    <td><b>Departamento</b></td>
                                                    <td><%=user.getDocente().getDepartamentoId().getNombreDepartamento()%>
                                                    </td>
                                                </tr>
                                                <tr>
                                                    <td><b>Programa</b></td>
                                                    <td>Ingenier??a de Sistemas</td>
                                                </tr>
                                                <tr>
                                                    <td><b>Rol</b></td>
                                                    <td><%=user.getRol().getRol()%></td>
                                                </tr>
                                                <tr>
                                                    <td><b>Estado</b></td>
                                                    <td><%=(user.getDocente().getEstado() == 1) ? "Activo" : "Inactivo"%>
                                                    </td>
                                                </tr>
                                            </tbody>
                                        </table>
                                    </div>
                                </div>
                            </div>
                        </div>
                        <!-- End Detalles Usuario -->
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
</body>

</html>