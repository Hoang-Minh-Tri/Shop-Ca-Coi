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
import org.springframework.web.bind.annotation.PathVariable;

import vn.MinhTri.ShopFizz.domain.Cart;
import vn.MinhTri.ShopFizz.domain.CartDetail;
import vn.MinhTri.ShopFizz.domain.Product;
import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.domain.dto.DtoRegister;
import vn.MinhTri.ShopFizz.domain.dto.Forgot;
import vn.MinhTri.ShopFizz.services.ProductService;
import vn.MinhTri.ShopFizz.services.UploadService;
import vn.MinhTri.ShopFizz.services.UserService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class HomePageController {
    private final ProductService productService;
    private final UserService userService;
    private PasswordEncoder passwordEncoder;
    private UploadService uploadService;

    public HomePageController(ProductService productService, UserService userService, PasswordEncoder passwordEncoder,
            UploadService uploadService) {
        this.productService = productService;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
        this.uploadService = uploadService;
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

    @GetMapping("/client/user/update/{id}")
    public String getUpdatePage(Model model, @PathVariable("id") long id) {
        User user = this.userService.GetUserById(id);
        model.addAttribute("newUser", user);
        return "client/profile/show";
    }

    @PostMapping("/client/user/update")
    public String postUpdateUser(@ModelAttribute("newUser") User user,
            @RequestParam("MinhTriFile") MultipartFile file, HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        User newUser = this.userService.GetUserById(user.getId());
        String avatar = this.uploadService.handleSaveUpLoadFile(file, "avatar");
        if (newUser != null) {
            newUser.setAddress(user.getAddress());
            newUser.setEmail(user.getEmail());
            newUser.setFullName(user.getFullName());
            newUser.setPhone(user.getPhone());
            newUser.setAvatar(avatar);
            session.setAttribute("avatar", avatar); // Cập nhật lại avatar cho session hiển thị trên thanh header
            this.userService.HandleSaveUser(newUser);
        }
        return "redirect:/";
    }
}
