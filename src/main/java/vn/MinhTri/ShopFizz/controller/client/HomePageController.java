package vn.MinhTri.ShopFizz.controller.client;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import vn.MinhTri.ShopFizz.domain.Product;
import vn.MinhTri.ShopFizz.services.ProductService;
import java.util.List;

@Controller
public class HomePageController {
    private final ProductService productService;

    public HomePageController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/")
    public String getMethodName(Model model) {
        List<Product> products = this.productService.GetAllProduct();
        model.addAttribute("products", products);
        return "client/homepage/show";
    }

}
