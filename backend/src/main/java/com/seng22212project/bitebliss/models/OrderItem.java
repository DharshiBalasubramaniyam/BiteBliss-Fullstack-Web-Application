package com.seng22212project.bitebliss.models;
import javax.annotation.processing.Generated;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;
import javax.persistence.ManyToOne;
import com.seng22212project.bitebliss.models.*;
@Entity
public class OrderItem {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private int orderItemId;
    @OneToOne
    private Products product;
    private double totalProductPrice;
    private int productQuantity;
    @ManyToOne
    private Order order;

    //cons

    public OrderItem(int orderItemId,
                     Products product,
                     double totalProductPrice,
                     Order order,
                     int productQuantity) {
        this.orderItemId = orderItemId;
        this.product = product;
        this.totalProductPrice = totalProductPrice;
        this.order = order;
        this.productQuantity= productQuantity;
    }

    public OrderItem() {
        super();
    }

    public int getProductQuantity() {
        return productQuantity;
    }

    public void setProductQuantity(int productQuantity) {
        this.productQuantity = productQuantity;
    }

    public int getOrderItemId() {
        return orderItemId;
    }

    public void setOrderItemId(int orderItemId) {
        this.orderItemId = orderItemId;
    }

    public Products getProduct() {
        return product;
    }

    public void setProduct(Products product) {
        this.product = product;
    }

    public double getTotalProductPrice() {
        return totalProductPrice;
    }

    public void setTotalProductPrice(double totalProductPrice) {

        this.totalProductPrice = totalProductPrice;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
