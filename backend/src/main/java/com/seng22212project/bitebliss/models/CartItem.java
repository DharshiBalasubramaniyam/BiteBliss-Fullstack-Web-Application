package com.seng22212project.bitebliss.models;
import com.seng22212project.bitebliss.models.Product;

import jakarta.persistence.*;

public class CartItem {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    private long cartItemId;
    @ManyToOne
    @JoinColumn(name="cartId")
    private Cart cart;

    @ManyToOne
    @JoinColumn(name="productId")
    private Product product;
    private int qunatity;
    private double subTotal;

    public long getCartItemId() {
        return cartItemId;
    }

    public void setCartItemId(long cartItemId) {
        this.cartItemId = cartItemId;
    }

    public Cart getCart() {
        return cart;
    }

    public void setCart(Cart cart) {
        this.cart = cart;
    }

    public Product getProduct() {
        return product;
    }

    public void setProduct(Product product) {
        this.product = product;
    }

    public int getQunatity() {
        return qunatity;
    }

    public void setQunatity(int qunatity) {
        this.qunatity = qunatity;
    }

    public double getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(double subTotal) {
        this.subTotal = subTotal;
    }
}
