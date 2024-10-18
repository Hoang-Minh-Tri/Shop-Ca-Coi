package vn.MinhTri.ShopFizz.services;

import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpSession;
import vn.MinhTri.ShopFizz.domain.Cart;
import vn.MinhTri.ShopFizz.domain.CartDetail;
import vn.MinhTri.ShopFizz.domain.Order;
import vn.MinhTri.ShopFizz.domain.OrderDetail;
import vn.MinhTri.ShopFizz.domain.Product;
import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.repository.CartDetailsRepository;
import vn.MinhTri.ShopFizz.repository.CartRepository;
import vn.MinhTri.ShopFizz.repository.OrderDetailRepository;
import vn.MinhTri.ShopFizz.repository.OrderRepository;
import vn.MinhTri.ShopFizz.repository.ProductRepository;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailsRepository cartDetailsRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailsRepository cartDetailsRepository, UserService userService, OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailsRepository = cartDetailsRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
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

    public void HandleSaveProductToCart(String email, long productId, HttpSession session) {
        User user = this.userService.getUserByEmail(email);
        if (user != null) {
            Cart cart = this.cartRepository.findByUser(user);
            if (cart == null) {
                Cart newCart = new Cart();
                newCart.setUser(user);
                newCart.setSum(0);
                cart = this.cartRepository.save(newCart);
            }
            Optional<Product> product = this.productRepository.findById(productId);
            if (product.isPresent()) {
                Product productNew = product.get();
                CartDetail cartDetailOld = this.cartDetailsRepository.findByCartAndProduct(cart, productNew);
                if (cartDetailOld == null) {
                    CartDetail cartDetail = new CartDetail();
                    cartDetail.setCart(cart);
                    cartDetail.setProduct(productNew);
                    cartDetail.setPrice(productNew.getPrice());
                    cartDetail.setQuantity(1);
                    this.cartDetailsRepository.save(cartDetail);
                    int sum = cart.getSum() + 1;
                    cart.setSum(cart.getSum() + 1);

                    session.setAttribute("sum", sum);
                    this.cartRepository.save(cart);

                } else {

                    cartDetailOld.setQuantity(cartDetailOld.getQuantity() + 1);
                    this.cartDetailsRepository.save(cartDetailOld);
                }

            }

        }
    }

    public void HanhleRemoveCartDetail(long productId, HttpSession session) {
        Optional<CartDetail> cartDetailOpp = this.cartDetailsRepository.findById(productId);
        if (cartDetailOpp.isPresent()) {
            CartDetail cartDetail = cartDetailOpp.get();
            Cart cart = cartDetail.getCart();
            this.cartDetailsRepository.delete(cartDetail);
            if (cart.getSum() > 1) {
                cart.setSum(cart.getSum() - 1);
                session.setAttribute("sum", cart.getSum());
                this.cartRepository.save(cart);
            } else {
                this.cartRepository.delete(cart);
                session.setAttribute("sum", 0);
            }
        }
    }

    public Cart GetByUser(User user) {
        return this.cartRepository.findByUser(user);
    }

    public void handleUpdateCartBeforeCheckout(List<CartDetail> cartDetails) {
        for (CartDetail cartDetail : cartDetails) {
            Optional<CartDetail> cdOptional = this.cartDetailsRepository.findById(cartDetail.getId());
            if (cdOptional.isPresent()) {
                CartDetail currentCartDetail = cdOptional.get();
                currentCartDetail.setQuantity(cartDetail.getQuantity());
                this.cartDetailsRepository.save(currentCartDetail);
            }
        }
    }

    public void PlaceOrder(User user, HttpSession session, String receiverName, String receiverAddress,
            String receiverPhone) {

        // Lưu order
        Order order = new Order();
        order.setUser(user);
        order.setReceiverAddress(receiverAddress);
        order.setReceiverName(receiverName);
        order.setReceiverPhone(receiverPhone);
        order = this.orderRepository.save(order);

        Cart cart = this.cartRepository.findByUser(user);
        List<CartDetail> cartDetails;
        if (cart != null) {
            cartDetails = cart.getCartDetails();
            if (cartDetails != null) {
                for (CartDetail cd : cartDetails) {
                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProduct(cd.getProduct());
                    orderDetail.setPrice(cd.getPrice());
                    orderDetail.setQuantity(cd.getQuantity());
                    this.orderDetailRepository.save(orderDetail);
                }

                // Xóa cartdetail
                for (CartDetail cd : cartDetails) {
                    this.cartDetailsRepository.deleteById(cd.getId());
                }

                // Xóa giỏ hàng
                this.cartRepository.deleteById(cart.getId());

                // Đưa sum về 0
                session.setAttribute("sum", 0);
            }
        }
    }

}
