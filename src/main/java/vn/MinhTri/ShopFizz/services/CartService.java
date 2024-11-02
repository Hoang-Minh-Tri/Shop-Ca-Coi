package vn.MinhTri.ShopFizz.services;

import java.util.List;

import org.springframework.stereotype.Service;

import vn.MinhTri.ShopFizz.domain.Cart;
import vn.MinhTri.ShopFizz.domain.CartDetail;
import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.repository.CartDetailsRepository;
import vn.MinhTri.ShopFizz.repository.CartRepository;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final CartDetailsRepository cartDetailsRepository;

    public CartService(CartRepository cartRepository, CartDetailsRepository cartDetailsRepository) {
        this.cartRepository = cartRepository;
        this.cartDetailsRepository = cartDetailsRepository;
    }

    public void DeleteByUser(User user) {
        Cart cartOptional = this.cartRepository.findByUser(user);
        if (cartOptional != null) {
            List<CartDetail> cartDetails = cartOptional.getCartDetails();
            for (CartDetail cartDetail : cartDetails) {
                this.cartDetailsRepository.delete(cartDetail);
            }
            this.cartRepository.delete(cartOptional);
        }

    }
}
