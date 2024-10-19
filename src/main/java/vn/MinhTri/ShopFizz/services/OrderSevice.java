package vn.MinhTri.ShopFizz.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.MinhTri.ShopFizz.domain.Order;

import vn.MinhTri.ShopFizz.repository.OrderRepository;

@Service
public class OrderSevice {
    private final OrderRepository orderRepository;

    public OrderSevice(OrderRepository orderRepository) {
        this.orderRepository = orderRepository;
    }

    public Page<Order> GetAllOrderPage(Pageable pageable) {
        return this.orderRepository.findAll(pageable);
    }

}
