package vn.MinhTri.ShopFizz.services;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void HanleSaveUser(User user) {
        this.userRepository.save(user);
    }

    public List<User> GetAllUser() {
        return this.userRepository.findAll();
    }
}
