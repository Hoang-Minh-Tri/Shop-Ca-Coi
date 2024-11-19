package vn.MinhTri.ShopFizz.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import vn.MinhTri.ShopFizz.domain.Product;
import vn.MinhTri.ShopFizz.domain.User;

import java.util.List;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long>, JpaSpecificationExecutor<Product> {
    Page<Product> findAll(Pageable page);

    Page<Product> findByStatus(String status, Pageable pageable);

    List<Product> findByUser(User user);

    Page<Product> findByUser(User user, Pageable pageable);

    Page<Product> findByUserAndStatus(User user, String status, Pageable pageable);

    @Query("SELECT COUNT(p) FROM Product p WHERE p.factory = :factory and p.status = 'Đã duyệt'")
    int countByFactory(String factory);

    // Page<Product> findAll(Specification<Product> spec, Pageable pageable);
}