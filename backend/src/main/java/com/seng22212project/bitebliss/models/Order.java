package com.seng22212project.bitebliss.models;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "online_order")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Order {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int orderId;

    private long user;

    private String firstName;

    private String lastName;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String district;

    private String phoneNo;

    private double orderAmt;

    private LocalDateTime placedOn;

    private String orderStatus;

    private String paymentStatus;

    @OneToMany(mappedBy="order",cascade=CascadeType.ALL)
    private Set<OrderItem> orderItem =new HashSet<>();


    public Order(long user, String firstName, String lastName, String addressLine1, String addressLine2, String city, String district, String phoneNo, double orderAmt, LocalDateTime placedOn, String orderStatus, String paymentStatus, Set<OrderItem> orderItem) {
        this.user = user;
        this.firstName = firstName;
        this.lastName = lastName;
        this.addressLine1 = addressLine1;
        this.addressLine2 = addressLine2;
        this.city = city;
        this.district = district;
        this.phoneNo = phoneNo;
        this.orderAmt = orderAmt;
        this.placedOn = placedOn;
        this.orderStatus = orderStatus;
        this.paymentStatus = paymentStatus;
        this.orderItem = orderItem;
    }
}
