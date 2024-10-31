package vn.MinhTri.ShopFizz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.MinhTri.ShopFizz.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Lấy các đánh giá của sản phẩm mình bán
    @Query("SELECT cd FROM Review cd WHERE cd.product.user.id= :id")
    Page<Review> findReviewByProduct(@Param("id") long id, Pageable pageable);
}
