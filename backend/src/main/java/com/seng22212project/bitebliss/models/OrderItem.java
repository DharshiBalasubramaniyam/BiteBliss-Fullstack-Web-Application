package com.seng22212project.bitebliss.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    @Id
    @GeneratedValue(strategy= GenerationType.IDENTITY)
    private int orderItemId;

    private int product;
    private double totalProductPrice;
    private int productQuantity;
    @ManyToOne
    private Order order;

}
