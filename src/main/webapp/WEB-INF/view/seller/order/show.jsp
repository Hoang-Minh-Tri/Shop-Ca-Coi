<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
            <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
                <!DOCTYPE html>
                <html lang="en">

                <head>
                    <meta charset="utf-8" />
                    <meta http-equiv="X-UA-Compatible" content="IE=edge" />
                    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no" />
                    <meta name="description" content="MT" />
                    <meta name="author" content="MT" />
                    <title>Order</title>
                    <link href="/css/styles.css" rel="stylesheet" />
                    <script src="https://use.fontawesome.com/releases/v6.3.0/js/all.js"
                        crossorigin="anonymous"></script>
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
                                        <li class="breadcrumb-item active">Orders</li>
                                    </ol>
                                    <div class="mt-5">
                                        <div class="row">
                                            <div class="col-12 mx-auto">
                                                <div class="d-flex justify-content-between">
                                                    <h3>ĐƠN ĐẶT HÀNG CỦA BẠN</h3>
                                                </div>

                                                <hr />
                                                <table class=" table table-bordered table-hover">
                                                    <thead>
                                                        <tr style="text-align: center;">
                                                            <th>ID</th>
                                                            <th>Người đặt</th>
                                                            <th>Tên sản phẩm</th>
                                                            <th>Số lượng</th>
                                                            <th>Tổng số tiền</th>
                                                            <th>Thời gian</th>
                                                            <th>Trạng thái</th>
                                                            <th>Chức Năng</th>
                                                        </tr>
                                                    </thead>
                                                    <tbody>
                                                        <c:forEach var="orderDetail" items="${orderDetails}">
                                                            <c:if test="${orderDetail.status eq 'Đã giao'}">
                                                                <tr
                                                                    style="text-align: center; background-color: rgb(79, 158, 69);">
                                                                    <th>${orderDetail.id}</th>
                                                                    <td>${orderDetail.userNameBuy}</td>
                                                                    <td>${orderDetail.productOrderDetail.name}</td>
                                                                    <td>${orderDetail.quantity}</td>
                                                                    <td>
                                                                        <fmt:formatNumber type="number"
                                                                            value="${orderDetail.price * orderDetail.quantity}" />
                                                                        đ
                                                                    </td>

                                                                    <td>${orderDetail.date}</td>
                                                                    <td>${orderDetail.status}</td>
                                                                    <td>
                                                                        <c:if
                                                                            test="${orderDetail.status eq 'Chờ xử lý'}">
                                                                            <a href="/seller/order/agree/${orderDetail.id}"
                                                                                class="btn btn-warning  mx-2">Giao</a>
                                                                            <a href="/seller/order/delete/${orderDetail.id}"
                                                                                class="btn btn-danger">Xóa</a>
                                                                        </c:if>

                                                                    </td>
                                                                </tr>
                                                            </c:if>
                                                            <c:if test="${orderDetail.status ne 'Đã giao'}">
                                                                <tr style="text-align: center;">
                                                                    <th>${orderDetail.id}</th>
                                                                    <td>${orderDetail.userNameBuy}</td>
                                                                    <td>${orderDetail.productOrderDetail.name}</td>
                                                                    <td>${orderDetail.quantity}</td>
                                                                    <td>
                                                                        <fmt:formatNumber type="number"
                                                                            value="${orderDetail.price * orderDetail.quantity}" />
                                                                        đ
                                                                    </td>

                                                                    <td>${orderDetail.date}</td>
                                                                    <td>${orderDetail.status}</td>
                                                                    <td>
                                                                        <c:if
                                                                            test="${orderDetail.status eq 'Chờ xử lý'}">
                                                                            <a href="/seller/order/agree/${orderDetail.id}"
                                                                                class="btn btn-warning  mx-2">Giao</a>
                                                                            <a href="/seller/order/delete/${orderDetail.id}"
                                                                                class="btn btn-danger">Xóa</a>
                                                                        </c:if>

                                                                    </td>
                                                                </tr>
                                                            </c:if>


                                                        </c:forEach>

                                                    </tbody>
                                                </table>
                                                <c:if test="${sumPage > 0}">
                                                    <nav aria-label="Page navigation example">
                                                        <ul class="pagination justify-content-center">
                                                            <li class="page-item">
                                                                <a class="${(nowPage) eq 1 ? 'disabled page-link' : 'page-link'}"
                                                                    href="/seller/order?page=${nowPage - 1}"
                                                                    aria-label="Previous">
                                                                    <span aria-hidden="true">&laquo;</span>
                                                                </a>
                                                            </li>
                                                            <c:forEach begin="0" end="${sumPage - 1}" varStatus="loop">
                                                                <li class="page-item ">
                                                                    <a class="${(loop.index + 1) eq nowPage ? 'active page-link' : 'page-link'}"
                                                                        href="/seller/order?page=${loop.index+1}">
                                                                        ${loop.index+1}
                                                                    </a>
                                                                </li>
                                                            </c:forEach>
                                                            <li class="page-item">
                                                                <a class="${nowPage eq sumPage ? 'disabled page-link' : 'page-link'}"
                                                                    href="/seller/order?page=${nowPage + 1}"
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
                    <script src="js/scripts.js"></script>
                </body>

                </html>