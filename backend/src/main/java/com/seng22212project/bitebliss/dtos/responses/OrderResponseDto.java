package com.seng22212project.bitebliss.dtos.responses;

import com.seng22212project.bitebliss.dtos.responses.OrderItemResponseDto;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderResponseDto {
    private int orderId;

    private LocalDateTime placedOn;

    private String orderStatus;

    private String paymentStatus;

    private String addressLine1;

    private String addressLine2;

    private String city;

    private String district;

    private String phoneNo;

    private double orderAmt;

    private List<OrderItemResponseDto> orderItems;

}
