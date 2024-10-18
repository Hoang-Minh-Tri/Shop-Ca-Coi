package vn.MinhTri.ShopFizz.controller.admin;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;

import vn.MinhTri.ShopFizz.domain.Order;
import vn.MinhTri.ShopFizz.domain.OrderDetail;
import vn.MinhTri.ShopFizz.repository.OrderDetailRepository;
import vn.MinhTri.ShopFizz.repository.OrderRepository;

import java.util.List;
import java.util.Optional;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@Controller
public class OrderController {
    private final OrderRepository orderRepository;
    private final OrderDetailRepository orderDetailRepository;

    public OrderController(OrderRepository orderRepository, OrderDetailRepository orderDetailRepository) {
        this.orderRepository = orderRepository;
        this.orderDetailRepository = orderDetailRepository;
    }

    @GetMapping("/admin/order")
    public String getOrderPage(Model model) {
        List<Order> orders = this.orderRepository.findAll();
        model.addAttribute("orders", orders);
        return "admin/order/show";
    }

    @GetMapping("/admin/order/delete/{id}")
    public String getMethodName(@PathVariable("id") long id, Model model) {
        Optional<Order> newOrder = this.orderRepository.findById(id);
        if (newOrder.isPresent()) {
            model.addAttribute("id", id);
            model.addAttribute("newOrder", newOrder);
        } else {
            // return "trang_loi";
        }
        return "admin/order/delete";
    }

    @PostMapping("/admin/order/delete")
    public String postMethodName(@ModelAttribute("newOrder") Order order) {
        Optional<Order> realOrder = this.orderRepository.findById(order.getId());
        List<OrderDetail> orderDetails;
        if (realOrder.isPresent()) {
            orderDetails = realOrder.get().getOrderDetails();
            for (OrderDetail cd : orderDetails) {
                this.orderDetailRepository.deleteById(cd.getId());
            }
            this.orderRepository.delete(order);
        } else {
            // return trang lỗi;
        }

        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/update/{id}")
    public String getUpdatePage(@PathVariable("id") long id, Model model) {
        Optional<Order> orderOpptional = this.orderRepository.findById(id);
        if (orderOpptional.isPresent())
            model.addAttribute("newOrder", orderOpptional.get());
        else {
            // return trang lỗi
        }
        return "admin/order/update";
    }

    @PostMapping("/admin/order/update")
    public String postOrderUpdate(@ModelAttribute("newOrder") Order order) {
        Optional<Order> orderOptional = this.orderRepository.findById(order.getId());
        if (orderOptional.isPresent()) {
            Order realOrder = orderOptional.get();
            realOrder.setStatus(order.getStatus());
            this.orderRepository.save(realOrder);
        } else {
            // Thông báo trang lỗi
        }
        return "redirect:/admin/order";
    }

    @GetMapping("/admin/order/{id}")
    public String getViewOrder(@PathVariable("id") long id, Model model) {
        Optional<Order> orderOptional = this.orderRepository.findById(id);
        if (orderOptional.isPresent()) {
            Order realOrder = orderOptional.get();
            model.addAttribute("id", id);
            model.addAttribute("order", realOrder);
        } else {
            // trang lỗi
        }
        return "admin/order/detail";
    }

}
