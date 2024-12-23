package vn.MinhTri.ShopFizz.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.MinhTri.ShopFizz.domain.Order;
import vn.MinhTri.ShopFizz.domain.User;

@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findByUser(User user);

    Page<Order> findByUser(User user, Pageable pageable);

    Page<Order> findAll(Pageable pageable);

}
