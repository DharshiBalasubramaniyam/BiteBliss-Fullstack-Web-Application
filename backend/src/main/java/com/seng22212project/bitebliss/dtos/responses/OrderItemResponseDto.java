package com.seng22212project.bitebliss.dtos.responses;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItemResponseDto {
    private int orderItemId;

    private int product_id;

    private String productName;

    private String productPrice;

    private String imageUrl;

    private double totalProductPrice;

    private int productQuantity;
}
