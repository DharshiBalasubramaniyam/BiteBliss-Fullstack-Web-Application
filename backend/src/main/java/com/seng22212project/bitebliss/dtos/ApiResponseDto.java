package com.seng22212project.bitebliss.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ApiResponseDto<T> {
    private String status;
    private T response;

}
