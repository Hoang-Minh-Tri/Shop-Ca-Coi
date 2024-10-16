package vn.MinhTri.ShopFizz.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.MinhTri.ShopFizz.domain.Role;
import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.domain.dto.DtoRegister;
import vn.MinhTri.ShopFizz.repository.RoleRepository;
import vn.MinhTri.ShopFizz.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
    }

    public void HandleSaveUser(User user) {
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

    public Role getRoleByName(String name) {
        return this.roleRepository.findByName(name);
    }

    public User registerDtoToUser(DtoRegister dtoRegister) {
        User user = new User();
        user.setFullName(dtoRegister.getFirstName() + " " + dtoRegister.getLastName());
        user.setEmail(dtoRegister.getEmail());
        user.setPassword(dtoRegister.getPassword());
        return user;
    }

    public boolean CheckEmailExits(String email) {
        return this.userRepository.existsByEmail(email);
    }

    public User getUserByEmail(String email) {
        return this.userRepository.findByEmail(email);
    }
}
