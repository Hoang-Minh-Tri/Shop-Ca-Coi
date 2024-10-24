<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="Hỏi Dân IT - Dự án laptopshop" />
                <meta name="author" content="Hỏi Dân IT" />
                <title>Create Product</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>
                <script>
                    $(document).ready(() => {
                        const avatarFile = $("#avatarFile");
                        avatarFile.change(function (e) {
                            const imgURL = URL.createObjectURL(e.target.files[0]);
                            $("#avatarPreview").attr("src", imgURL);
                            $("#avatarPreview").css({ "display": "block" });
                        });
                    });
                </script>
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
                <link rel="preconnect" href="https://fonts.googleapis.com">
                <link rel="preconnect" href="https://fonts.gstatic.com" crossorigin>
                <link
                    href="https://fonts.googleapis.com/css2?family=Open+Sans:wght@400;600&family=Raleway:wght@600;800&display=swap"
                    rel="stylesheet">

                <!-- Icon Font Stylesheet -->
                <link rel="stylesheet" href="https://use.fontawesome.com/releases/v5.15.4/css/all.css" />
                <link href="https://cdn.jsdelivr.net/npm/bootstrap-icons@1.4.1/font/bootstrap-icons.css"
                    rel="stylesheet">

                <!-- Libraries Stylesheet -->
                <link href="/client/lib/lightbox/css/lightbox.min.css" rel="stylesheet">
                <link href="/client/lib/owlcarousel/assets/owl.carousel.min.css" rel="stylesheet">


                <!-- Customized Bootstrap Stylesheet -->
                <link href="/client/css/bootstrap.min.css" rel="stylesheet">

                <!-- Template Stylesheet -->
                <link href="/client/css/style.css" rel="stylesheet">
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav justify-content-center">
                    <main>
                        <div class="container-fluid px-4">
                            <h1 style="margin-top: 100px;">Products</h1>
                            <div class="mt-auto">
                                <div class="row">
                                    <div class="col-md-6 col-12 mx-auto">
                                        <h3>Thêm loại cá mới</h3>
                                        <hr />
                                        <form:form method="post" action="/myProduct/create" class="row"
                                            enctype="multipart/form-data" modelAttribute="newProduct">
                                            <div class="mb-3 col-12 col-md-6">
                                                <c:set var="NameHasErrors">
                                                    <form:errors path="name" cssClass="invalid-feedback" />
                                                </c:set>
                                                <label class="form-label">Tên cá:</label>
                                                <form:input type="text"
                                                    class="form-control ${not empty NameHasErrors ? 'is-invalid' : ''}"
                                                    path="name" />
                                                ${NameHasErrors}
                                            </div>
                                            <div class="mb-3 col-12 col-md-6">
                                                <c:set var="PriceHasErrors">
                                                    <form:errors path="price" cssClass="invalid-feedback" />
                                                </c:set>
                                                <label class="form-label">Giá:</label>
                                                <form:input type="number"
                                                    class="form-control ${not empty PriceHasErrors ? 'is-invalid' : ''}"
                                                    path="price" />
                                                ${PriceHasErrors}
                                            </div>
                                            <div class="mb-3 col-12">
                                                <label class="form-label">Miêu tả chi tiết:</label>
                                                <form:textarea type="text" class="form-control" path="detailDesc" />
                                            </div>
                                            <div class="mb-3 col-12 col-md-6">
                                                <label class="form-label">Miêu tả ngắn:</label>
                                                <form:input type="text" class="form-control" path="shortDesc" />
                                            </div>
                                            <div class="mb-3 col-12 col-md-6">
                                                <c:set var="QuantityHasErrors">
                                                    <form:errors path="quantity" cssClass="invalid-feedback" />
                                                </c:set>
                                                <label class="form-label">Số lượng:</label>
                                                <form:input type="number"
                                                    class="form-control ${not empty QuantityHasErrors ? 'is-invalid' : ''}"
                                                    path="quantity" />
                                                ${QuantityHasErrors}
                                            </div>

                                            <div class="mb-3 col-12 col-md-6">
                                                <label class="form-label">Dòng cá:</label>
                                                <form:select class="form-select" path="factory">
                                                    <form:option value="APPLE">Gosanke</form:option>
                                                    <form:option value="ASUS">Utsurimono</form:option>
                                                    <form:option value="LENOVO">Hikarimono</form:option>
                                                </form:select>
                                            </div>
                                            <div class="mb-3 col-12 col-md-6">
                                                <label class="form-label">Thể loại:</label>
                                                <form:select class="form-select" path="target">
                                                    <form:option value="Loại 1">Loại 1</form:option>
                                                    <form:option value="Loại 2">Loại 2
                                                    </form:option>
                                                    <form:option value="Loại 3">Loại 3
                                                    </form:option>
                                                </form:select>
                                            </div>
                                            <div class="mb-3 col-12 col-md-6">
                                                <label for="avatarFile" class="form-label">Hình ảnh:</label>
                                                <input class="form-control" type="file" id="avatarFile"
                                                    accept=".png, .jpg, .jpeg" name="MinhTriFile" />
                                            </div>
                                            <div class="col-12 mb-3">
                                                <img style="max-height: 250px; display: none;" alt="avatar preview"
                                                    id="avatarPreview" />
                                            </div>
                                            <div class="col-12 mb-5">
                                                <button type="submit" class="btn btn-primary">Tạo mới</button>
                                            </div>
                                        </form:form>

                                    </div>

                                </div>
                            </div>
                        </div>
                    </main>
                </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <!-- JavaScript Libraries -->
                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.6.4/jquery.min.js"></script>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.0/dist/js/bootstrap.bundle.min.js"></script>
                <script src="/client/lib/easing/easing.min.js"></script>
                <script src="/client/lib/waypoints/waypoints.min.js"></script>
                <script src="/client/lib/lightbox/js/lightbox.min.js"></script>
                <script src="/client/lib/owlcarousel/owl.carousel.min.js"></script>

                <!-- Template Javascript -->
                <script src="/client/js/main.js"></script>

            </body>

            </html>