package vn.MinhTri.ShopFizz.controller.admin;

import java.util.List;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.services.UploadService;
import vn.MinhTri.ShopFizz.services.UserService;

@Controller
public class UserController {

    private final UserService userService;
    private final UploadService uploadService;
    private PasswordEncoder passwordEncoder;

    public UserController(UserService userService, UploadService uploadService,
            PasswordEncoder passwordEncoder) {
        this.userService = userService;
        this.uploadService = uploadService;
        this.passwordEncoder = passwordEncoder;
    }

    @GetMapping("/admin/user")
    public String getAllUser(Model model) {
        List<User> users = this.userService.GetAllUser();
        model.addAttribute("users", users);
        return "/admin/user/show";
    }

    @GetMapping("/admin/user/{id}")
    public String getUserDetail(@PathVariable("id") long id, Model model) {
        User user = this.userService.GetUserById(id);
        model.addAttribute("user", user);
        return "/admin/user/detail";
    }

    @GetMapping("/admin/user/create")
    public String getMethodName(Model model) {
        model.addAttribute("newUser", new User());
        return "admin/user/create";
    }

    @PostMapping("/admin/user/create")
    public String postCreateUser(@ModelAttribute("newUser") User user,
            @RequestParam("MinhTriFile") MultipartFile file) {
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
        return "/admin/user/update";
    }

    @PostMapping("/admin/user/update")
    public String postUpdateUser(@ModelAttribute("newUser") User user) {
        User newUser = this.userService.GetUserById(user.getId());
        if (newUser != null) {
            newUser.setAddress(user.getAddress());
            newUser.setEmail(user.getEmail());
            newUser.setFullName(user.getFullName());
            newUser.setPhone(user.getPhone());
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
        if (user != null)
            this.userService.DeleteAUser(user);

        return "redirect:/admin/user";
    }

}