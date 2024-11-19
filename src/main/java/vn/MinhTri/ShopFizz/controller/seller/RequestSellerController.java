package vn.MinhTri.ShopFizz.controller.seller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import vn.MinhTri.ShopFizz.domain.CartDetail;
import vn.MinhTri.ShopFizz.domain.Order_;
import vn.MinhTri.ShopFizz.domain.Product;
import vn.MinhTri.ShopFizz.domain.Review;
import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.services.ProductService;
import vn.MinhTri.ShopFizz.services.ReviewService;
import vn.MinhTri.ShopFizz.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;

//Xử lí các yêu cầu của người dùng lên admin

@Controller
public class RequestSellerController {
    private final ProductService productService;
    private final UserService userService;

    private final ReviewService reviewService;

    public RequestSellerController(ProductService productService, UserService userService,
            ReviewService reviewService) {
        this.productService = productService;
        this.userService = userService;
        this.reviewService = reviewService;
    }

    @GetMapping("/seller/request")
    public String getRequestPage(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        User user = this.userService.GetUserById(id);
        Pageable pageable = PageRequest.of(page - 1, 6, Sort.by(Order_.ID).descending());
        Page<Product> products = this.productService.GetAllProductStatus(user, "Chờ xử lý", pageable);
        List<Product> listProducts = products.getContent();
        model.addAttribute("products", listProducts);
        model.addAttribute("nowPage", page);
        model.addAttribute("sumPage", products.getTotalPages());
        return "seller/request/show";
    }

    @GetMapping("/seller/request/{id}")
    public String getDetailsRequest(Model model, @PathVariable("id") long id) {
        Optional<Product> product = this.productService.fetchProductById(id);
        if (product.isPresent()) {
            Product realProduct = product.get();
            model.addAttribute("id", id);
            model.addAttribute("product", realProduct);
            return "seller/request/detail";
        } else {
            return "redirect:/seller/request";
        }
    }

    @GetMapping("/seller/request/delete/{id}")
    public String getDeleteRequest(Model model, @PathVariable("id") long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "seller/request/delete";
    }

    @PostMapping("/seller/request/delete")
    public String postDeleteRequest(@ModelAttribute("newProduct") Product pr) {
        Long id = pr.getId();
        List<CartDetail> CartDetails = this.productService.findCartDetailByProduct(pr);
        if (CartDetails != null) {
            for (CartDetail cartDetail : CartDetails) {
                this.productService.RemoveProductWithCartDetail(cartDetail);
            }
        }
        Optional<Product> productOp = this.productService.fetchProductById(pr.getId());
        if (productOp.isPresent()) {
            Product product = productOp.get();
            List<Review> reviews = product.getReviews();
            for (Review review : reviews) {
                this.reviewService.Delete(review);
            }

        }

        Optional<Product> nowProduct = this.productService.fetchProductById(id);
        if (nowProduct.isPresent()) {
            Product realProduct = nowProduct.get();
            this.productService.deleteProduct(realProduct);
        }
        return "redirect:/seller/request";
    }

}
