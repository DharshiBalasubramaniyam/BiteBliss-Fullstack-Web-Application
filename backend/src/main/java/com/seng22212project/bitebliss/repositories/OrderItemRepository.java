package com.seng22212project.bitebliss.repositories;

import com.seng22212project.bitebliss.models.Order;
import com.seng22212project.bitebliss.models.OrderItem;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrderItemRepository extends JpaRepository<OrderItem, Long> {
    List<OrderItem> findByOrder(Order order);
}
