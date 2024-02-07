package com.seng22212project.bitebliss.dtos;

import com.seng22212project.bitebliss.models.CartItem
import java.util.HashSet;
import java.util.Set;

public class CartDto {
    private long cartId;
    private Set<CartItem> items = new HashSet<>();
    private UserDto user;

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

    public UserDto getUser() {
        return user;
    }

    public void setUser(UserDto user) {
        this.user = user;
    }
}



