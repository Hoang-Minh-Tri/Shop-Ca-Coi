<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="utf-8">
                    <title> Sản phẩm của tôi</title>
                    <meta content="width=device-width, initial-scale=1.0" name="viewport">
                    <meta content="" name="keywords">
                    <meta content="" name="description">

                    <!-- Google Web Fonts -->
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

                <body>

                    <!-- Spinner-->
                    <div id="spinner"
                        class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
                        <div class="spinner-grow text-primary" role="status"></div>
                    </div>
                    <!-- Spinner-->

                    <jsp:include page="../layout/header.jsp" />

                    <!-- Cart-->
                    <div class="container-fluid py-5">
                        <div class="container py-5">
                            <div class="mb-3">
                                <nav aria-label="breadcrumb">
                                    <ol class="breadcrumb">
                                        <li class="breadcrumb-item"><a href="/">Home</a></li>
                                        <li class="breadcrumb-item active" aria-current="page">Chi Tiết Giỏ Hàng</li>
                                    </ol>
                                    <a href="/myProduct/create" class="btn btn-primary" style="align-items: left">Tạo
                                        sản phẩm
                                        mới</a>
                                </nav>
                            </div>
                            <div class="table-responsive">
                                <table class="table">
                                    <thead>
                                        <tr>
                                            <th scope="col">Sản phẩm</th>
                                            <th scope="col">Tên</th>
                                            <th scope="col">Giá cả</th>
                                            <th scope="col">Số lượng</th>
                                            <th scope="col">Tình trạng</th>
                                            <th scope="col">Xóa</th>
                                            <th scope="col">Cập nhật</th>
                                        </tr>
                                    </thead>
                                    <tbody>
                                        <c:if test="${ empty products}">
                                            <tr>
                                                <td colspan="6">
                                                    Không có sản phẩm
                                                </td>
                                            </tr>
                                        </c:if>
                                        <c:forEach var="product" items="${products}" varStatus="status">
                                            <tr>
                                                <th scope="row">
                                                    <div class="d-flex align-items-center">
                                                        <img src="/images/product/${product.image}"
                                                            class="img-fluid me-5 rounded-circle"
                                                            style="width: 80px; height: 80px;" alt="">
                                                    </div>
                                                </th>
                                                <td>
                                                    <p class="mb-0 mt-4">
                                                        <c:if test="${product.status== 'Đã duyệt'}">
                                                            <a href="/product/${product.id}" target="_blank">
                                                                ${product.name}
                                                            </a>
                                                        </c:if>
                                                        <c:if test="${not (product.status == 'Đã duyệt')}">
                                                            <p>
                                                                ${product.name}
                                                            </p>
                                                        </c:if>
                                                    </p>
                                                </td>
                                                <td>
                                                    <p class="mb-0 mt-4">
                                                        <fmt:formatNumber type="number" value="${product.price}" /> đ
                                                    </p>
                                                </td>
                                                <td>
                                                    <c:if test="${product.status eq 'Đã duyệt'}">
                                                        <form action="/myProduct/preUpdate/${product.id}" method="post"
                                                            modelAttribute="product">
                                                            <div class="input-group quantity mt-4"
                                                                style="width: 100px;">
                                                                <div class="input-group-btn">
                                                                    <button
                                                                        class="btn btn-sm btn-minus rounded-circle bg-light border">
                                                                        <i class="fa fa-minus"></i>
                                                                    </button>
                                                                </div>
                                                                <input type="text" name="quantity"
                                                                    class="form-control form-control-sm text-center border-0"
                                                                    value="${product.quantity}"
                                                                    data-cart-detail-id="${product.id}">
                                                                <div class="input-group-btn">
                                                                    <button
                                                                        class="btn btn-sm btn-plus rounded-circle bg-light border">
                                                                        <i class="fa fa-plus"></i>
                                                                    </button>
                                                                </div>
                                                            </div>
                                                            <input type="hidden" name="${_csrf.parameterName}"
                                                                value="${_csrf.token}" />
                                                        </form>
                                                    </c:if>
                                                    <c:if test="${product.status eq 'Chờ xử lý'}">
                                                        <div class="input-group quantity mt-4" style="width: 100px;">
                                                            <input type="text"
                                                                class="form-control form-control-sm text-center border-0"
                                                                value="${product.quantity}">
                                                        </div>
                                                    </c:if>
                                                </td>
                                                <td>
                                                    <p class="mb-0 mt-4">
                                                        ${product.status}
                                                    </p>
                                                </td>
                                                <td>
                                                    <form method="post" action="/myProduct/delete/${product.id}">
                                                        <input type="hidden" name="${_csrf.parameterName}"
                                                            value="${_csrf.token}" />
                                                        <button class="btn btn-md rounded-circle bg-light border mt-4">
                                                            <i class="fa fa-times text-danger"></i>
                                                        </button>
                                                    </form>
                                                </td>
                                                <td>
                                                    <form method="get" action="/myProduct/update/${product.id}">
                                                        <input type="hidden" name="${_csrf.parameterName}"
                                                            value="${_csrf.token}" />
                                                        <button class="btn btn-md rounded-circle bg-light border mt-4">
                                                            <i class="fas fa-wrench text-warning"></i>
                                                        </button>
                                                    </form>
                                                </td>
                                            </tr>
                                        </c:forEach>

                                    </tbody>
                                </table>
                                <c:if test="${sumPage > 0}">
                                    <div class="pagination d-flex justify-content-center mt-5">
                                        <li class="page-item ${nowPage eq 1 ? 'disabled' : ''}">
                                            <a class="page-link" href="/myProduct?page=${nowPage - 1}"
                                                aria-label="Previous">
                                                <span aria-hidden="true">&laquo;</span>
                                            </a>
                                        </li>
                                        <c:forEach begin="0" end="${sumPage- 1}" varStatus="loop">
                                            <li class="page-item">
                                                <a class="${(loop.index + 1) eq nowPage ? 'active page-link' : 'page-link'}"
                                                    href="/myProduct?page=${loop.index + 1}">
                                                    ${loop.index + 1}
                                                </a>
                                            </li>
                                        </c:forEach>
                                        <li class="page-item ${sumPage eq nowPage ? 'disabled' : ''}">
                                            <a class="page-link" href="/myProduct?page=${nowPage + 1}"
                                                aria-label="Next">
                                                <span aria-hidden="true">&raquo;</span>
                                            </a>
                                        </li>

                                    </div>
                                </c:if>
                            </div>
                        </div>
                    </div>
                    <!-- Cart-->


                    <jsp:include page="../layout/footer.jsp" />


                    <!-- Trở về Top-->
                    <a href="#" class="btn btn-primary border-3 border-primary rounded-circle back-to-top"><i
                            class="fa fa-arrow-up"></i></a>


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