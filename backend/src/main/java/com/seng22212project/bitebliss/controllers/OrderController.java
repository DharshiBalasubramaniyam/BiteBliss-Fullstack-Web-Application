//package com.seng22212project.bitebliss.controllers;
//
//import com.seng22212project.bitebliss.dtos.ApiResponseDto;
//import com.seng22212project.bitebliss.dtos.ApiResponseStatus;
//import com.seng22212project.bitebliss.dtos.OrderDto;
//import com.seng22212project.bitebliss.dtos.OrderRequest;
//import com.seng22212project.bitebliss.services.OrderServices;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//
//
//import java.security.Principal;
//
//@RestController
//@RequestMapping("/order")
//public class OrderController {
//
//    @Autowired
//    private OrderServices orderServices;
//
//    @PostMapping("/create")
//    public ResponseEntity<OrderDto> createOrder(@RequestBody OrderRequest orderReq, Principal principal) {
//        String username = principal.getName();
//        OrderDto order = this.orderServices.orderCreate(orderReq, username);
//        // Return the ResponseEntity with the created OrderDto
//        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(
//                ApiResponseStatus.SUCCESS.name(), "Order has been successfully created!"
//        ));
//    @DeleteMapping("/{orderId}")
//    public void ResponseEntity<ApiResponseStatus > cancelOrderById( @PathVariable int orderId){
//            this.orderServices.cancelOrder(orderId);
//            String message = "Cancellation successful: Order has been canceled.";
//            ApiResponseDto<String> apiResponseDto = new ApiResponseDto<>(
//                    ApiResponseStatus.SUCCESS.name(), message);
//
//            return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
//        }
//    }
package com.seng22212project.bitebliss.controllers;

import com.seng22212project.bitebliss.dtos.ApiResponseDto;
import com.seng22212project.bitebliss.dtos.ApiResponseStatus;
import com.seng22212project.bitebliss.dtos.OrderDto;
import com.seng22212project.bitebliss.dtos.OrderRequest;
import com.seng22212project.bitebliss.services.OrderServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
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
        return ResponseEntity.status(HttpStatus.CREATED).body(new ApiResponseDto<>(
                ApiResponseStatus.SUCCESS.name(), "Order has been successfully created!"
        ));
    }

    @DeleteMapping("/{orderId}")
    public ResponseEntity<ApiResponseDto<String>> cancelOrderById(@PathVariable int orderId) {
        this.orderServices.cancelOrder(orderId);
        String message = "Cancellation successful: Order has been canceled.";
        ApiResponseDto<String> apiResponseDto = new ApiResponseDto<>(
                ApiResponseStatus.SUCCESS.name(), message);

        return new ResponseEntity<>(apiResponseDto, HttpStatus.OK);
    }
    @GetMapping{"/{orderId}"}
    public ResponseEntity<OrderDto> findById(@PathVariable int orderId) {
        OrderDto orderDto = this.orderServices.findById(orderId);
        return new ResponseEntity<>(orderDto, HttpStatus.ACCEPTED);
    }
}
