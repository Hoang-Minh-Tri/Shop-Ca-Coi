package vn.MinhTri.ShopFizz.services;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import jakarta.servlet.http.HttpSession;
import vn.MinhTri.ShopFizz.domain.Cart;
import vn.MinhTri.ShopFizz.domain.CartDetail;
import vn.MinhTri.ShopFizz.domain.Order;
import vn.MinhTri.ShopFizz.domain.OrderDetail;
import vn.MinhTri.ShopFizz.domain.Product;
import vn.MinhTri.ShopFizz.domain.ProductOrderDetail;
import vn.MinhTri.ShopFizz.domain.Review;
import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.domain.dto.ProductCrieateDTO;
import vn.MinhTri.ShopFizz.repository.CartDetailsRepository;
import vn.MinhTri.ShopFizz.repository.CartRepository;
import vn.MinhTri.ShopFizz.repository.OrderDetailRepository;
import vn.MinhTri.ShopFizz.repository.OrderRepository;
import vn.MinhTri.ShopFizz.repository.ProductOrderRepository;
import vn.MinhTri.ShopFizz.repository.ProductRepository;
import vn.MinhTri.ShopFizz.repository.ReviewRepository;
import vn.MinhTri.ShopFizz.services.Specification.ProductSpec;

@Service
public class ProductService {
    private final ProductRepository productRepository;
    private final CartRepository cartRepository;
    private final CartDetailsRepository cartDetailsRepository;
    private final UserService userService;
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final ProductOrderRepository productOrderRepository;
    private final ReviewRepository reviewRepository;

    public ProductService(ProductRepository productRepository, CartRepository cartRepository,
            CartDetailsRepository cartDetailsRepository, UserService userService, OrderRepository orderRepository,
            OrderDetailRepository orderDetailRepository, ProductOrderRepository productOrderRepository,
            ReviewRepository reviewRepository) {
        this.productRepository = productRepository;
        this.cartRepository = cartRepository;
        this.cartDetailsRepository = cartDetailsRepository;
        this.userService = userService;
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.productOrderRepository = productOrderRepository;
        this.reviewRepository = reviewRepository;
    }

    public Specification<Product> checkPrice(List<String> prices) {
        Specification<Product> Spec = (root, query, criteriaBuilder) -> criteriaBuilder.disjunction();
        for (String p : prices) {
            double min = 0;
            double max = 0;
            switch (p) {
                case "duoi-10-trieu":
                    min = 1;
                    max = 10000000;
                    break;
                case "10-15-trieu":
                    min = 10000000;
                    max = 15000000;
                    break;
                case "15-20-trieu":
                    min = 15000000;
                    max = 20000000;
                    break;
                case "tren-20-trieu":
                    min = 20000000;
                    max = 200000000;
                    break;
            }

            if (min != 0 && max != 0) {
                Specification<Product> SpecTmp = ProductSpec.PriceMinMax(min, max);
                Spec = Spec.or(SpecTmp);
            }
        }

        return Spec;

    }

    public Page<Product> GetAllProductSpec(Pageable pageable, ProductCrieateDTO productCrieateDTO) {

        Specification<Product> Spec = Specification.where(null);
        Specification<Product> specStatus = ProductSpec.checkStatus("Đã duyệt");
        Spec = Spec.and(specStatus);
        if (productCrieateDTO.getTarget() == null
                && productCrieateDTO.getFactory() == null
                && productCrieateDTO.getPrice() == null) {
            return this.productRepository.findAll(Spec, pageable);
        }
        if (productCrieateDTO.getTarget() != null && productCrieateDTO.getTarget().isPresent()) {
            Specification<Product> SpecTaget = ProductSpec.checkTaget(productCrieateDTO.getTarget().get());
            Spec = Spec.and(SpecTaget);
        }
        if (productCrieateDTO.getFactory() != null && productCrieateDTO.getFactory().isPresent()) {
            Specification<Product> SpecFactory = ProductSpec.checkFactory(productCrieateDTO.getFactory().get());
            Spec = Spec.and(SpecFactory);
        }

        if (productCrieateDTO.getPrice() != null && productCrieateDTO.getPrice().isPresent()) {
            Specification<Product> SpecPrice = this.checkPrice(productCrieateDTO.getPrice().get());
            Spec = Spec.and(SpecPrice);
        }

        return this.productRepository.findAll(Spec, pageable);

    }

