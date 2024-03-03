package com.seng22212project.bitebliss.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.seng22212project.bitebliss.models.Order;

import java.util.List;
import java.util.Optional;

public interface OrderRepository extends JpaRepository<Order,Integer>  {
    Optional<Order> findById(int orderId);

    List<Order> findByUserOrderByOrderIdDesc(long user);


}
