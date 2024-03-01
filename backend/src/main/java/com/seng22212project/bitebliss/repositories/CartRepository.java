package com.seng22212project.bitebliss.repositories;

import com.seng22212project.bitebliss.models.Cart;
import com.seng22212project.bitebliss.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CartRepository extends JpaRepository <Cart, Long> {
    Cart findByUser(User user);
}
