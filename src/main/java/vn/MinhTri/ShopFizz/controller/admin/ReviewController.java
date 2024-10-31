package vn.MinhTri.ShopFizz.controller.admin;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import vn.MinhTri.ShopFizz.domain.Review;
import vn.MinhTri.ShopFizz.domain.Review_;
import vn.MinhTri.ShopFizz.services.ReviewService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;

//Xử lí các đánh giá sản phẩm từ khách hàng
@Controller
public class ReviewController {
    private final ReviewService reviewService;

    public ReviewController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/admin/review")
    public String getReviewPage(Model model, @RequestParam(value = "page", defaultValue = "1") int page) {
        Pageable pageable = PageRequest.of(page - 1, 8,
                Sort.by(Review_.STATUS).ascending().and(Sort.by(Review_.ID).descending()));
        Page<Review> pageReview = this.reviewService.GetAllReviewPage(pageable);
        List<Review> reviews = pageReview.getContent();
        model.addAttribute("reviews", reviews);
        model.addAttribute("sumPage", pageReview.getTotalPages());
        model.addAttribute("nowPage", page);
        return "admin/review/show";
    }

    @GetMapping("/admin/review/delete/{id}")
    public String postDeleteReview(@PathVariable("id") long id) {
        this.reviewService.DeleteById(id);
        return "redirect:/admin/review?page=1";
    }
}
