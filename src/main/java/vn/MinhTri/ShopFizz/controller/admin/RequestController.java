package vn.MinhTri.ShopFizz.controller.admin;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import vn.MinhTri.ShopFizz.domain.Product;
import vn.MinhTri.ShopFizz.services.ProductService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import java.util.Optional;

//Xử lí các yêu cầu của người dùng lên admin

@Controller
public class RequestController {
    private final ProductService productService;

    public RequestController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping("/admin/request")
    public String getRequestPage(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page - 1, 6);
        Page<Product> products = this.productService.GetAllProductStatus("Chờ xử lý", pageable);
        List<Product> listProducts = products.getContent();
        model.addAttribute("products", listProducts);
        model.addAttribute("nowPage", page);
        model.addAttribute("sumPage", products.getTotalPages());
        return "admin/request/show";
    }

    @GetMapping("/admin/request/{id}")
    public String getDetailsRequest(Model model, @PathVariable("id") long id) {
        Optional<Product> product = this.productService.fetchProductById(id);
        if (product.isPresent()) {
            Product realProduct = product.get();
            model.addAttribute("id", id);
            model.addAttribute("product", realProduct);
            return "admin/request/detail";
        } else {
            return "redirect:/admin/request";
        }
    }

    @GetMapping("/admin/request/agree/{id}")
    public String getAgreeRequest(Model model, @PathVariable("id") long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "admin/request/agree";
    }

    @PostMapping("/admin/request/agree")
    public String postAgreeRequest(@ModelAttribute("newProduct") Product product) {
        Long id = product.getId();
        Optional<Product> nowProduct = this.productService.fetchProductById(id);
        if (nowProduct.isPresent()) {
            Product realProduct = nowProduct.get();
            realProduct.setStatus("Đã duyệt");
            this.productService.HandleSaveProduct(realProduct);
        }
        return "redirect:/admin/request";
    }

    @GetMapping("/admin/request/delete/{id}")
    public String getDeleteRequest(Model model, @PathVariable("id") long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "admin/request/delete";
    }

    @PostMapping("/admin/request/delete")
    public String postDeleteRequest(@ModelAttribute("newProduct") Product product) {
        Long id = product.getId();
        Optional<Product> nowProduct = this.productService.fetchProductById(id);
        if (nowProduct.isPresent()) {
            Product realProduct = nowProduct.get();
            this.productService.deleteProduct(realProduct);
        }
        return "redirect:/admin/request";
    }

}
