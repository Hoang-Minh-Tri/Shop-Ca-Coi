package vn.MinhTri.ShopFizz.controller.seller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;

import vn.MinhTri.ShopFizz.domain.CartDetail;
import vn.MinhTri.ShopFizz.domain.Product;
import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.services.ProductService;
import vn.MinhTri.ShopFizz.services.UploadService;
import vn.MinhTri.ShopFizz.services.UserService;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;
import jakarta.validation.Valid;

@Controller
public class ProductSellerController {
    private final ProductService productService;
    private final UploadService uploadService;
    private final UserService userService;

    public ProductSellerController(ProductService productService, UploadService uploadService,
            UserService userService) {
        this.productService = productService;
        this.uploadService = uploadService;
        this.userService = userService;
    }

    @GetMapping("/seller/product")
    public String getProduct(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        User user = this.userService.GetUserById(id);
        Pageable pageable = PageRequest.of(page - 1, 4);
        Page<Product> products = this.productService.GetAllProductStatus(user, "Đã duyệt", pageable);
        List<Product> listProducts = products.getContent();
        model.addAttribute("products", listProducts);
        model.addAttribute("nowPage", page);
        model.addAttribute("sumPage", products.getTotalPages());
        return "seller/product/show";
    }

    @GetMapping("/seller/product/create")
    public String getCreatePage(Model model) {
        model.addAttribute("newProduct", new Product());
        return "seller/product/create";
    }

    @PostMapping("/seller/product/create")
    public String postMethodName(@ModelAttribute("newProduct") @Valid Product product,
            BindingResult bindingResult,
            @RequestParam("MinhTriFile") MultipartFile file, HttpServletRequest httpServletRequest) {
        HttpSession httpSession = httpServletRequest.getSession(false);
        Long id = (Long) httpSession.getAttribute("id");
        User user = this.userService.GetUserById(id);
        List<FieldError> errors = bindingResult.getFieldErrors();
        for (FieldError error : errors) {
            System.out.println(error.getField() + " - " + error.getDefaultMessage());
        }

        if (bindingResult.hasErrors())
            return "seller/product/create";
        String avatar = this.uploadService.handleSaveUpLoadFile(file, "product");
        product.setImage(avatar);
        product.setUser(user);
        product.setStatus("Chờ xử lý");
        product.setSold(0);
        this.productService.HandleSaveProduct(product);
        return "redirect:/seller/request";
    }

    @GetMapping("/seller/product/update/{id}")
    public String getUpdateProductPage(Model model, @PathVariable long id) {
        Optional<Product> currentProduct = this.productService.fetchProductById(id);
        model.addAttribute("newProduct", currentProduct.get());
        return "seller/product/update";
    }

    @PostMapping("/seller/product/update")
    public String handleUpdateProduct(@ModelAttribute("newProduct") @Valid Product pr,
            BindingResult newProductBindingResult,
            @RequestParam("hoidanitFile") MultipartFile file) {

        // validate
        if (newProductBindingResult.hasErrors()) {
            return "seller/product/update";
        }

        Product currentProduct = this.productService.fetchProductById(pr.getId()).get();
        if (currentProduct != null) {
            if (!file.isEmpty()) {
                String img = this.uploadService.handleSaveUpLoadFile(file, "product");
                currentProduct.setImage(img);
            }

            currentProduct.setName(pr.getName());
            currentProduct.setPrice(pr.getPrice());
            currentProduct.setQuantity(pr.getQuantity());
            currentProduct.setDetailDesc(pr.getDetailDesc());
            currentProduct.setShortDesc(pr.getShortDesc());
            currentProduct.setFactory(pr.getFactory());
            currentProduct.setTarget(pr.getTarget());
            currentProduct.setStatus("Chờ xử lý");

            this.productService.HandleSaveProduct(currentProduct);
        }

        return "redirect:/seller/request";
    }

    @GetMapping("/seller/product/delete/{id}")
    public String getDeleteProductPage(Model model, @PathVariable long id) {
        model.addAttribute("id", id);
        model.addAttribute("newProduct", new Product());
        return "seller/product/delete";
    }

    @PostMapping("/seller/product/delete")
    public String postDeleteProduct(Model model, @ModelAttribute("newProduct") Product pr) {
        List<CartDetail> CartDetails = this.productService.findCartDetailByProduct(pr);
        if (CartDetails != null) {
            for (CartDetail cartDetail : CartDetails) {
                this.productService.RemoveProductWithCartDetail(cartDetail);
            }
        }
        this.productService.deleteProduct(pr);
        return "redirect:/seller/product";
    }

    @GetMapping("/seller/product/{id}")
    public String getProductDetailPage(Model model, @PathVariable long id) {
        Product pr = this.productService.fetchProductById(id).get();
        model.addAttribute("product", pr);
        model.addAttribute("id", id);
        return "seller/product/detail";
    }
}
