package vn.MinhTri.ShopFizz.controller.seller;

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

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

//Xử lí các đánh giá sản phẩm từ khách hàng
@Controller
public class ReviewSellerController {
    private final ReviewService reviewService;

    public ReviewSellerController(ReviewService reviewService) {
        this.reviewService = reviewService;
    }

    @GetMapping("/seller/review")
    public String getReviewPage(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        Pageable pageable = PageRequest.of(page - 1, 8,
                Sort.by(Review_.STATUS).ascending().and(Sort.by(Review_.ID).descending()));
        Page<Review> pageReview = this.reviewService.GetReviewByMyProduct(id, pageable);
        List<Review> reviews = pageReview.getContent();
        model.addAttribute("reviews", reviews);
        model.addAttribute("sumPage", pageReview.getTotalPages());
        model.addAttribute("nowPage", page);
        return "seller/review/show";
    }

    @GetMapping("/seller/review/check/{id}")
    public String postCheckReview(@PathVariable("id") long id) {
        Review review = this.reviewService.GetReviewById(id);
        review.setStatus("Đã xử lý");
        this.reviewService.Save(review);
        return "redirect:/seller/review?page=1";
    }
}
