package vn.MinhTri.ShopFizz.controller.seller;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.MinhTri.ShopFizz.domain.Order;
import vn.MinhTri.ShopFizz.domain.OrderDetail;
import vn.MinhTri.ShopFizz.domain.OrderDetail_;
import vn.MinhTri.ShopFizz.domain.Order_;
import vn.MinhTri.ShopFizz.domain.Product_;
import vn.MinhTri.ShopFizz.domain.User;
import vn.MinhTri.ShopFizz.repository.OrderDetailRepository;
import vn.MinhTri.ShopFizz.repository.OrderRepository;
import vn.MinhTri.ShopFizz.services.OrderSevice;
import vn.MinhTri.ShopFizz.services.UserService;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestParam;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpSession;

import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class OrderSellerController {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;
    private final OrderSevice orderSevice;
    private final UserService userService;

    public OrderSellerController(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository,
            OrderSevice orderSevice, UserService userService) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
        this.orderSevice = orderSevice;
        this.userService = userService;
    }

    @GetMapping("/seller/order")
    public String getOrderPage(Model model, @RequestParam(value = "page", defaultValue = "1") int page,
            HttpServletRequest request) {
        HttpSession session = request.getSession(false);
        long id = (long) session.getAttribute("id");
        User user = this.userService.GetUserById(id);
        Pageable pageable = PageRequest.of(page - 1, 10,
                Sort.by(OrderDetail_.STATUS).ascending().and(Sort.by(OrderDetail_.ID).descending()));
        Page<OrderDetail> orders = this.orderDetailRepository.findByProductUserEmail(user.getEmail(), pageable);
        List<OrderDetail> listOrders = orders.getContent();
        model.addAttribute("nowPage", page);
        model.addAttribute("sumPage", orders.getTotalPages());
        model.addAttribute("orderDetails", listOrders);
        return "seller/order/show";
    }

    @GetMapping("/seller/order/delete/{id}")
    public String getDeleteOrderDetail(@PathVariable("id") long id, Model model) {
        Optional<OrderDetail> newOrder = this.orderDetailRepository.findById(id);
        if (newOrder.isPresent()) {
            model.addAttribute("id", id);
            model.addAttribute("newOrder", newOrder.get());

        } else {
            // return "trang_loi";
        }
        return "seller/order/delete";
    }

    @PostMapping("/seller/order/delete")
    public String postMethodName(@ModelAttribute("newOrder") Order order) {
        Optional<OrderDetail> realOrder = this.orderDetailRepository.findById(order.getId());
        if (realOrder.isPresent()) {
            OrderDetail orderDetail = realOrder.get();
            this.orderDetailRepository.delete(orderDetail);
        }

        return "redirect:/seller/order";
    }

    @GetMapping("/seller/order/agree/{id}")
    public String getUpdatePage(@PathVariable("id") long id) {
        Optional<OrderDetail> orderOpptional = this.orderDetailRepository.findById(id);
        if (orderOpptional.isPresent()) {
            OrderDetail orderDetail = orderOpptional.get();
            orderDetail.setStatus("Đã giao");
            this.orderDetailRepository.save(orderDetail);
        } else {
            // return trang lỗi
        }
        return "redirect:/seller/order";
    }
}
