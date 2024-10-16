package vn.MinhTri.ShopFizz.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import vn.MinhTri.ShopFizz.domain.Cart;
import vn.MinhTri.ShopFizz.domain.CartDetail;
import vn.MinhTri.ShopFizz.domain.Product;
import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.repository.CartDetailsRepository;
import vn.MinhTri.ShopFizz.repository.CartRepository;
import vn.MinhTri.ShopFizz.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailsRepository cartDetailsRepository;
    private final UserService userService;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailsRepository cartDetailsRepository, UserService userService) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailsRepository = cartDetailsRepository;
        this.userService = userService;
    }

    public List<Product> GetAllProduct() {
        List<Product> products = this.productRepository.findAll();
        return products;
    }

    public void HandleSaveProduct(Product product) {
        this.productRepository.save(product);
    }

    public Optional<Product> fetchProductById(long id) {
        return this.productRepository.findById(id);
    }

    public void deleteProduct(long id) {
        this.productRepository.deleteById(id);
    }

    public void HandleSaveProductToCart(String email, long productId) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            Cart cart = this.cartRepository.findByUser(user);
            if (cart == null) {
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setSum(1);
                cart = this.cartRepository.save(newCart);
            }
            Optional<Product> product = this.productRepository.findById(productId);
            if (product.isPresent()) {
                Product productNew = product.get();

                CartDetail cartDetail = new CartDetail();
                cartDetail.setCart(cart);
                cartDetail.setProduct(productNew);
                cartDetail.setPrice(productNew.getPrice());
                cartDetail.setQuantity(1);

                this.cartDetailsRepository.save(cartDetail);
            }

        }
    }
}
