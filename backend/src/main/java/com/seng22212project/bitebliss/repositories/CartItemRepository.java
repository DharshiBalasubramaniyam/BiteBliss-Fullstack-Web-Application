package com.seng22212project.bitebliss.repositories;

import com.seng22212project.bitebliss.models.Cart;
import com.seng22212project.bitebliss.models.CartItem;
import com.seng22212project.bitebliss.models.Products;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CartItemRepository extends JpaRepository<CartItem, Long> {
    List<CartItem> findByCart(Cart cart);

    CartItem findByCartAndProduct(Cart cart, Products product);

    void deleteByCart(Cart cart);
}
