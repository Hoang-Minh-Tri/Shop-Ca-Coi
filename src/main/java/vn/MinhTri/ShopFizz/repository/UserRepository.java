package vn.MinhTri.ShopFizz.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import vn.MinhTri.ShopFizz.domain.User;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    List<User> findAll();

    boolean existsByEmail(String email);

    User findByEmail(String email);
}
