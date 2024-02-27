package com.seng22212project.bitebliss.repositories;
import org.springframework.data.jpa.repository.JpaRepository;
import com.seng22212project.bitebliss.models.Order;

import java.util.Optional;
import com.seng22212project.bitebliss.dtos.*;
import com.seng22212project.bitebliss.models.*;
import com.seng22212project.bitebliss.repositories.*;

public interface OrderRepository extends JpaRepository<Order,Integer>  {
Optional<Order>findById(int orderId);

}
