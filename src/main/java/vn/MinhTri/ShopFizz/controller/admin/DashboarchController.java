package vn.MinhTri.ShopFizz.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.MinhTri.ShopFizz.services.OrderDetailService;
import vn.MinhTri.ShopFizz.services.UserService;

@Controller
public class DashboarchController {

    private final UserService userService;
    private final OrderDetailService orderDetailService;

    public DashboarchController(UserService userService, OrderDetailService orderDetailService) {
        this.userService = userService;
        this.orderDetailService = orderDetailService;
    }

    @GetMapping("/admin")
    public String getDashboarch(Model model) {
        model.addAttribute("countUsers", this.userService.countUsers());
        model.addAttribute("countProducts", this.userService.countProducts());
        model.addAttribute("countOrders", this.userService.countOrders());
        model.addAttribute("sumOrder", this.orderDetailService.sumOrder());
        return "admin/dashboard/show";
    }

}
