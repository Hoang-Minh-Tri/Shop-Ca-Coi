package vn.MinhTri.ShopFizz.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import vn.MinhTri.ShopFizz.domain.ProductOrderDetail;

@Repository
public interface ProductOrderRepository extends JpaRepository<ProductOrderDetail, Long> {

}
