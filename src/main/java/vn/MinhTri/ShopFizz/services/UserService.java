package vn.MinhTri.ShopFizz.services;

import org.springframework.stereotype.Service;

import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;

    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void SaveUser(User user) {
        this.userRepository.save(user);
    }
}
