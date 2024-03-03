package com.seng22212project.bitebliss.controllers;

import com.seng22212project.bitebliss.dtos.responses.ApiResponseDto;
import com.seng22212project.bitebliss.dtos.requests.OrderRequestDto;
import com.seng22212project.bitebliss.exceptions.UserNotFoundException;
import com.seng22212project.bitebliss.services.OrderServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bitebliss/user/order")
@CrossOrigin(origins = "http://localhost:3000")
public class OrderController {

    @Autowired
    private OrderServices orderServices;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/create")
    public ResponseEntity<ApiResponseDto<?>> createOrder(@RequestBody OrderRequestDto orderReq) throws UserNotFoundException {
        return orderServices.createOrder(orderReq);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/{orderId}")
    public ResponseEntity<ApiResponseDto<?>> cancelOrderById(@PathVariable int orderId) {
        return orderServices.cancelOrder(orderId);
    }

}
