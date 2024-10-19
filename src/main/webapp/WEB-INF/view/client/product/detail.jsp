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
                                        <h4 class="fw-bold mb-3">${product.name}</h4>
                                        <p class="mb-4">${product.factory}</p>
                                        <h5 class="fw-bold mb-3">
                                            <fmt:formatNumber type="number" value="${product.price}" /> đ
                                        </h5>
                                        <div class="d-flex mb-4">
                                            <i class="fa fa-star text-secondary"></i>
                                            <i class="fa fa-star text-secondary"></i>
                                            <i class="fa fa-star text-secondary"></i>
                                            <i class="fa fa-star text-secondary"></i>
                                            <i class="fa fa-star"></i>
                                        </div>
                                        <p class="mb-3">${product.shortDesc}</p>
                                        <div class="input-group quantity mb-5" style="width: 100px;">

                                        </div>
                                        <form action="/addProductToCart/${product.id}" method="post">
                                            <div>
                                                <input type="hidden" name="${_csrf.parameterName}"
                                                    value="${_csrf.token}" />
                                            </div>
                                            <button
                                                class="mx-auto btn border border-secondary rounded-pill px-3 text-primary">
                                                <i class="fa fa-shopping-bag me-2 text-primary"></i>
                                                Thêm vào giỏ hàng
                                            </button>
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
                                                <div class="d-flex">
                                                    <img src="/client/img/avatar.jpg"
                                                        class="img-fluid rounded-circle p-3"
                                                        style="width: 100px; height: 100px;" alt="">
                                                    <div class="">
                                                        <p class="mb-2" style="font-size: 14px;">April 12, 2024</p>
                                                        <div class="d-flex justify-content-between">
                                                            <h5>Minh Trí</h5>
                                                            <div class="d-flex mb-3">
                                                                <i class="fa fa-star text-secondary"></i>
                                                                <i class="fa fa-star text-secondary"></i>
                                                                <i class="fa fa-star text-secondary"></i>
                                                                <i class="fa fa-star text-secondary"></i>
                                                                <i class="fa fa-star"></i>
                                                            </div>
                                                        </div>
                                                        <p>Đây là một loại cá rất đẹp, sống rất tốt, thích nghi với môi
                                                            trường nhanh, phù hợp, giá cả hợp lý.
                                                        </p>
                                                    </div>
                                                </div>
                                                <div class="d-flex">
                                                    <img src="/client/img/avatar.jpg"
                                                        class="img-fluid rounded-circle p-3"
                                                        style="width: 100px; height: 100px;" alt="">
                                                    <div class="">
                                                        <p class="mb-2" style="font-size: 14px;">April 12, 2024</p>
                                                        <div class="d-flex justify-content-between">
                                                            <h5>Thanh Tâm</h5>
                                                            <div class="d-flex mb-3">
                                                                <i class="fa fa-star text-secondary"></i>
                                                                <i class="fa fa-star text-secondary"></i>
                                                                <i class="fa fa-star text-secondary"></i>
                                                                <i class="fa fa-star"></i>
                                                                <i class="fa fa-star"></i>
                                                            </div>
                                                        </div>
                                                        <p class="text-dark">
                                                            Sản phẩm cá Koi này có màu sắc tươi sáng và đa dạng, mang
                                                            lại vẻ đẹp thẩm mỹ cao cho hồ cá. Chất lượng cá tốt, khỏe
                                                            mạnh và có sức sống lâu dài. Đặc biệt, cá Koi được chọn lọc
                                                            kỹ càng, kích thước đều đặn và không có dấu hiệu bệnh tật.
                                                            Tuy nhiên, giá thành hơi cao so với thị trường, nhưng điều
                                                            này phản ánh đúng chất lượng của sản phẩm. Nhìn chung, đây
                                                            là một lựa chọn tuyệt vời cho những ai yêu thích cá cảnh và
                                                            muốn trang trí không gian sống của mình.
                                                        </p>
                                                    </div>
                                                </div>
                                            </div>
                                        </div>
                                    </div>
                                    <form action="#">
                                        <h4 class="mb-5 fw-bold">Đánh giá của bạn</h4>
                                        <div class="row g-4">
                                            <div class="col-lg-6">
                                                <div class="border-bottom rounded">
                                                    <input type="text" class="form-control border-0 me-4"
                                                        placeholder="Tên:">
                                                </div>
                                            </div>
                                            <div class="col-lg-6">
                                                <div class="border-bottom rounded">
                                                    <input type="email" class="form-control border-0"
                                                        placeholder="Gmail:">
                                                </div>
                                            </div>
                                            <div class="col-lg-12">
                                                <div class="border-bottom rounded my-4">
                                                    <textarea name="" id="" class="form-control border-0" cols="30"
                                                        rows="8" placeholder="Miêu tả đánh giá:"
                                                        spellcheck="false"></textarea>
                                                </div>
                                            </div>
                                            <div class="col-lg-12">
                                                <div class="d-flex justify-content-between py-3 mb-5">
                                                    <div class="d-flex align-items-center">
                                                        <p class="mb-0 me-3">Please rate:</p>
                                                        <div class="d-flex align-items-center" style="font-size: 12px;">
                                                            <i class="fa fa-star text-muted"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                            <i class="fa fa-star"></i>
                                                        </div>
                                                    </div>
                                                    <a href="#"
                                                        class="btn border border-secondary text-primary rounded-pill px-4 py-3">
                                                        Lưu đánh giá</a>
                                                </div>
                                            </div>
                                        </div>
                                    </form>
                                </div>
                            </div>
                            <div class="col-lg-4 col-xl-3">
                                <div class="row g-4 fruite">
                                    <div class="col-lg-12">
                                        <div class="input-group w-100 mx-auto d-flex mb-4">
                                            <input type="search" class="form-control p-3" placeholder="keywords"
                                                aria-describedby="search-icon-1">
                                            <span id="search-icon-1" class="input-group-text p-3"><i
                                                    class="fa fa-search"></i></span>
                                        </div>
                                        <div class="mb-4">
                                            <h4>Categories</h4>
                                            <ul class="list-unstyled fruite-categorie">

                                                <li>
                                                    <div class="d-flex justify-content-between fruite-name">
                                                        <a href="#"><i class="fas fa-apple-alt me-2"></i>Utsurimono</a>
                                                        <span>(5)</span>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="d-flex justify-content-between fruite-name">
                                                        <a href="#"><i class="fas fa-apple-alt me-2"></i>Hikarimono</a>
                                                        <span>(2)</span>
                                                    </div>
                                                </li>
                                                <li>
                                                    <div class="d-flex justify-content-between fruite-name">
                                                        <a href="#"><i class="fas fa-apple-alt me-2"></i>GooSanKe</a>
                                                        <span>(8)</span>
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