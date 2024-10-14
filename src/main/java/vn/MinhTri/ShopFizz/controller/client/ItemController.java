package vn.MinhTri.ShopFizz.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@Controller
public class ItemController {
    @GetMapping("/product/{id}")
    public String getMethodName(@PathVariable("id") long id) {
        return "client/product/show";
    }

}
