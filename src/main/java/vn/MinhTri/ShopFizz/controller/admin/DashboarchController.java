package vn.MinhTri.ShopFizz.controller.admin;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.MinhTri.ShopFizz.domain.Order;
import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.services.UserService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

@Controller
public class DashboarchController {

    private final UserService userService;

    public DashboarchController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/admin")
    public String getDashboarch(Model model) {
        model.addAttribute("countUsers", this.userService.countUsers());
        model.addAttribute("countProducts", this.userService.countProducts());
        model.addAttribute("countOrders", this.userService.countOrders());
        return "admin/dashboard/show";
    }

    @GetMapping("/Order-History")
    public String getMethodName(HttpServletRequest request, Model model) {
        HttpSession session = request.getSession(false);
        User user = new User();
        long id = (long) session.getAttribute("id");
        user.setId(id);

        List<Order> orders = this.userService.getOrderByUser(user);
        model.addAttribute("orders", orders);
        return "client/cart/historyOrder";
    }

}
