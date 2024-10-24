<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <div id="layoutSidenav_nav">
            <nav class="sb-sidenav accordion sb-sidenav-dark" id="sidenavAccordion">
                <div class="sb-sidenav-menu">
                    <div class="nav">
                        <div class="sb-sidenav-menu-heading">Chức năng</div>
                        <a class="nav-link" href="/admin">
                            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                            Trang chủ
                        </a>

                        <a class="nav-link" href="/admin/user?page=1">
                            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                            Người dùng
                        </a>

                        <a class="nav-link" href="/admin/product?page=1">
                            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                            Sản phẩm
                        </a>

                        <a class="nav-link" href="/admin/order?page=1">
                            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                            Đơn đặt hàng
                        </a>
                        <a class="nav-link" href="/admin/request?page=1">
                            <div class="sb-nav-link-icon"><i class="fas fa-tachometer-alt"></i></div>
                            Yêu cầu
                        </a>
                    </div>
                </div>
                <div class="sb-sidenav-footer">
                    <div class="small">Đăng nhập bởi:</div>
                    Minh Tri
                </div>
            </nav>
        </div>