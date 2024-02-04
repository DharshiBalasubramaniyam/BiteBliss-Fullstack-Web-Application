package com.seng22212project.bitebliss.repositories;

import com.seng22212project.bitebliss.models.Cart;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CartRepository extends JpaRepository <Cart, Long> {
    Optional<Cart> findCartByUserId(Long userId);
}
