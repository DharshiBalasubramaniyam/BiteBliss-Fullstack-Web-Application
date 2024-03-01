package com.seng22212project.bitebliss.controllers;

import com.seng22212project.bitebliss.dtos.ApiResponseDto;
import com.seng22212project.bitebliss.exceptions.UserNotFoundException;
import com.seng22212project.bitebliss.services.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.seng22212project.bitebliss.payload.ItemRequest;

@RestController
@RequestMapping("/bitebliss/user/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
    @Autowired
    private CartServiceImpl cartService;

    @PostMapping("/new")
    public ResponseEntity<ApiResponseDto<?>> addToCart(@RequestBody ItemRequest itemRequest, @Param("email") String email) throws UserNotFoundException {
        return cartService.addItem(itemRequest, email);
    }

    @GetMapping("/getCartItems")
    public ResponseEntity<ApiResponseDto<?>> getAllCartItems(@Param("email") String email) throws UserNotFoundException {
        return cartService.getCartItems(email);
    }

    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponseDto<?>> deleteCartItemFromCart(@Param("cartItemId") long cartItemId){
        return cartService.removeCartItemFromCart(cartItemId);
    }

    @PutMapping("/update")
    public ResponseEntity<ApiResponseDto<?>> updateCartItem(@RequestBody ItemRequest itemRequest, @Param("email") String email) throws UserNotFoundException {
        return cartService.updateCartItemQuantity(itemRequest, email);
    }

}
