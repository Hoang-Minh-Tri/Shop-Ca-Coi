package vn.MinhTri.ShopFizz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.MinhTri.ShopFizz.domain.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

}
