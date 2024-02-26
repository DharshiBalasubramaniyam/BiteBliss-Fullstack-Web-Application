package com.seng22212project.bitebliss.models;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import jakarta.persistence.*;
//import javax.persistence.CascadeType;
//import javax.persistence.Entity;
//import javax.persistence.Id;
//import javax.persistence.OneToOne;
//import javax.persistence.OneToMany;
//import javax.persistence.GeneratedValue;
//import javax.persistence.GenerationType;
@Entity
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;
    @OneToOne
    private User userId;
    private String orderStatus;
    private String paymentStatus;
    private String addressLine1;
    private String addressLine2;
    private String city;
    private String district;
    private int phoneNo;
    private int orderAmt;

    @OneToMany(mappedBy="order",cascade=CascadeType.ALL)
    private Set<OrderItem> orderItem =new HashSet<>();
//cons

    public Order(int orderId,
                 User userId,
                 String orderStatus,
                 String paymentStatus,
                 String addressLine1,
                 String addressLine2,
                 String city,
                 String district,
                 int phoneNo,
                 int orderAmt,
                 Set<OrderItem> orderItem) {
        this.orderId = orderId;
        this.userId = userId;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.district = district;
        this.phoneNo = phoneNo;
        this.orderItem = orderItem;
        this.orderAmt= orderAmt;
    }

    public Order() {
        super();
    }

    //getters && setters
    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public User getUserId() {
        return userId;
    }

    public void setUserId(User userId) {
        this.userId = userId;
    }

    public String getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(String orderStatus) {
        this.orderStatus = orderStatus;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getAddressLine1() {
        return addressLine1;
    }

    public void setAddressLine1(String addressLine1) {
        this.addressLine1 = addressLine1;
    }

    public String getAddressLine2() {
        return addressLine2;
    }

    public void setAddressLine2(String addressLine2) {
        this.addressLine2 = addressLine2;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public int getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(int phoneNo) {
        this.phoneNo = phoneNo;
    }

    public Set<OrderItem> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Set<OrderItem> orderItem) {
        this.orderItem = orderItem;
    }

    public int getOrderAmt() {
        return orderAmt;
    }

    public void setOrderAmt(int orderAmt) {
        this.orderAmt = orderAmt;
    }
}
