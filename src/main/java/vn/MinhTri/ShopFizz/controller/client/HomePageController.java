package vn.MinhTri.ShopFizz.controller.client;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.MinhTri.ShopFizz.domain.Cart;
import vn.MinhTri.ShopFizz.domain.CartDetail;
import vn.MinhTri.ShopFizz.domain.Product;
import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.domain.dto.DtoRegister;
import vn.MinhTri.ShopFizz.domain.dto.Forgot;
import vn.MinhTri.ShopFizz.repository.CartDetailsRepository;
import vn.MinhTri.ShopFizz.repository.CartRepository;
import vn.MinhTri.ShopFizz.services.ProductService;
import vn.MinhTri.ShopFizz.services.UserService;

import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.view.RedirectView;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomePageController {
    private final ProductService productService;
    private final UserService userService;
    private PasswordEncoder passwordEncoder;
    private CartRepository cartRepository;
    private CartDetailsRepository cartDetailsRepository;

    public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder,
            CartRepository cartRepository, CartDetailsRepository cartDetailsRepository) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.cartRepository = cartRepository;
        this.cartDetailsRepository = cartDetailsRepository;
    }

    @GetMapping("/")
    public String getMethodName(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        this.productService.ConverStatus();
        Pageable pageable = PageRequest.of(page - 1, 20);
        Page<Product> productPage = this.productService.GetAllProductPage("Đã duyệt", pageable);
        List<Product> products = productPage.getContent();
        model.addAttribute("sumPage", productPage.getTotalPages());
        model.addAttribute("nowPage", page);
        model.addAttribute("products", products);
        return "client/homepage/show";
    }

    @GetMapping("/register")
    public String getRegisterPage(Model model) {
        model.addAttribute("registerUser", new DtoRegister());
        return "client/auth/register";
    }

    @PostMapping("/register")
    public String postMethodName(@ModelAttribute("registerUser") @Valid DtoRegister dtoRegister,
            BindingResult bindingResult) {
        // List<FieldError> errors = bindingResult.getFieldErrors();
        // for (FieldError error : errors) {
        // System.out.println(error.getField() + " - " + error.getDefaultMessage());
        // }

        if (bindingResult.hasErrors())
            return "/client/auth/register";
        User user = this.userService.registerDtoToUser(dtoRegister);
        String hashPass = this.passwordEncoder.encode(user.getPassword());
        user.setPassword(hashPass);
        user.setRole(this.userService.getRoleByName("USER"));
        this.userService.HandleSaveUser(user);
        return "redirect:/login";
    }

    @GetMapping("/login")
    public String getLoginPage() {
        return "client/auth/login";
    }

    // Trang thông báo lỗi
    @GetMapping("/access")
    public String getaccessPay() {
        return "client/auth/deny";
    }

    @GetMapping("/cart")
    public String getCartPage(Model model, HttpServletRequest request) {
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

        model.addAttribute("cart", cart);
        return "client/cart/show";
    }

    @GetMapping("/forgot")
    public String getForgot(Model model) {
        model.addAttribute("forgot", new Forgot());
        return "client/auth/forgot";
    }

    @PostMapping("/forgot")
    public String postforgotpage(@ModelAttribute("forgot") @Valid Forgot forgot,
            BindingResult bindingResult) {
        if (bindingResult.hasErrors())
            return "client/auth/forgot";
        User user = this.userService.getUserByEmail(forgot.getEmail());
        String hashPass = this.passwordEncoder.encode(forgot.getPassword());
        user.setPassword(hashPass);
        this.userService.HandleSaveUser(user);
        return "redirect:/login";
    }
}
