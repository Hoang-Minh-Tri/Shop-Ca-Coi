package vn.MinhTri.ShopFizz.services;

import java.util.List;
import java.util.Optional;

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

    public User GetUserById(long id) {
        Optional<User> user = this.userRepository.findById(id);
        if (user.isPresent())
            return user.get();
        return null;
    }

    public void DeleteAUser(User user) {
        this.userRepository.delete(user);
    }
}
