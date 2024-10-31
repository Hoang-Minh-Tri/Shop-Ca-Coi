package vn.MinhTri.ShopFizz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import vn.MinhTri.ShopFizz.domain.OrderDetail;

@Repository
public interface OrderDetailRepository extends JpaRepository<OrderDetail, Long> {
    @Query("SELECT cd FROM OrderDetail cd WHERE cd.productOrderDetail.userName = :email")
    Page<OrderDetail> findByProductUserEmail(@Param("email") String email, Pageable pageable);

}
