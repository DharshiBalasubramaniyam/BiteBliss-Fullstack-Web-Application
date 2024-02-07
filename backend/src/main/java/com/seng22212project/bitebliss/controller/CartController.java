package com.seng22212project.bitebliss.controller;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.seng22212project.bitebliss.dtos.CartDto;
import com.seng22212project.bitebliss.payload.ItemRequest;
import com.seng22212project.bitebliss.services.CartService;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping
    public ResponseEntity<CartDto> addToCart(@RequestBody ItemRequest itemRequest, Principal principal){
        CartDto addItem = this.cartService.addItem(itemRequest,principal.getName());
        return new ResponseEntity<CartDto>(addItem, HttpStatus.OK);
    }
}
