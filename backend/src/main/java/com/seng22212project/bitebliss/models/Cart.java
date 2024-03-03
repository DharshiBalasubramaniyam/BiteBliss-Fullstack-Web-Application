package com.seng22212project.bitebliss.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="Cart")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Cart {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long cartId;
    @OneToMany(mappedBy = "cart")
    private Set<CartItem> items = new HashSet<>();
    @OneToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "user_id")
    private User user;


    public Cart(long cartId, Set<CartItem> items) {
        this.cartId = cartId;
        this.items = items;
    }

    public Cart(User user) {
        this.user = user;
    }

}
