<%-- 
    Document   : pensum
    Created on : 15-jun-2021, 19:50:02
    Author     : Stiward
--%>

<%@page import="dto.Pensum"%>
<%@page import="java.util.List"%>
<%@page import="dto.Usuario"%>
<%@page import="negocio.AdministrarPensum"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>CSM Software</title>
        <meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
        <link rel="icon" href="../../../assets/img/icon.ico" type="image/x-icon" />

        <!-- Fonts and icons -->
        <script src="../../../assets/js/plugin/webfont/webfont.min.js"></script>
        <!-- Custom JS -->
        <script src="../../../assets/js/JQuery.js" type="text/javascript"></script>
        <script src="../../../assets/js/index.js" type="text/javascript"></script>
        <script>
            WebFont.load({
                google: {
                    "families": ["Lato:300,400,700,900"]
                },
                custom: {
                    "families": ["Flaticon", "Font Awesome 5 Solid", "Font Awesome 5 Regular", "Font Awesome 5 Brands",
                        "simple-line-icons"
                    ],
                    urls: ['../../../assets/css/fonts.min.css']
                },
                active: function () {
                    sessionStorage.fonts = true;
                }
            });
        </script>

        <!-- CSS Files -->
        <link rel="stylesheet" href="../../../assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="../../../assets/css/atlantis.min.css">
        <!-- CSS Just for demo purpose, don't include it in your project -->
        <link rel="stylesheet" href="../../../assets/css/demo.css">
    </head>

    <body>
        <%
            Usuario user = (Usuario) request.getSession().getAttribute("usuario");
        %>
        <div class="wrapper">
            <div class="main-header">
                <!-- Logo Header -->
                <div class="logo-header" data-background-color="red">
                    <a href="dashboard.jsp" style="text-decoration: none;">
                        <h2 class=" ml-3 text-white d-flex align-items-center">CSM Software</h2>
                    </a>
                    <button class="navbar-toggler sidenav-toggler ml-auto" type="button" data-toggle="collapse"
                            data-target="collapse" aria-expanded="false" aria-label="Toggle navigation">
                        <span class="navbar-toggler-icon">
                            <i class="icon-menu"></i>
                        </span>
                    </button>
                    <button class="topbar-toggler more"><i class="icon-options-vertical"></i></button>
                    <div class="nav-toggle">
                        <button class="btn btn-toggle toggle-sidebar">
                            <i class="icon-menu"></i>
                        </button>
                    </div>
                </div>
                <!-- End Logo Header -->

                <!-- Navbar Header -->
                <nav class="navbar navbar-header navbar-expand-lg" data-background-color="red">
                    <div class="container-fluid ">
                        <div class="d-inline-flex">
                            <div class="avatar"><img src="../../../assets/img/logo_ingsistemas.png"
                                                     alt="logotipo de ing Sistemas" class="avatar-img rounded"></div>
                            <h1 class=" ml-3 text-white d-flex align-items-center">Ingeniería de Sistemas</h1>
                        </div>
                        <ul class="navbar-nav topbar-nav ml-md-auto align-items-center">
                            <li class="nav-item dropdown hidden-caret">
                                <a class="nav-link dropdown-toggle" href="#" id="notifDropdown" role="button"
                                   data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                                    <i class="fa fa-bell"></i>
                                    <span class="notification">4</span>
                                </a>
                                <ul class="dropdown-menu notif-box animated fadeIn" aria-labelledby="notifDropdown">
                                    <li>
                                        <div class="dropdown-title">You have 4 new notification</div>
                                    </li>
                                    <li>
                                        <div class="notif-scroll scrollbar-outer">
                                            <div class="notif-center">
                                                <a href="#">
                                                    <div class="notif-icon notif-primary"> <i class="fa fa-user-plus"></i>
                                                    </div>
                                                    <div class="notif-content">
                                                        <span class="block">
                                                            New user registered
                                                        </span>
                                                        <span class="time">5 minutes ago</span>
                                                    </div>
                                                </a>
                                                <a href="#">
                                                    <div class="notif-icon notif-success"> <i class="fa fa-comment"></i>
                                                    </div>
                                                    <div class="notif-content">
                                                        <span class="block">
                                                            Rahmad commented on Admin
                                                        </span>
                                                        <span class="time">12 minutes ago</span>
                                                    </div>
                                                </a>
                                                <a href="#">
                                                    <div class="notif-img">
                                                        <img src="../../../assets/img/profile2.jpg" alt="Img Profile">
                                                    </div>
                                                    <div class="notif-content">
                                                        <span class="block">
                                                            Reza send messages to you
                                                        </span>
                                                        <span class="time">12 minutes ago</span>
                                                    </div>
                                                </a>
                                                <a href="#">
                                                    <div class="notif-icon notif-danger"> <i class="fa fa-heart"></i> </div>
                                                    <div class="notif-content">
                                                        <span class="block">
                                                            Farrah liked Admin
                                                        </span>
                                                        <span class="time">17 minutes ago</span>
                                                    </div>
                                                </a>
                                            </div>
                                        </div>
                                    </li>
                                    <li>
                                        <a class="see-all" href="javascript:void(0);">See all notifications<i
                                                class="fa fa-angle-right"></i> </a>
                                    </li>
                                </ul>
                            </li>
                            <li class="nav-item dropdown hidden-caret">
                                <a class="dropdown-toggle profile-pic" data-toggle="dropdown" href="#"
                                   aria-expanded="false">
                                    <div class="avatar-sm">
                                        <img src="../../../assets/img/profile.jfif" alt="..."
                                             class="avatar-img rounded-circle">
                                    </div>
                                </a>
                                <ul class="dropdown-menu dropdown-user animated fadeIn">
                                    <div class="dropdown-user-scroll scrollbar-outer">
                                        <li>
                                            <div class="user-box">
                                                <div class="avatar-lg">
                                                    <img src="../../../assets/img/profile.jfif" alt="image profile"
                                                         class="avatar-img rounded">
                                                </div>
                                                <div class="u-text">
                                                    <h4><%=user.getDocente().getNombre()%></h4>
                                                    <p class="text-muted"><%=user.getRol().getRol()%></p>
                                                </div>
                                            </div>
                                        </li>
                                        <li>
                                            <div class="dropdown-divider"></div>
                                            <a class="dropdown-item" href="perfil/profile.jsp">My Profile</a>
                                            <div class="dropdown-divider"></div>
                                            <a class="dropdown-item" href="perfil/edit-profile.jsp">Account Setting</a>
                                            <div class="dropdown-divider"></div>
                                            <a class="dropdown-item" href="../../sign-in/singin.jsp">Logout</a>
                                        </li>
                                    </div>
                                </ul>
                            </li>
                        </ul>
                    </div>
                </nav>
                <!-- End Navbar -->
            </div>

            <!-- Sidebar -->
            <div class="h-100 sidebar sidebar-style-2">
                <div class="h-100 sidebar-wrapper scrollbar scrollbar-inner">
                    <div class="sidebar-content">
                        <div>
                            <div class="user">
                                <div class="avatar-sm float-left mr-2">
                                    <img src="../../../assets/img/profile.jfif" alt="..." class="avatar-img rounded-circle">
                                </div>
                                <div class="info">
                                    <a data-toggle="collapse" href="#collapseExample" aria-expanded="true">
                                        <span>
                                            <%=user.getDocente().getNombre()%>
                                            <span class="user-level"><%=user.getRol().getRol()%></span>
                                            <span class="caret"></span>
                                        </span>
                                    </a>
                                    <div class="clearfix"></div>
                                    <div class="collapse in" id="collapseExample">
                                        <ul class="nav">
                                            <li>
                                                <a href="perfil/profile.jsp">
                                                    <span class="link-collapse">My Profile</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a href="perfil/edit-profile.jsp">
                                                    <span class="link-collapse">Account Setting</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a href="../../sign-in/singin.jsp">
                                                    <span class="link-collapse">Logout</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </div>
                            </div>
                            <ul class="nav nav-primary">
                                <li class="nav-item">
                                    <a href="dashboard.jsp" class="collapsed" aria-expanded="false">
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
                                <li class="nav-item active">
                                    <a href="../../../../ControladorPensum?accion=listarPensum">
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
                                                <a href="../../../../ControladorMicrocurriculo?accion=listarTodos">
                                                    <span class="sub-item">Consultar Microcurriculo</span>
                                                </a>
                                            </li>
                                            <li>
                                                <a href="microcurriculo/solicitudes-microcurriculo.jsp">
                                                    <span class="sub-item">Solicitudes Microcurriculo</span>
                                                </a>
                                            </li>
                                        </ul>
                                    </div>
                                </li>
                                <li class="nav-item">
                                    <a href="seguimiento.jsp">
                                        <i class="fas fa-chart-bar"></i>
                                        <p>Seguimiento</p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="grupos.jsp">
                                        <i class="fas fa-table"></i>
                                        <p>Grupos</p>
                                    </a>
                                </li>
                                <li class="nav-item">
                                    <a href="../../../../ControladorDocente?action=listarDocente">
                                        <i class="fas fa-user-friends"></i>
                                        <p>Docentes</p>
                                    </a>
                                </li>
                            </ul>
                            <div class=" d-flex justify-content-center align-items-end w-100 logoUFPS">
                                <img src="../../../assets/img/Logo-nuevo-vertical.png" alt="..." class="avatar-img w-75">
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
                            <div>
                                <h4 class="page-title">Pensum</h4>
                            </div>
                            <div class="ml-md-auto">
                                <ul class="breadcrumbs">
                                    <li class="nav-home">
                                        <a href="dashboard.jsp">
                                            <i class="flaticon-home"></i>
                                        </a>
                                    </li>
                                    <li class="separator">
                                        <i class="flaticon-right-arrow"></i>
                                    </li>
                                    <li class="nav-item">
                                        <a href="#">Pensum</a>
                                    </li>
                                </ul>
                            </div>
                        </div>
                        <div class="row">

                            <!-- Pensum Actual-->
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header">
                                        <div class="d-flex justify-content-center">
                                            <h4 class="card-title">Pensum 115 - 1</h4>
                                        </div>
                                    </div>
                                    <div class="card-body d-flex align-items-end">
                                        <iframe class="materialboxed" id="contenedor" src="pensum/ver-pensum.jsp"
                                                title="visualizar pensum" scrolling="auto" frameborder="0" height="850px"
                                                width="100%" allowfullscreen></iframe>
                                    </div>
                                </div>
                            </div>
                            <!-- Fin Pensum Actual -->

                            <!-- Card Pensum -->
                            <div class="col-md-12">
                                <div class="card">
                                    <div class="card-header row">
                                        <div class="col d-flex justify-content-center align-items-center">
                                            <h4 class="card-title  ">Listado Pensum</h4>
                                        </div>
                                        <button class="btn btn-primary btn-round ml-auto" data-toggle="modal"
                                                data-target="#addRowModal">
                                            <i class="fa fa-plus"></i>
                                            Cargar Pensum
                                        </button>
                                    </div>
                                    <div class="card-body">
                                        <!-- Modal -->
                                        <div class="modal fade" id="addRowModal" tabindex="-1" role="dialog"
                                             aria-hidden="true">
                                            <div class="modal-dialog" role="document">
                                                <div class="modal-content">
                                                    <div class="modal-header no-bd">
                                                        <h5 class="modal-title">Cargar Pensum</h5>
                                                        <button type="button" class="close" data-dismiss="modal"
                                                                aria-label="Close">
                                                            <span aria-hidden="true">&times;</span>
                                                        </button>
                                                    </div>
                                                    <div class="modal-body">
                                                        <form action="../../../../ControladorPensum?accion=registrar" method="POST" enctype="multipart/form-data">
                                                            <div class="row">
                                                                <div class="col-sm-6">
                                                                    <div class="form-group form-group-default">
                                                                        <label>Codigo Pensum</label>
                                                                        <input class="form-control" type="text" name="programa" placeholder="115">
                                                                    </div>
                                                                </div>
                                                                <div class="col-sm-12">
                                                                    <div class="form-group form-group-default">
                                                                        <p class="small">Importe el Archivo en Formato PDF</p>
                                                                        <input class="form-control-file" type="file" name="pensum">
                                                                    </div>
                                                                </div>                                                               
                                                                <div class="modal-footer no-bd">
                                                                    <input class="btn btn-primary" type="submit" value="Agregar">
                                                                    <input class="btn btn-primary" type="submit" value="Cerrar" data-dismiss="modal">
                                                                </div>
                                                            </div>
                                                        </form>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                        <!-- Fin Modal -->

                                        <!-- Tabla Pensum -->
                                        <div class="table-responsive">
                                            <table id="basic-datatables"
                                                   class="display table table-striped table-hover text-center">
                                                <thead>
                                                    <tr>
                                                        <th>Código</th>
                                                        <th>Créditos</th>
                                                        <th>Cant Materias</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </thead>
                                                <tfoot>
                                                    <tr>
                                                        <th>Código</th>
                                                        <th>Créditos</th>
                                                        <th>Cant Materias</th>
                                                        <th>Action</th>
                                                    </tr>
                                                </tfoot>
                                                <tbody>
                                                    <%
                                                        AdministrarPensum admin = new AdministrarPensum();
                                                        Usuario u = (Usuario) request.getSession().getAttribute("usuario");
                                                        List<Pensum> pensum = (List<Pensum>) request.getSession().getAttribute("listaPensum2");
                                                        if (pensum != null) {
                                                            for (Pensum p : pensum) {
                                                                if (u.getDocente().getProgramaList().get(0).getDirectorPrograma().getCodigoDocente() == p.getPrograma().getDirectorPrograma().getCodigoDocente()) {
                                                                    int materiaXcreditos[] = admin.creditosMateriasPensum(p.getPensumPK().getCodigo(), p.getPensumPK().getProgramaCodigo());
                                                    %>
                                                    <tr>
                                                        <td><%= p.getPensumPK().getProgramaCodigo() + " - " + p.getPensumPK().getCodigo()%></td>                                                        
                                                        <td><%= materiaXcreditos[1]%></td>
                                                        <td><%= materiaXcreditos[0]%></td>                                                        
                                                        <td>
                                                            <div class="form-button-action">
                                                                <a href="../../../../ControladorPensum?accion=ver&cod=<%=p.getPensumPK().getCodigo()%>" type="button" data-toggle="tooltip" title=""
                                                                        class="btn btn-link btn-dark" data-original-title="See"
                                                                        style="color: black;">
                                                                    <i class="fas fa-search"></i>
                                                                </a>
                                                                <button type="button" data-toggle="tooltip" title=""
                                                                        class="btn btn-link btn-dark"
                                                                        data-original-title="Export PDF" style="color: black;">
                                                                    <i class="fas fa-file-pdf"></i>
                                                                </button>
                                                            </div>
                                                        </td>
                                                    </tr>
                                                    <%}
                                                            }
                                                        }%>
                                                </tbody>
                                            </table>
                                        </div>
                                        <!-- Fin Tabla Pensum -->
                                    </div>
                                </div>
                            </div>
                            <!-- Fin Card Pensum -->
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
        <script src="../../../assets/js/core/jquery.3.2.1.min.js"></script>
        <script src="../../../assets/js/core/popper.min.js"></script>
        <script src="../../../assets/js/core/bootstrap.min.js"></script>
        <!-- jQuery UI -->
        <script src="../../../assets/js/plugin/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
        <script src="../../../assets/js/plugin/jquery-ui-touch-punch/jquery.ui.touch-punch.min.js"></script>
        <!-- jQuery Scrollbar -->
        <script src="../../../assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
        <!-- Datatables -->
        <script src="../../../assets/js/plugin/datatables/datatables.min.js"></script>
        <!-- Atlantis JS -->
        <script src="../../../assets/js/atlantis.min.js"></script>
        <!-- Atlantis DEMO methods, don't include it in your project! -->
        <script src="../../../assets/js/setting-demo2.js"></script>
        <!-- Filtros Tablas JS -->
        <script>
            // Basic
            $('#basic-datatables').DataTable({
                "pageLength": 5});

            // Add Row
            $('#addRowButton').click(function () {
                $('#addRowModal').modal('hide');
            });
        </script>
    </body>

</html>
