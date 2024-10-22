package vn.MinhTri.ShopFizz.controller.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.MinhTri.ShopFizz.domain.Cart;
import vn.MinhTri.ShopFizz.domain.CartDetail;
import vn.MinhTri.ShopFizz.domain.Product;
import vn.MinhTri.ShopFizz.domain.Product_;
import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.domain.dto.ProductCrieateDTO;
import vn.MinhTri.ShopFizz.services.ProductService;
import org.springframework.web.bind.annotation.PostMapping;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;
import java.util.ArrayList;

@Controller
public class ItemController {
    private final ProductService productService;

    public ItemController(ProductService productService) {
        this.productService = productService;
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
    public String getMethodName(@PathVariable("id") long id, Model model) {
        Product pr = this.productService.fetchProductById(id).get();
        model.addAttribute("product", pr);
        model.addAttribute("id", id);
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

    @PostMapping("/delete_Cart_Product/{id}")
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

}
