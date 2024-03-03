package com.seng22212project.bitebliss.services;

import com.seng22212project.bitebliss.dtos.responses.ApiResponseDto;
import com.seng22212project.bitebliss.exceptions.UserNotFoundException;
import com.seng22212project.bitebliss.dtos.requests.CartItemRequestDto;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<ApiResponseDto<?>> addItem(CartItemRequestDto item, String email) throws UserNotFoundException;

    ResponseEntity<ApiResponseDto<?>> getCartItems(String email) throws UserNotFoundException;

    ResponseEntity<ApiResponseDto<?>> removeCartItemFromCart(long cartItemId);

    ResponseEntity<ApiResponseDto<?>> updateCartItemQuantity(CartItemRequestDto itemRequest, String email) throws UserNotFoundException;
}
