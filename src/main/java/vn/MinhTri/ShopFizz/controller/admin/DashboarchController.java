package vn.MinhTri.ShopFizz.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashboarchController {
    @GetMapping("/admin")
    public String getDashboarch() {
        return "admin/dashboard/show";
    }
}
