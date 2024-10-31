package vn.MinhTri.ShopFizz.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.MinhTri.ShopFizz.domain.Review;
import vn.MinhTri.ShopFizz.repository.ReviewRepository;

@Service
public class ReviewService {
    private final ReviewRepository reviewRepository;

    public ReviewService(ReviewRepository reviewRepository) {
        this.reviewRepository = reviewRepository;
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
}
