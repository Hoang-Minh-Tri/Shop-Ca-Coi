package vn.MinhTri.ShopFizz.services;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import vn.MinhTri.ShopFizz.domain.Order;
import vn.MinhTri.ShopFizz.domain.OrderDetail;
import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.repository.OrderRepository;

@Service
public class OrderSevice {
    private final OrderRepository orderRepository;
    private final OrderDetailService orderDetailService;

    public OrderSevice(OrderRepository orderRepository, OrderDetailService orderDetailService) {
        this.orderRepository = orderRepository;
        this.orderDetailService = orderDetailService;
    }

    public Page<Order> GetAllOrderPage(Pageable pageable) {
        return this.orderRepository.findAll(pageable);
    }

    public Page<Order> GetAllOrderPage(User user, Pageable pageable) {
        return this.orderRepository.findByUser(user, pageable);
    }

    // Kiểm tra xem các order mà toàn bộ đơn hàng đã được giao thì đổi sang trạng
    // thái đã hoàn thành
    public void DoiTrangThaiOrder() {
        List<Order> orders = this.orderRepository.findAll();
        boolean kt = true;
        for (Order order : orders) {
            List<OrderDetail> orderDetails = order.getOrderDetails();
            for (OrderDetail orderDetail : orderDetails) {
                if (orderDetail.getStatus().equals("Chờ xử lý")) {
                    kt = false;
                    break;
                }
            }
            if (kt)
                order.setStatus("Đã hoàn thành");
        }
    }

    public void DeleteByUser(User user) {
        List<Order> orders = this.orderRepository.findByUser(user);
        for (Order order : orders) {
            List<OrderDetail> orderDetails = this.orderDetailService.GetOrderDetailsByOrder(order);
            for (OrderDetail orderDetail : orderDetails)
                this.orderDetailService.Delete(orderDetail);
            this.orderRepository.delete(order);
        }
    }
}
