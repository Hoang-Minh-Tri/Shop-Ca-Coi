package vn.MinhTri.ShopFizz.services;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.MinhTri.ShopFizz.domain.OrderDetail;
import vn.MinhTri.ShopFizz.repository.OrderDetailRepository;

@Service
public class OrderDetailService {
    private final OrderDetailRepository orderDetailRepository;

    public OrderDetailService(OrderDetailRepository orderDetailRepository) {
        this.orderDetailRepository = orderDetailRepository;
    }

    // Lấy ra các order của các khách hàng khác order đến người bán
    public Page<OrderDetail> GetMyOrder(String email, Pageable pageable) {
        return this.orderDetailRepository.findByProductUserEmail(email, pageable);
    }
}
