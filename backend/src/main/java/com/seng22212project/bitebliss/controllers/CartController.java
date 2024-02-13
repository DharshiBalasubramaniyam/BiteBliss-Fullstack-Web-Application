package com.seng22212project.bitebliss.controllers;

import java.security.Principal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.seng22212project.bitebliss.service.CartService;

import com.seng22212project.bitebliss.dtos.CartDto;
import com.seng22212project.bitebliss.payload.ItemRequest;

@RestController
@RequestMapping("/cart")
public class CartController {
    @Autowired
    private CartService cartService;

    @PostMapping("/")
    public ResponseEntity<CartDto> addToCart(@RequestBody ItemRequest itemRequest, Principal principal){
        CartDto addItem = this.cartService.addItem(itemRequest,principal.getName());
        return new ResponseEntity<CartDto>(addItem, HttpStatus.OK);
    }

    @GetMapping("/")
    public ResponseEntity<CartDto> getAllCartItems(Principal principal){
        CartDto getCartItems = this.cartService.getCartItems(principal.getName());
        return new ResponseEntity<CartDto>(getCartItems, HttpStatus.ACCEPTED);
    }

//@GetMapping
//public ResponseEntity<CartDto> getCartById(@PathVariable long cartId){
//    CartDto cartById = this.cartService.getCartById(cartId);
//    return new ResponseEntity<CartDto>(cartById, HttpStatus.OK);
//}

    @DeleteMapping
    public ResponseEntity<CartDto> deleteCartItemFromCart(@PathVariable int productId,Principal principal){
        CartDto remove = this.cartService.removeCartItemFromCart(principal.getName() ,productId);
        return new ResponseEntity<CartDto>(remove, HttpStatus.UPGRADE_REQUIRED);
    }

    @PutMapping
    public ResponseEntity<CartDto> updateCartItem(@RequestBody ItemRequest itemRequest, Principal principal) {
        CartDto updatedItem = this.cartService.updateCartItem(itemRequest, principal.getName());
        return new ResponseEntity<>(updatedItem, HttpStatus.OK);
    }
}
