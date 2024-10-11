package vn.MinhTri.ShopFizz.controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;

import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.services.UserService;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class UserController {

    private final UserService userService;

    UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/")
    public String getHomePage() {
        return "hello";
    }

    @GetMapping("/admin/user")
    public String getMethodName(Model model) {
        User user = new User();
        user.setId(1);
        user.setEmail("minhtri@gmail.com");
        user.setFullName("minhtri");
        user.setPassword("123456");
        user.setAddress("Ha Noi");
        user.setPhone("123456");
        model.addAttribute("newUser", user);
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create1")
    public String postCreateUser(@ModelAttribute("newUser") User user) {
        user.ToString();

        return "redirect:/";
    }

}
