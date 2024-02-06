package com.seng22212project.bitebliss.models;

import jakarta.persistence.*;
import java.util.HashSet;
import java.util.Set;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name="Cart")
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;
    @OneToMany(mappedBy = "cart")
    private Set<CartItem> items = new HashSet<>();
    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;


    public Cart(long cartId, Set<CartItem> items) {
        this.cartId = cartId;
        this.items = items;
    }

    public Cart() {
        super();
    }

    public long getCartId() {
        return cartId;
    }

    public void setCartId(long cartId) {
        this.cartId = cartId;
    }

    public Set<CartItem> getItems() {
        return items;
    }

    public void setItems(Set<CartItem> items) {
        this.items = items;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
