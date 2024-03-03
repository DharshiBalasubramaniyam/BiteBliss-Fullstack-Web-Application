package com.seng22212project.bitebliss.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name="Cart_Item")
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CartItem {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long cartItemId;
    @ManyToOne
    @JoinColumn(name="cartId")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name="productId")
    private Products product;

    private int quantity;

    private double totalPrice;

    public CartItem(Cart cart, Products product, int quantity, double totalPrice) {
        this.cart = cart;
        this.product = product;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
    }
}
