package com.seng22212project.bitebliss.controllers;

import com.seng22212project.bitebliss.dtos.OrderDto;
import com.seng22212project.bitebliss.dtos.OrderRequest;
import com.seng22212project.bitebliss.services.OrderServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/order")
public class OrderController {

    @Autowired
    private OrderServices orderServices;

    @PostMapping("/create")
    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest orderReq, Principal principal) {
        String username = principal.getName();
        OrderDto order = this.orderServices.orderCreate(orderReq, username);
        // Return the ResponseEntity with the created OrderDto
        return new ResponseEntity<>(order, HttpStatus.CREATED);}
}
