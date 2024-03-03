package com.seng22212project.bitebliss.services;

import com.seng22212project.bitebliss.dtos.responses.ApiResponseDto;
import com.seng22212project.bitebliss.enums.ApiResponseStatus;
import com.seng22212project.bitebliss.dtos.responses.CartItemResponseDto;
import com.seng22212project.bitebliss.dtos.responses.CartResponseDto;
import com.seng22212project.bitebliss.exceptions.UserNotFoundException;
import com.seng22212project.bitebliss.models.*;
import com.seng22212project.bitebliss.dtos.requests.CartItemRequestDto;
import com.seng22212project.bitebliss.repositories.*;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CartServiceImpl implements CartService{
    @Autowired
    UserService userService;
    @Autowired
    ProductRepository productRepo;
    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private CartItemRepository cartItemRepository;

    public ResponseEntity<ApiResponseDto<?>> addItem(CartItemRequestDto item, String email) throws UserNotFoundException {
        User user = userService.findByEmail(email);
        try {
            Products product = this.productRepo.findById(item.getProductId()).orElseThrow(() -> new RuntimeException("Product not found"));

            Cart cart = cartRepository.findByUser(user);

            if(cart == null) {
                cart = new Cart(user);
                cartRepository.save(cart);
            }

            CartItem cartItem = cartItemRepository.findByCartAndProduct(cart, product);
            double amount = Double.parseDouble(String.format("%.2f", Double.parseDouble(product.getPrice()) * item.getQuantity()));
            if (cartItem == null) {
                cartItem = new CartItem(cart, product, item.getQuantity(), amount);
            }else {
                cartItem.setQuantity(cartItem.getQuantity() + item.getQuantity());
                cartItem.setTotalPrice(cartItem.getTotalPrice() + amount);
            }

            cartItemRepository.save(cartItem);

            return ResponseEntity.ok(new ApiResponseDto<>(
                    ApiResponseStatus.SUCCESS.name(), "Item has been successfully added to cart!"
            ));
        }catch (Exception e) {
            log.error("Failed to add item to cart: " + e.getMessage());
            return ResponseEntity.ok(new ApiResponseDto<>(
                    ApiResponseStatus.FAILED.name(), "Something went wrong!Please try again later!"
            ));
        }
    }

    public ResponseEntity<ApiResponseDto<?>> getCartItems(String email) throws UserNotFoundException {
            User user = userService.findByEmail(email);
            Cart cart = cartRepository.findByUser(user);
            List<CartItem> cartItemList = cartItemRepository.findByCart(cart);
            List<CartItemResponseDto> cartItems = new ArrayList<>();
            int noOfCartItems = 0;
            double subtotal = 0.0;

            for(CartItem cartItem: cartItemList) {
                cartItems.add(cartItemToCartItemDto(cartItem));
                noOfCartItems+=cartItem.getQuantity();
                subtotal+=cartItem.getTotalPrice();
            }

            if (cart == null) {
                return ResponseEntity.ok(new ApiResponseDto<>(
                        ApiResponseStatus.SUCCESS.name(), new ArrayList<>()
                ));
            }
            return ResponseEntity.ok(new ApiResponseDto<>(
                    ApiResponseStatus.SUCCESS.name(),
                    new CartResponseDto(cart.getCartId(), cartItems, noOfCartItems, subtotal)
            ));
    }

    public ResponseEntity<ApiResponseDto<?>> removeCartItemFromCart(long cartItemId){
        cartItemRepository.deleteById(cartItemId);
        return ResponseEntity.ok(new ApiResponseDto<>(
                ApiResponseStatus.SUCCESS.name(), "Cart item has been successfully deleted!"
        ));
    }

    public ResponseEntity<ApiResponseDto<?>> updateCartItemQuantity(CartItemRequestDto itemRequest, String email) throws UserNotFoundException {
        return addItem(itemRequest, email);
    }

    private CartItemResponseDto cartItemToCartItemDto(CartItem cartItem) {
        return new CartItemResponseDto(
                cartItem.getCartItemId(),
                cartItem.getProduct().getProduct_id(),
                cartItem.getProduct().getProductName(),
                cartItem.getProduct().getPrice(),
                cartItem.getProduct().getImageUrl(),
                cartItem.getQuantity(),
                cartItem.getTotalPrice()
        );
    }
}
