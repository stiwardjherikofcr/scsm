<%-- 
    Document   : signup
    Created on : 15-jun-2021, 23:42:29
    Author     : Stiward
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html lang="en">

    <head>
        <meta charset="UTF-8">
        <meta http-equiv="X-UA-Compatible" content="IE=edge" />
        <title>CSM Software</title>
        <meta content='width=device-width, initial-scale=1.0, shrink-to-fit=no' name='viewport' />
        <link rel="icon" href="../../assets/img/icon.ico" type="image/x-icon" />
        <!-- Custom CSS -->
        <link rel="stylesheet" href="../../assets/css/singup.css">

        <!-- CSS Files -->
        <link rel="stylesheet" href="../../assets/css/bootstrap.min.css">
        <link rel="stylesheet" href="../../assets/css/checkradio.css">
        <link rel="stylesheet" href="../../assets/css/microcurriculo.css">
        <!-- CSS Just for demo purpose, don't include it in your project -->
        <link rel="stylesheet" href="../../assets/css/demo.css">
        <!-- CSS Custom -->
        <link rel="stylesheet" href="../../assets/css/microcurriculo.css">
    </head>

    <body>
        <div class="cuerpologin">
            <div class="row vh-20">
                <div class="row w-100 d-flex justify-content-center">
                    <h2 class="t-white mt-5">Registro</h2>
                </div>
                <div class="row w-100 ">
                    <div class="offset-md-10 col-2">
                        <img class="logoUFPS" src="../../assets/img/Logo-nuevo-vertical.png" alt="logo de la UFPS">
                    </div>

                </div>
            </div>
            <div class=" row d-flex justify-content-center">
                <form class="w-75">
                    <div class=" fbr plogin  gbcolor">
                        <div class="mb-3 w-100 d-flex justify-content-center align-items-center">
                            <img class="account" src="../../assets/img/profile.svg" alt="">
                        </div>
                        <div class="mb-3 row w-100">
                            <h3 class="col-2">Nombres</h3>
                            <input type="text" class="col form-control  singin" placeholder="Nombres" id="">
                            <h3 class="col-2">Apellidos</h3>
                            <input type="text" class="col form-control  singin" placeholder="Apellidos" id="">
                        </div>
                        <div class="mb-3 row w-100">
                            <h3 class="col-2">Codigo</h3>
                            <input type="text" class="col form-control  singin" placeholder="Codigo" id="">
                            <h3 class="col-2">Correo</h3>
                            <input type="text" class="col form-control  singin" placeholder="Correo" id="">
                        </div>
                        <div class="mb-3 row w-100">
                            <h3 class="col-2">Contraseña</h3>
                            <input type="text" class="col form-control   singin" placeholder="Contraseña" id="">
                            <h3 class="col-2">Confirmar Contraseña</h3>
                            <input type="text" class="col form-control  singin" placeholder="Confirmar Contraseña"
                                   id="">
                        </div>
                        <div class="mb-3 row w-100">
                            <h3 class="col-2">Facultad</h3>
                            <select class=" col form-control" id="formGroupDefaultSelect">
                                <option selected>En curso</option>
                                <option>Aprovado</option>
                                <option>Rechazado</option>
                            </select>
                            <h3 class="col-2">Departamento</h3>
                            <select class=" col form-control" id="formGroupDefaultSelect">
                                <option selected>En curso</option>
                                <option>Aprovado</option>
                                <option>Rechazado</option>
                            </select>
                        </div>
                        <div class="mb-3 row w-100">
                            <h3 class="col-2">Facultad</h3>
                            <select class=" col form-control" id="formGroupDefaultSelect">
                                <option selected>En curso</option>
                                <option>Aprovado</option>
                                <option>Rechazado</option>
                            </select>

                        </div>
                        <!--botones-->
                        <div class="w-100 d-flex justify-content-center ">
                            <div class="w-25">
                                <a class="btn btn-danger mb-2 w-100 brlogin mr-2" href="../sign-in/singin.jsp">Iniciar
                                    Sesion</a>
                            </div>
                            <div class="w-25">
                                <a class="btn btn-danger mb-2 w-100 brlogin ml-2"
                                   href="../sign-up/signup.jsp">Registrarse</a>
                            </div>
                        </div>
                        <!--botones-->
                    </div>
                </form>
            </div>

        </div>
        <!--   Core JS Files   -->
        <script src="../../assets/js/core/jquery.3.2.1.min.js"></script>
        <script src="../../assets/js/core/popper.min.js"></script>
        <script src="../../assets/js/core/bootstrap.min.js"></script>
        <!-- jQuery UI -->
        <script src="../../assets/js/plugin/jquery-ui-1.12.1.custom/jquery-ui.min.js"></script>
        <script src="../../assets/js/plugin/jquery-ui-touch-punch/jquery.ui.touch-punch.min.js"></script>
        <!-- jQuery Scrollbar -->
        <script src="../../assets/js/plugin/jquery-scrollbar/jquery.scrollbar.min.js"></script>
        <!-- Atlantis JS -->
        <script src="../../assets/js/atlantis.min.js"></script>
        <!-- Atlantis DEMO methods, don't include it in your project! -->
        <script src="../../assets/js/setting-demo2.js"></script>
        <script>
            function href() {
                var input_radio_director = document.getElementById("opt1").checked;
                var input_radio_docente = document.getElementById("opt2").checked;
                if (input_radio_director) {
                    window.location = "../director/dashboard/dashboard.jsp";
                } else if (input_radio_docente) {
                    window.location = "../docente/dashboard/dashboard.jsp";
                } else {
                    window.location = "#";
                }
            }
        </script>
    </body>

</html>
