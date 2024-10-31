package vn.MinhTri.ShopFizz.controller.seller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class DashBoarchControler {
    @GetMapping("/seller")
    public String getPageSeller(Model model) {
        return "seller/dashboard/show";
    }

}
