<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="" />
                <meta name="author" content="" />
                <title>Tạo lại mật khẩu</title>
                <link href="css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body class="bg-primary">
                <div id="layoutAuthentication">
                    <div id="layoutAuthentication_content">
                        <main>
                            <div class="container">
                                <div class="row justify-content-center">
                                    <div class="col-lg-5">
                                        <div class="card shadow-lg border-0 rounded-lg mt-5">
                                            <div class="card-header">
                                                <h3 class="text-center font-weight-light my-4">Khôi phục mật khẩu</h3>
                                            </div>
                                            <div class="card-body">
                                                <form:form method="post" action="/forgot" modelAttribute="forgot">
                                                    <div class="form-floating mb-3">
                                                        <c:set var="EmailError">
                                                            <form:errors path="email" cssClass="invalid-feedback" />
                                                        </c:set>
                                                        <form:input
                                                            class="form-control ${not empty EmailError ? 'is-invalid' : ''}"
                                                            type="email" placeholder="name@example.com" path="email" />
                                                        ${EmailError}
                                                        <label>Email</label>
                                                    </div>
                                                    <div class="form-floating mb-3">
                                                        <c:set var="fullNameError">
                                                            <form:errors path="fullName" cssClass="invalid-feedback" />
                                                        </c:set>
                                                        <form:input
                                                            class="form-control ${not empty fullNameError ? 'is-invalid' : ''}"
                                                            type="text" path="fullName" />
                                                        ${fullNameError}
                                                        <label>Tên đầy đủ</label>
                                                    </div>
                                                    <div class="form-floating mb-3">
                                                        <c:set var="passwordError">
                                                            <form:errors path="password" cssClass="invalid-feedback" />
                                                        </c:set>
                                                        <form:input
                                                            class="form-control ${not empty passwordError ? 'is-invalid' : ''}"
                                                            type="text" path="password" />
                                                        ${passwordError}
                                                        <label>Mật khẩu</label>
                                                    </div>
                                                    <div
                                                        class="d-flex align-items-center justify-content-between mt-4 mb-0">
                                                        <a class="small" href="/login">Trở lại đăng nhập</a>
                                                        <button class="btn btn-primary">Cập nhật mật
                                                            khẩu</button>
                                                    </div>
                                                </form:form>
                                            </div>
                                            <div class="card-footer text-center py-3">
                                                <div class="small"><a href="/register">Đăng kí</a></div>
                                            </div>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </main>
                    </div>
                    <div id="layoutAuthentication_footer">
                        <footer class="py-4 bg-light mt-auto">
                            <div class="container-fluid px-4">
                                <div class="d-flex align-items-center justify-content-between small">
                                    <div class="text-muted">Copyright &copy; Your Website 2023</div>
                                    <div>
                                        <a href="#">Privacy Policy</a>
                                        &middot;
                                        <a href="#">Terms &amp; Conditions</a>
                                    </div>
                                </div>
                            </div>
                        </footer>
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="js/scripts.js"></script>
            </body>

            </html>