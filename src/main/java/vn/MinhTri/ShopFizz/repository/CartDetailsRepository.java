package vn.MinhTri.ShopFizz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.MinhTri.ShopFizz.domain.CartDetail;

@Repository
public interface CartDetailsRepository extends JpaRepository<CartDetail, Long> {

}
