package com.seng22212project.bitebliss.dtos;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartItemResponseDto {
    private long cartItemId;

    private int product_id;

    private String productName;

    private String price;

    private String imageUrl;

    private int quantity;

    private double totalPrice;
}
