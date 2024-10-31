<%@page contentType="text/html" pageEncoding="UTF-8" %>
    <%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
        <%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
            <html lang="en">

            <head>
                <meta charset="UTF-8">
                <meta name="viewport" content="width=device-width, initial-scale=1.0">
                <title>Document</title>
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

                <!-- Spinner Start -->
                <div id="spinner"
                    class="show w-100 vh-100 bg-white position-fixed translate-middle top-50 start-50  d-flex align-items-center justify-content-center">
                    <div class="spinner-grow text-primary" role="status"></div>
                </div>
                <!-- Spinner End -->


                <jsp:include page="../layout/header.jsp" />


                <!-- Modal Search Start -->
                <div class="modal fade" id="searchModal" tabindex="-1" aria-labelledby="exampleModalLabel"
                    aria-hidden="true">
                    <div class="modal-dialog modal-fullscreen">
                        <div class="modal-content rounded-0">
                            <div class="modal-header">
                                <h5 class="modal-title" id="exampleModalLabel">Search by keyword</h5>
                                <button type="button" class="btn-close" data-bs-dismiss="modal"
                                    aria-label="Close"></button>
                            </div>
                            <div class="modal-body d-flex align-items-center">
                                <div class="input-group w-75 mx-auto d-flex">
                                    <input type="search" class="form-control p-3" placeholder="keywords"
                                        aria-describedby="search-icon-1">
                                    <span id="search-icon-1" class="input-group-text p-3"><i
                                            class="fa fa-search"></i></span>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Modal Search End -->



                <!-- Single Product Start -->
                <div class="container-fluid py-5 mt-5">
                    <div class="container py-5">
                        <div class="row g-4 mb-5">
                            <div>
                                <nav aria-label="breadcrumb">
                                    <ol class="breadcrumb">
                                        <li class="breadcrumb-item"><a href="/">Home</a></li>
                                        <li class="breadcrumb-item active" aria-current="page">Chi Tiết Sản Phẩm</li>
                                    </ol>
                                </nav>
                            </div>

                            <div class="col-lg-8 col-xl-9">
                                <div class="row g-4">
                                    <div class="col-lg-6">
                                        <div class="border rounded">
                                            <a href="#">
                                                <img src="/images/product/${product.image}" class="img-fluid rounded"
                                                    alt="Image">
                                            </a>
                                        </div>
                                    </div>
                                    <div class="col-lg-6">
                                        <h4 class="fw-bold mb-3" style="color: rgba(214, 55, 55, 0.493);">
                                            ${product.name}</h4>
                                        <p class="mb-4">Loại cá: ${product.factory}</p>
                                        <p class="mb-4">Chất lượng: ${product.target}</p>
                                        <p class="mb-4">Người bán: ${product.user.fullName}</p>
                                        <h5 class="fw-bold mb-3">
                                            Giá:
                                            <fmt:formatNumber type="number" value="${product.price}" /> đ
                                        </h5>
                                        <div class="d-flex mb-4">
                                            Xếp hạng:
                                            <c:forEach var="i" begin="1" end="5">
                                                <c:if test="${i <= avg}">
                                                    <i class="fa fa-star text-secondary"></i>
                                                </c:if>
                                                <c:if test="${i > avg}">
                                                    <i class="fa fa-star"></i>
                                                </c:if>
                                            </c:forEach>
                                        </div>
                                        <p class="mb-3">${product.shortDesc}</p>
                                        <form action="/addProductToCart/${product.id}" method="post">
                                            <div>
                                                <input type="hidden" name="${_csrf.parameterName}"
                                                    value="${_csrf.token}" />
                                            </div>
                                            <c:if test="${check == 0}">
                                                <button
                                                    class="mx-auto btn border border-secondary rounded-pill px-3 text-primary">
                                                    <i class="fa fa-shopping-bag me-2 text-primary"></i>
                                                    Thêm vào giỏ hàng
                                                </button>
                                            </c:if>

                                        </form>
                                    </div>
                                    <div class="col-lg-12">
                                        <nav>
                                            <div class="nav nav-tabs mb-3">
                                                <button class="nav-link active border-white border-bottom-0"
                                                    type="button" role="tab" id="nav-about-tab" data-bs-toggle="tab"
                                                    data-bs-target="#nav-about" aria-controls="nav-about"
                                                    aria-selected="true">Miêu tả chi tiết</button>
                                                <button class="nav-link border-white border-bottom-0" type="button"
                                                    role="tab" id="nav-mission-tab" data-bs-toggle="tab"
                                                    data-bs-target="#nav-mission" aria-controls="nav-mission"
                                                    aria-selected="false">Đánh giá</button>
                                            </div>
                                        </nav>
                                        <div class="tab-content mb-5">
                                            <div class="tab-pane active" id="nav-about" role="tabpanel"
                                                aria-labelledby="nav-about-tab">
                                                <p>${product.detailDesc}</p>
                                            </div>
                                            <div class="tab-pane" id="nav-mission" role="tabpanel"
                                                aria-labelledby="nav-mission-tab">
                                                <c:if test="${ empty reviews}">
                                                    <tr>
                                                        <td colspan="6">
                                                            Chưa có đánh giá.
                                                        </td>
                                                    </tr>
                                                </c:if>
                                                <c:forEach var="review" items="${reviews}">
                                                    <div class="d-flex">
                                                        <img src="/images/avatar/${review.user.avatar}"
                                                            class="img-fluid rounded-circle p-3"
                                                            style="width: 100px; height: 100px;" alt="">
                                                        <div class="">
                                                            <p class="mb-2" style="font-size: 14px;">
                                                                ${review.date}</p>
                                                            <c:if test="${review.user == user}">
                                                                <form action="/review/delete/${review.id}"
                                                                    method="post">
                                                                    <div>
                                                                        <input type="hidden"
                                                                            name="${_csrf.parameterName}"
                                                                            value="${_csrf.token}" />
                                                                    </div>
                                                                    <button class="btn btn-danger">Xóa</button>
                                                                    <input style="display: none;" name="id"
                                                                        value="${id}" />

                                                                </form>
                                                            </c:if>
                                                            <div class="d-flex justify-content-between">
                                                                <h5>${review.user.fullName}</h5>
                                                                <div class="d-flex mb-3 ">
                                                                    <c:forEach var="i" begin="1" end="5">
                                                                        <c:if test="${i <= review.star}">
                                                                            <i class="fa fa-star text-secondary"></i>
                                                                        </c:if>
                                                                        <c:if test="${i > review.star}">
                                                                            <i class="fa fa-star"></i>
                                                                        </c:if>
                                                                    </c:forEach>
                                                                </div>
                                                                <div>
                                                                    ${review.purchaseStatus}
                                                                </div>
                                                            </div>
                                                            <p>
                                                                ${review.assessment}
                                                            </p>
                                                        </div>
                                                    </div>
                                                </c:forEach>

                                            </div>
                                        </div>
                                    </div>
                                    <c:if test="${not empty pageContext.request.userPrincipal and check == 0}">
                                        <h4 class="mb-5 fw-bold">Thêm đánh giá:</h4>
                                        <div class="col-lg-12 s">
                                            <div class="d-flex justify-content-between">
                                                <div class="d-flex align-items-center" data-start="5">
                                                    <p class="mb-0 me-3">Đánh giá:</p>
                                                    <div class="d-flex align-items-center stars"
                                                        style="font-size: 12px;">
                                                        <button style="border: 0; padding: 0;"
                                                            class="fa fa-star text-secondary" data-index="1"></button>
                                                        <button style="border: 0; padding: 0;" class="fa fa-star"
                                                            data-index="2"></button>
                                                        <button style="border: 0; padding: 0;" class="fa fa-star"
                                                            data-index="3"></button>
                                                        <button style="border: 0; padding: 0;" class="fa fa-star"
                                                            data-index="4"></button>
                                                        <button style="border: 0; padding: 0;" class="fa fa-star"
                                                            data-index="5"></button>
                                                    </div>
                                                </div>

                                            </div>
                                        </div>
                                        <form action="/review/${product.id}" method="post">
                                            <div class="row g-4">
                                                <div class="col-lg-12">
                                                    <div class="border-bottom rounded">
                                                        <textarea lass="form-control border-0" cols="120" rows="8"
                                                            placeholder="Miêu tả đánh giá:" spellcheck="false"
                                                            name="assessment" type="text"></textarea>
                                                    </div>
                                                    <input type="text" id="ratingInput" name="star" value="1" />
                                                </div>
                                            </div>
                                            <button
                                                class="btn border border-secondary text-primary rounded-pill px-4 py-3"
                                                style="margin: 5px;">
                                                Lưu đánh giá</button>
                                            <input type="hidden" name="${_csrf.parameterName}" value="${_csrf.token}" />
                                        </form>

                                    </c:if>

                                </div>
                            </div>
                            <div class="col-lg-4 col-xl-3">
                                <div class="row g-4 fruite">
                                    <div class="col-lg-12">

                                        <div class="mb-4">
                                            <h4>Thể loại</h4>
                                            <ul class="list-unstyled fruite-categorie">
                                                <li>
                                                    <div class="d-flex justify-content-between fruite-name">
                                                        <a href="/products?page=1&sort=gia-nothing&factory=Utsurimono"><i
                                                                class="bi bi-cart-fill me-2"></i>Utsurimono</a>
                                                        <span>${numUtsurimono}</span>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="d-flex justify-content-between fruite-name">
                                                        <a href="/products?page=1&sort=gia-nothing&factory=Hikarimono"><i
                                                                class="bi bi-cart-fill me-2"></i>Hikarimono</a>
                                                        <span>${numHikarimono}</span>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="d-flex justify-content-between fruite-name">
                                                        <a href="/products?page=1&sort=gia-nothing&factory=Gosanke"><i
                                                                class="bi bi-cart-fill me-2"></i>GooSanKe</a>
                                                        <span>${numGooSanKe}</span>
                                                    </div>
                                                </li>
                                            </ul>
                                        </div>
                                    </div>
                                </div>
                            </div>
                        </div>
                    </div>
                </div>
                <!-- Single Product End -->

                <jsp:include page="../layout/footer.jsp" />


                <!-- Back to Top -->
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