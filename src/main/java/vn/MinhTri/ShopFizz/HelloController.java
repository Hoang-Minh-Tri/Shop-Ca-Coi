package vn.MinhTri.ShopFizz;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class HelloController {

    @GetMapping("/")
    public String index() {
        return "Hello World update";
    }

    @GetMapping("/user")
    public String userPage() {
        return "";
    }

    @GetMapping("/admin")
    public String adminPage() {
        return "";
    }

}
