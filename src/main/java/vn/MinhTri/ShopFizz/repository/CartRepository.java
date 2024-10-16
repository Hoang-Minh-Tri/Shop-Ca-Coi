package vn.MinhTri.ShopFizz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.MinhTri.ShopFizz.domain.Cart;

@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
    Cart findByUser(vn.MinhTri.ShopFizz.domain.User user);

}
