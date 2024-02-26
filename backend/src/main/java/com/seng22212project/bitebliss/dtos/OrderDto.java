package com.seng22212project.bitebliss.dtos;
import com.seng22212project.bitebliss.models.OrderItem;
import com.seng22212project.bitebliss.models.User;

import java.util.HashSet;
import java.util.Set;

public class OrderDto {
    private int orderId;
    private UserDto userId;
    private String orderStatus;
    private String paymentStatus;
    private String addressLine1;

    public int getOrderId() {
        return orderId;
    }

    public void setOrderId(int orderId) {
        this.orderId = orderId;
    }

    public UserDto getUserId() {
        return userId;
    }

    public void setUserId(UserDto userId) {
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

    public Set<OrderItemDto> getOrderItem() {
        return orderItem;
    }

    public void setOrderItem(Set<OrderItemDto> orderItem) {
        this.orderItem = orderItem;
    }

    private String addressLine2;
    private String city;
    private String district;
    private int phoneNo;
    private Set<OrderItemDto> orderItem =new HashSet<>();

}
