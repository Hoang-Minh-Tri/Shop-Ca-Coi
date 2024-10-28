package vn.MinhTri.ShopFizz.controller.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import vn.MinhTri.ShopFizz.domain.Cart;
import vn.MinhTri.ShopFizz.domain.CartDetail;
import vn.MinhTri.ShopFizz.domain.Order;
import vn.MinhTri.ShopFizz.domain.Order_;
import vn.MinhTri.ShopFizz.domain.Product;
import vn.MinhTri.ShopFizz.domain.Product_;
import vn.MinhTri.ShopFizz.domain.Review;
import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.domain.dto.ProductCrieateDTO;
import vn.MinhTri.ShopFizz.services.OrderSevice;
import vn.MinhTri.ShopFizz.services.ProductService;
import vn.MinhTri.ShopFizz.services.ReviewService;
import vn.MinhTri.ShopFizz.services.UploadService;
import vn.MinhTri.ShopFizz.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class ItemController {
    private final ProductService productService;
    private final UploadService uploadService;
    private final UserService userService;
    private final OrderSevice orderSevice;
    private final ReviewService reviewService;

    public ItemController(ProductService productService, UploadService uploadService, UserService userService,
            OrderSevice orderSevice, ReviewService reviewService) {
        this.productService = productService;
        this.uploadService = uploadService;
        this.userService = userService;
        this.orderSevice = orderSevice;
        this.reviewService = reviewService;
    }

    @GetMapping("/products")
    public String GetAllProductPage(Model model, ProductCrieateDTO productCrieateDTO,
            HttpServletRequest httpServletRequest) {
        int NumPage = 1;
        try {
            if (productCrieateDTO.getPage().isPresent()) {
                NumPage = Integer.parseInt(productCrieateDTO.getPage().get());
            } else {
                // Numpage = 1;
            }
        } catch (Exception e) {
            // Numpage = 1;
        }
        Pageable pageable = PageRequest.of(NumPage - 1, 6);
        if (productCrieateDTO.getSort() != null && productCrieateDTO.getSort().isPresent()) {
            String sortPrice = productCrieateDTO.getSort().get();
            if (sortPrice.equals("gia-tang-dan")) {
                pageable = PageRequest.of(NumPage - 1, 6, Sort.by(Product_.PRICE).ascending());
            } else if (sortPrice.equals("gia-giam-dan")) {
                pageable = PageRequest.of(NumPage - 1, 6, Sort.by(Product_.PRICE).descending());
            }
        }
        Page<Product> pageProduct = this.productService.GetAllProductSpec(pageable, productCrieateDTO);
        List<Product> products = pageProduct.getContent() != null ? pageProduct.getContent() : new ArrayList<Product>();
        String qs = httpServletRequest.getQueryString();
        if (qs != null && !qs.isBlank()) {
            qs = qs.replace("page=" + NumPage, "");
        }
        model.addAttribute("products", products);
        model.addAttribute("sumPage", pageProduct.getTotalPages());
        model.addAttribute("nowPage", NumPage);
        model.addAttribute("queryString", qs);
        return "client/product/show";
    }

    @GetMapping("/product/{id}")
    public String getMethodName(@PathVariable("id") long id, Model model, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long idUser = (long) session.getAttribute("id");
        User user = this.userService.GetUserById(idUser);
        Product pr = this.productService.fetchProductById(id).get();
        int check = 0;
        if (user == pr.getUser())
            check = 1;
        model.addAttribute("check", check); // Kiểm tra xem sản phẩm có phải của người đang đăng nhập không
        List<Review> reviews = pr.getReviews();
        model.addAttribute("reviews", reviews);
        model.addAttribute("product", pr);
        model.addAttribute("id", id);
        model.addAttribute("user", user);
        return "client/product/detail";
    }

    @PostMapping("/addProductToCart/{id}")
    public String postMethodName(@PathVariable("id") long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        String email = (String) session.getAttribute("email");
        long productId = id;
        this.productService.HandleSaveProductToCart(email, productId, session);
        return "redirect:/";
    }

    @PostMapping("/delete-cart-product/{id}")
    public String postDeleteCartDetails(@PathVariable("id") long id, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long cartDetailId = id;
        this.productService.HanhleRemoveCartDetail(cartDetailId, session);
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String getCheckOutPage(Model model, HttpServletRequest request) {
        User currentUser = new User();
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        currentUser.setId(id);

        Cart cart = this.productService.GetByUser(currentUser);

        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();

        double totalPrice = 0;
        for (CartDetail cd : cartDetails) {
            totalPrice += cd.getPrice() * cd.getQuantity();
        }

        model.addAttribute("cartDetails", cartDetails);
        model.addAttribute("totalPrice", totalPrice);

        return "client/cart/checkout";
    }

    @PostMapping("/confirm-checkout")
    public String getCheckOutPage(@ModelAttribute("cart") Cart cart) { // cart này chỉ lưu id và quantity
        List<CartDetail> cartDetails = cart == null ? new ArrayList<CartDetail>() : cart.getCartDetails();
        this.productService.handleUpdateCartBeforeCheckout(cartDetails);
        return "redirect:/checkout";
    }

    @PostMapping("/place-order")
    public String handlePlaceOrder(
            HttpServletRequest request,
            @RequestParam("receiverName") String receiverName,
            @RequestParam("receiverAddress") String receiverAddress,
            @RequestParam("receiverPhone") String receiverPhone) {
        HttpSession session = request.getSession(false);
        User user = new User();
        long id = (long) session.getAttribute("id");
        user.setId(id);
        this.productService.PlaceOrder(user, session, receiverName, receiverAddress, receiverPhone);
        return "client/cart/Ordersuccess";
    }

    @GetMapping("/myProduct/create")
    public String getProduct(Model model) {
        model.addAttribute("newProduct", new Product());
        return "client/myProduct/create";
    }

    @PostMapping("/myProduct/create")
    public String postMethodName(@ModelAttribute("newProduct") @Valid Product product,
            BindingResult bindingResult,
            @RequestParam("MinhTriFile") MultipartFile file, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession(false);
        Long id = (Long) httpSession.getAttribute("id");
        User user = this.userService.GetUserById(id);
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }

        if (bindingResult.hasErrors())
            return "client/myProduct/create";
        String avatar = this.uploadService.handleSaveUpLoadFile(file, "product");
        product.setImage(avatar);
        product.setUser(user);
        if (user.getRole().getName().equals("ADMIN"))
            product.setStatus("Đã duyệt");
        else
            product.setStatus("Chờ xử lý");
        product.setSold(0);
        this.productService.HandleSaveProduct(product);
        return "redirect:/myProduct";
    }

    @GetMapping("/myProduct")
    public String getMyProduct(Model model, HttpServletRequest request,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page - 1, 8,
                Sort.by(Product_.STATUS).ascending().and(Sort.by(Order_.ID).descending()));
        HttpSession session = request.getSession(false);
        Long id = (Long) session.getAttribute("id");
        User user = this.userService.GetUserById(id);
        Page<Product> productPage = this.productService.GetAllProduct(user, pageable);
        List<Product> products = productPage.getContent();
        model.addAttribute("products", products);
        model.addAttribute("sumPage", productPage.getTotalPages());
        model.addAttribute("nowPage", page);
        return "client/myProduct/show";
    }

    @PostMapping("/myProduct/preUpdate/{id}")
    public String postpreUpate(@RequestParam("quantity") long quantity, @PathVariable("id") long id) {
        Optional<Product> prOptional = this.productService.fetchProductById(id);
        if (prOptional.isPresent()) {
            Product product = prOptional.get();
            product.setQuantity(quantity);
            this.productService.HandleSaveProduct(product);
        }
        return "redirect:/myProduct";
    }

    @PostMapping("/myProduct/delete/{id}")
    public String postMethodName(@PathVariable("id") long id) {
        Optional<Product> product = this.productService.fetchProductById(id);
        if (product.isPresent()) {
            Product realProduct = product.get();
            if (realProduct.getStatus().equals("Đã duyệt")) {
                List<CartDetail> CartDetails = this.productService.findCartDetailByProduct(realProduct);
                if (CartDetails != null) {
                    for (CartDetail cartDetail : CartDetails) {
                        this.productService.RemoveProductWithCartDetail(cartDetail);
                    }
                }
            } else
                this.productService.deleteProduct(realProduct);
        }
        this.productService.deleteProduct(id);
        return "redirect:/myProduct";
    }

    @GetMapping("/myProduct/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        Optional<Product> currentProduct = this.productService.fetchProductById(id);
        model.addAttribute("newProduct", currentProduct.get());
        return "client/myProduct/update";
    }

    @PostMapping("/myProduct/update")
    public String handleUpdateProduct(@ModelAttribute("newProduct") @Valid Product pr,
            BindingResult newProductBindingResult,
            @RequestParam("MinhtriFile") MultipartFile file) {

        // validate
        if (newProductBindingResult.hasErrors()) {
            return "myProduct/update";
        }

        Product currentProduct = this.productService.fetchProductById(pr.getId()).get();
        if (currentProduct != null) {
            if (!file.isEmpty()) {
                String img = this.uploadService.handleSaveUpLoadFile(file, "product");
                currentProduct.setImage(img);
            }

            currentProduct.setName(pr.getName());
            currentProduct.setPrice(pr.getPrice());
            currentProduct.setQuantity(pr.getQuantity());
            currentProduct.setDetailDesc(pr.getDetailDesc());
            currentProduct.setShortDesc(pr.getShortDesc());
            currentProduct.setFactory(pr.getFactory());
            currentProduct.setTarget(pr.getTarget());
            currentProduct.setStatus("Chờ xử lý");
            this.productService.HandleSaveProduct(currentProduct);
        }

        return "redirect:/myProduct";
    }

    @GetMapping("/Order-History")
    public String getMethodName(HttpServletRequest request, Model model,
            @RequestParam(value = "page", defaultValue = "1") int page) {
        HttpSession session = request.getSession(false);
        User user = new User();
        long id = (long) session.getAttribute("id");
        user.setId(id);

        Pageable pageable = PageRequest.of(page - 1, 2, Sort.by(Order_.ID).descending());
        Page<Order> ordersPage = this.userService.getOrderByUser(user, pageable);
        List<Order> orders = ordersPage.getContent();
        model.addAttribute("orders", orders);
        model.addAttribute("sumPage", ordersPage.getTotalPages());
        model.addAttribute("nowPage", page);
        return "client/cart/historyOrder";
    }

    // Lưu đánh giá
    @PostMapping("/review/{id}")
    public String postReview(@RequestParam("assessment") String assessment, @RequestParam("star") int star,
            HttpServletRequest request, @PathVariable("id") long id) {
        HttpSession session = request.getSession(false);
        Long idUser = (Long) session.getAttribute("id");
        User user = this.userService.GetUserById(idUser);
        Optional<Product> productOp = this.productService.fetchProductById(id);
        if (productOp.isPresent()) {
            Product product = productOp.get();
            Review review = new Review();
            LocalDate currentDate = LocalDate.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
            String formattedDate = currentDate.format(formatter);
            review.setDate(formattedDate);
            review.setAssessment(assessment);
            review.setProduct(product);
            review.setStar(star);
            review.setUser(user);
            review.setStatus("Chưa xử lý");
            if (this.productService.CheckPurchaseStatus(product.getImage()))
                review.setPurchaseStatus("Đã mua");
            else
                review.setPurchaseStatus("Chưa mua");
            this.reviewService.SaveReview(review);
        }
        String s = "redirect:/product/" + id;
        return s;
    }

    // Xóa đánh giá của mình (người đánh giá tự xóa)
    @PostMapping("/review/delete/{id}")
    public String postDeleteReview(@PathVariable("id") long id, @RequestParam("id") String idProduct) {
        this.reviewService.deleteReview(id);
        return "redirect:/product/" + idProduct;
    }
}