package vn.MinhTri.ShopFizz.controller;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

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
    public String getAllUser(Model model) {
        List<User> users = this.userService.GetAllUser();
        model.addAttribute("users", users);
        return "/admin/user/table-user";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserDetail(@PathVariable("id") long id, Model model) {
        User user = this.userService.GetUserById(id);
        model.addAttribute("user", user);
        return "/admin/user/show";
    }

    @GetMapping("/admin/user/create")
    public String getMethodName(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create1")
    public String postCreateUser(@ModelAttribute("newUser") User user) {
        this.userService.HanleSaveUser(user);
        return "redirect:/admin/user";
    }

    @GetMapping("/admin/user/update/{id}")
    public String getUpdatePage(Model model, @PathVariable("id") long id) {
        User user = this.userService.GetUserById(id);
        model.addAttribute("user1", user);
        return "/admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(@ModelAttribute("user1") User user) {
        User newUser = this.userService.GetUserById(user.getId());
        if (newUser != null) {
            newUser.setAddress(user.getAddress());
            newUser.setEmail(user.getEmail());
            newUser.setFullName(user.getFullName());
            newUser.setPhone(user.getPhone());
            this.userService.HanleSaveUser(newUser);
        }
        return "redirect:/admin/user";
    }

}
