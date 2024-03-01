package com.seng22212project.bitebliss.services;

import com.seng22212project.bitebliss.dtos.ApiResponseDto;
import com.seng22212project.bitebliss.exceptions.UserNotFoundException;
import com.seng22212project.bitebliss.payload.ItemRequest;
import org.springframework.http.ResponseEntity;

public interface CartService {
    ResponseEntity<ApiResponseDto<?>> addItem(ItemRequest item, String email) throws UserNotFoundException;

    ResponseEntity<ApiResponseDto<?>> getCartItems(String email) throws UserNotFoundException;

    ResponseEntity<ApiResponseDto<?>> removeCartItemFromCart(long cartItemId);

    ResponseEntity<ApiResponseDto<?>> updateCartItemQuantity(ItemRequest itemRequest, String email) throws UserNotFoundException;
}
