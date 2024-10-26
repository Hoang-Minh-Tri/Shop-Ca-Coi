package vn.MinhTri.ShopFizz.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import vn.MinhTri.ShopFizz.domain.Order;
import vn.MinhTri.ShopFizz.domain.Role;
import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.domain.dto.DtoRegister;
import vn.MinhTri.ShopFizz.repository.OrderRepository;
import vn.MinhTri.ShopFizz.repository.ProductRepository;
import vn.MinhTri.ShopFizz.repository.RoleRepository;
import vn.MinhTri.ShopFizz.repository.UserRepository;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final ProductRepository productRepository;
    private final OrderRepository orderRepository;

    public UserService(UserRepository userRepository, RoleRepository roleRepository,
            ProductRepository productRepository, OrderRepository orderRepository) {
        this.userRepository = userRepository;
        this.roleRepository = roleRepository;
        this.productRepository = productRepository;
        this.orderRepository = orderRepository;
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

    public long countUsers() {
        return this.userRepository.count();
    }

    public long countProducts() {
        return this.productRepository.count();
    }

    public long countOrders() {
        return this.orderRepository.count();
    }

    public List<Order> getOrderByUser(User user) {
        return this.orderRepository.findByUser(user);
    }

    public Page<Order> getOrderByUser(User user, Pageable pageable) {
        return this.orderRepository.findByUser(user, pageable);
    }

    public Page<User> GetAllUserPage(Pageable pageable) {
        return this.userRepository.findAll(pageable);
    }
}
