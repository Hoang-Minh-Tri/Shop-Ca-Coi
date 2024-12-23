<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <!DOCTYPE html>
            <html lang="en">

            <head>
                <meta charset="utf-8" />
                <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                <meta name="description" content="MT" />
                <meta name="author" content="MT" />
                <title>Dashboard</title>
                <link href="/css/styles.css" rel="stylesheet" />
                <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js" crossorigin="anonymous"></script>
            </head>

            <body class="sb-nav-fixed">
                <jsp:include page="../layout/header.jsp" />
                <div id="layoutSidenav">
                    <jsp:include page="../layout/sidebar.jsp" />
                    <div id="layoutSidenav_content">
                        <main>
                            <div class="container-fluid px-4">
                                <h1 class="mt-4">Quản lí yêu cầu từ người dùng</h1>
                                <ol class="breadcrumb mb-4">
                                    <li class="breadcrumb-item"><a href="/admin">Trang chủ</a></li>
                                    <li class="breadcrumb-item active">Yêu cầu/li>
                                </ol>
                                <div class="mt-5">
                                    <div class="row">
                                        <div class="col-12 mx-auto">
                                            <div class="d-flex justify-content-between">
                                                <h3>DANH SÁCH CHỜ XỬ LÝ</h3>
                                            </div>

                                            <hr />
                                            <table class=" table table-bordered table-hover"
                                                style="text-align: center;">
                                                <thead>
                                                    <tr>
                                                        <th>ID</th>
                                                        <th>Tên sản phẩm</th>
                                                        <th>Giá sản phẩm</th>
                                                        <th>Tên chủng loại</th>
                                                        <th>Số lượng</th>
                                                        <th>Người bán</th>
                                                        <th>Vai trò</th>
                                                    </tr>
                                                </thead>
                                                <tbody>
                                                    <c:forEach var="product" items="${products}">
                                                        <tr>
                                                            <th>${product.id}</th>
                                                            <td>${product.name}</td>
                                                            <td>
                                                                <fmt:formatNumber type="number"
                                                                    value="${product.price}" />đ
                                                            </td>
                                                            <td>${product.factory}</td>
                                                            <td>${product.quantity}</td>
                                                            <td>${product.user.fullName}</td>
                                                            <td>
                                                                <a href="/admin/request/${product.id}"
                                                                    class="btn btn-success">Xem chi tiết</a>
                                                                <a href="/admin/request/agree/${product.id}"
                                                                    class="btn btn-warning  mx-2">Đồng ý</a>
                                                                <a href="/admin/request/delete/${product.id}"
                                                                    class="btn btn-danger">Xóa</a>
                                                            </td>
                                                        </tr>

                                                    </c:forEach>

                                                </tbody>
                                            </table>
                                            <c:if test="${sumPage > 0}">
                                                <nav aria-label="Page navigation example">
                                                    <ul class="pagination justify-content-center">
                                                        <li class="page-item">
                                                            <a class="${(nowPage) eq 1 ? 'disabled page-link' : 'page-link'}"
                                                                href="/admin/request?page=${nowPage - 1}"
                                                                aria-label="Previous">
                                                                <span aria-hidden="true">&laquo;</span>
                                                            </a>
                                                        </li>
                                                        <c:forEach begin="0" end="${sumPage - 1}" varStatus="loop">
                                                            <li class="page-item ">
                                                                <a class="${(loop.index + 1) eq nowPage ? 'active page-link' : 'page-link'}"
                                                                    href="/admin/request?page=${loop.index+1}">
                                                                    ${loop.index+1}
                                                                </a>
                                                            </li>
                                                        </c:forEach>
                                                        <li class="page-item">
                                                            <a class="${nowPage eq sumPage ? 'disabled page-link' : 'page-link'}"
                                                                href="/admin/request?page=${nowPage + 1}"
                                                                aria-label="Next">
                                                                <span aria-hidden="true">&raquo;</span>
                                                            </a>
                                                        </li>
                                                    </ul>
                                                </nav>
                                            </c:if>

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