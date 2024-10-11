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
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create1")
    public String postCreateUser(@ModelAttribute("newUser") User user) {
        this.userService.SaveUser(user);
        return "redirect:/";
    }

}
