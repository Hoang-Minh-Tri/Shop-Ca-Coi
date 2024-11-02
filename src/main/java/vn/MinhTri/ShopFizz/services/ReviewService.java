package vn.MinhTri.ShopFizz.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.MinhTri.ShopFizz.domain.CartDetail;
import vn.MinhTri.ShopFizz.domain.Product;
import vn.MinhTri.ShopFizz.domain.Review;
import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.repository.CartDetailsRepository;
import vn.MinhTri.ShopFizz.repository.ProductRepository;
import vn.MinhTri.ShopFizz.repository.ReviewRepository;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;
    private final ProductRepository productRepository;
    private final CartDetailsRepository cartDetailsRepository;

    public ReviewService(ReviewRepository reviewRepository, ProductRepository productRepository,
            CartDetailsRepository cartDetailsRepository) {
        this.reviewRepository = reviewRepository;
        this.productRepository = productRepository;
        this.cartDetailsRepository = cartDetailsRepository;
    }

    // Lưu các đánh giá
    public void SaveReview(Review review) {
        this.reviewRepository.save(review);
    }

    // Xóa đánh giá của người dùng
    public void deleteReview(long id) {
        this.reviewRepository.deleteById(id);
    }

    // Lấy review từ id
    public Review GetReviewById(Long id) {
        return this.reviewRepository.findById(id).get();
    }

    // Lấy toàn bộ đánh giá có phân trang
    public Page<Review> GetAllReviewPage(Pageable pageable) {
        return this.reviewRepository.findAll(pageable);
    }

    // Lưu dánh giá
    public void Save(Review review) {
        this.reviewRepository.save(review);
    }

    // Xóa đánh giá từ phía admin

    public void DeleteById(Long id) {
        this.reviewRepository.deleteById(id);
    }

    // Lấy các đánh giá của sản phẩm do mình bán
    public Page<Review> GetReviewByMyProduct(long id, Pageable pageable) {
        return this.reviewRepository.findReviewByProduct(id, pageable);
    }

    public void DeleteByUser(User user) {
        List<Review> reviews = this.reviewRepository.findByUser(user);
        for (Review review : reviews) {
            this.reviewRepository.delete(review);
        }
    }
}
