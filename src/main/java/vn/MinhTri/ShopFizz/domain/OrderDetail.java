package vn.MinhTri.ShopFizz.domain;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "order_detail")
public class OrderDetail {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    private long quantity;
    private double price;

    @ManyToOne
    @JoinColumn(name = "productOrderDetail_id")
    private ProductOrderDetail productOrderDetail;

    @ManyToOne
    @JoinColumn(name = "order_id")
    private Order order;

    private String status;

    public String getUserNameBuy() {
        return userNameBuy;
    }

    public void setUserNameBuy(String userNameBuy) {
        this.userNameBuy = userNameBuy;
    }

    private String userNameBuy; // Người mua

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public ProductOrderDetail getProductOrderDetail() {
        return productOrderDetail;
    }

    public void setProductOrderDetail(ProductOrderDetail productOrderDetail) {
        this.productOrderDetail = productOrderDetail;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

}
