package vn.MinhTri.ShopFizz.controller.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.mail.MailSender;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import jakarta.validation.Valid;
import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.services.CartService;
import vn.MinhTri.ShopFizz.services.OrderSevice;
import vn.MinhTri.ShopFizz.services.ProductService;
import vn.MinhTri.ShopFizz.services.ReviewService;
import vn.MinhTri.ShopFizz.services.UploadService;
import vn.MinhTri.ShopFizz.services.UserService;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private final PasswordEncoder passwordEncoder;
    private final ReviewService reviewService;
    private final CartService cartService;
    private final OrderSevice orderService;
    private final ProductService productService;

    public UserController(UserService userService, UploadService uploadService, PasswordEncoder passwordEncoder,
            ReviewService reviewService, CartService cartService, OrderSevice orderService,
            ProductService productService) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
        this.reviewService = reviewService;
        this.cartService = cartService;
        this.orderService = orderService;
        this.productService = productService;
    }

    @GetMapping("/admin/user")
    public String getAllUser(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page - 1, 4);
        Page<User> users = this.userService.GetAllUserPage(pageable);
        List<User> listUsers = users.getContent();
        model.addAttribute("nowPage", page);
        model.addAttribute("sumPage", users.getTotalPages());
        model.addAttribute("users", listUsers);
        return "admin/user/show";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserDetail(@PathVariable("id") long id, Model model) {
        User user = this.userService.GetUserById(id);
        model.addAttribute("user", user);
        return "admin/user/detail";
    }

    @GetMapping("/admin/user/create")
    public String getMethodName(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String postCreateUser(@ModelAttribute("newUser") @Valid User user,
            BindingResult bindingResult,
            @RequestParam("MinhTriFile") MultipartFile file) {
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }

        if (bindingResult.hasErrors())
            return "/admin/user/create";
        String avatar = this.uploadService.handleSaveUpLoadFile(file, "avatar");
        String hashPass = this.passwordEncoder.encode(user.getPassword());
        user.setAvatar(avatar);
        user.setPassword(hashPass);
        user.setRole(this.userService.getRoleByName(user.getRole().getName()));
        this.userService.HandleSaveUser(user);
        return "redirect:/admin/user";

    }

    @GetMapping("/admin/user/update/{id}")
    public String getUpdatePage(Model model, @PathVariable("id") long id) {
        User user = this.userService.GetUserById(id);
        model.addAttribute("newUser", user);
        return "admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(@ModelAttribute("newUser") User user,
            @RequestParam("MinhTriFile") MultipartFile file) {
        User newUser = this.userService.GetUserById(user.getId());
        String avatar = this.uploadService.handleSaveUpLoadFile(file, "avatar");
        if (newUser != null) {
            newUser.setAddress(user.getAddress());
            newUser.setEmail(user.getEmail());
            newUser.setFullName(user.getFullName());
            newUser.setPhone(user.getPhone());
            newUser.setAvatar(avatar);
            this.userService.HandleSaveUser(newUser);
        }
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/delete/{id}")
    public String getDeletePage(@PathVariable("id") long id, Model model) {
        model.addAttribute("id", id);
        User user = this.userService.GetUserById(id);
        model.addAttribute("newUser", user);
        return "admin/user/delete";
    }

    @PostMapping("/admin/user/delete")
    public String postDeleteUser(@ModelAttribute("newUser") User user) {
        if (user != null) {
            this.reviewService.DeleteByUser(user);
            this.cartService.DeleteByUser(user);
            this.orderService.DeleteByUser(user);
            this.productService.deleteByUser(user);
            this.userService.DeleteAUser(user);
        }
        return "redirect:/admin/user";
    }

}
