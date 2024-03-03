package com.seng22212project.bitebliss.controllers;

import com.seng22212project.bitebliss.dtos.responses.ApiResponseDto;
import com.seng22212project.bitebliss.exceptions.UserNotFoundException;
import com.seng22212project.bitebliss.services.CartServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import com.seng22212project.bitebliss.dtos.requests.CartItemRequestDto;

@RestController
@RequestMapping("/bitebliss/user/cart")
@CrossOrigin(origins = "http://localhost:3000")
public class CartController {
    @Autowired
    private CartServiceImpl cartService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/new")
    public ResponseEntity<ApiResponseDto<?>> addToCart(@RequestBody CartItemRequestDto itemRequest, @Param("email") String email) throws UserNotFoundException {
        return cartService.addItem(itemRequest, email);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/getCartItems")
    public ResponseEntity<ApiResponseDto<?>> getAllCartItems(@Param("email") String email) throws UserNotFoundException {
        return cartService.getCartItems(email);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/delete")
    public ResponseEntity<ApiResponseDto<?>> deleteCartItemFromCart(@Param("cartItemId") long cartItemId){
        return cartService.removeCartItemFromCart(cartItemId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @PutMapping("/update")
    public ResponseEntity<ApiResponseDto<?>> updateCartItem(@RequestBody CartItemRequestDto itemRequest, @Param("email") String email) throws UserNotFoundException {
        return cartService.updateCartItemQuantity(itemRequest, email);
    }

}