    public List<Product> GetAllProduct() {
        List<Product> products = this.productRepository.findAll();
        return products;
    }

    public List<Product> GetAllProduct(User user) {
        List<Product> products = this.productRepository.findByUser(user);
        return products;
    }

    public Page<Product> GetAllProduct(User user, Pageable pageable) {
        return this.productRepository.findByUser(user, pageable);

    }

    public Page<Product> GetAllProductPage(String status, Pageable pageable) {
        return this.productRepository.findByStatus(status, pageable);
    }

    public Page<Product> GetAllProductStatus(String status, Pageable pageable) {
        return this.productRepository.findByStatus(status, pageable);
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

    public void deleteProduct(Product product) {
        this.productRepository.delete(product);
    }

    public void HandleSaveProductToCart(String email, long productId, HttpSession session) {
        User user = this.userService.getUserByEmail(email);
        Optional<Product> products = this.fetchProductById(productId);
        // Nếu như sản phẩm vừa thêm vào là của chính bản thân người dùng thì không thể
        // thêm vào
        if (products.isPresent()) {
            if (products.get().getUser() == user)
                return;
        }
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

    public void RemoveProductWithCartDetail(CartDetail cartDetail) {
        Cart cart = cartDetail.getCart();
        cart.setSum(cart.getSum() - 1);
        this.cartRepository.save(cart);
        this.cartDetailsRepository.delete(cartDetail);
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

        Cart cart = this.cartRepository.findByUser(user);
        List<CartDetail> cartDetails;
        if (cart != null) {
            cartDetails = cart.getCartDetails();
            if (cartDetails != null) {
                // Lưu order
                Order order = new Order();
                order.setUser(user);
                order.setReceiverAddress(receiverAddress);
                order.setReceiverName(receiverName);
                order.setReceiverPhone(receiverPhone);
                order.setStatus("Chờ xử lý");
                long sum = 0;
                for (CartDetail cd : cartDetails) {
                    sum += (cd.getQuantity() * cd.getPrice());
                }
                order.setTotalPrice(sum);
                order = this.orderRepository.save(order);
                for (CartDetail cd : cartDetails) {
                    // Chuyen product sang product _
                    ProductOrderDetail product_OrderDetail = new ProductOrderDetail();
                    product_OrderDetail.setName(cd.getProduct().getName());
                    product_OrderDetail.setFactory(cd.getProduct().getFactory());
                    product_OrderDetail.setPrice(cd.getProduct().getPrice());
                    product_OrderDetail.setQuantity(cd.getQuantity());
                    product_OrderDetail.setTarget(cd.getProduct().getTarget());
                    product_OrderDetail.setImages(cd.getProduct().getImage());
                    product_OrderDetail = this.productOrderRepository.save(product_OrderDetail);

                    // Cập nhật số lượng còn lại và số lượng đã bán
                    Product product = cd.getProduct();
                    product.setSold(product.getSold() + cd.getQuantity());
                    product.setQuantity(product.getQuantity() - cd.getQuantity());
                    this.productRepository.save(product);

                    OrderDetail orderDetail = new OrderDetail();
                    orderDetail.setOrder(order);
                    orderDetail.setProductOrderDetail(product_OrderDetail);
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

    public List<CartDetail> findCartDetailByProduct(Product product) {
        return this.cartDetailsRepository.findByProduct(product);
    }

    // Chuyển đổi các sản phẩm chỉ có số lượng 1 sang trạng thái chờ xử lý, yêu càu
    // người dùng phải update thêm số lượng
    public void ConverStatus() {
        List<Product> products = this.productRepository.findAll();
        if (products != null) {
            for (Product pr : products) {
                if (pr.getQuantity() == 1) {
                    pr.setStatus("Chờ xử lý");
                    this.productRepository.save(pr);
                }
            }
        }
    }

    // Lưu các đánh giá
    public void SaveReview(Review review) {
        this.reviewRepository.save(review);
    }
}
