<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="MT" />
                <meta name="author" content="MT" />
                <title>Cập Nhập Trạng Thái Order</title>
                <link href="/css/styles.css" rel="stylesheet" />

                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>


                <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.7.1/jquery.min.js"></script>


            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Quản lí đơn đặt hàng</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Trang Chủ</a></li>
                                    <li class="breadcrumb-item active">Order</li>
                                </ol>
                                <div class=" mt-5">
                                    <div class="row">
                                        <div class="col-md-6 col-12 mx-auto">
                                            <h3 style="text-align: center; color: red;">Cập nhật đơn đặt hàng</h3>
                                            <hr />
                                            <form:form method="post" action="/admin/order/update"
                                                modelAttribute="newOrder">

                                                <div class="mb-3" style="display: none;">
                                                    <label class="form-label">Id:</label>
                                                    <form:input type="text" class="form-control" path="id" />
                                                </div>

                                                <div class="mb-3">
                                                    <label class="form-label">Tổng số tiền:</label>
                                                    <form:input type="email" class="form-control" path="totalPrice"
                                                        readonly="true" />
                                                </div>

                                                <div class="mb-3">
                                                    <label class="form-label">Người đặt:</label>
                                                    <form:input type="text" class="form-control" path="user.fullName"
                                                        readonly="true" />
                                                </div>
                                                <div class="mb-3">
                                                    <label class="form-label">Trang thái</label>
                                                    <form:input type="text" class="form-control" path="status" />
                                                </div>

                                                <button type="submit" class="btn btn-warning">Cập nhật</button>
                                            </form:form>
                                        </div>
                                    </div>

                                </div>
                            </div>
                        </main>
                        <jsp:include page="../layout/footer.jsp" />
                    </div>
                </div>
                <script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js"
                    crossorigin="anonymous"></script>
                <script src="/js/scripts.js"></script>

            </body>

            </html>