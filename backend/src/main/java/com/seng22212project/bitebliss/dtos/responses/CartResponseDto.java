package com.seng22212project.bitebliss.dtos.responses;

import com.seng22212project.bitebliss.dtos.responses.CartItemResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CartResponseDto {

    private long cartId;
    private List<CartItemResponseDto> cartItemResponse;
    private int noOfCartItems;
    private double subtotal;

}
