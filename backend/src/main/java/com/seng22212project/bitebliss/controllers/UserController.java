package com.seng22212project.bitebliss.controllers;

import com.seng22212project.bitebliss.dtos.responses.ApiResponseDto;
import com.seng22212project.bitebliss.dtos.requests.ResetPasswordRequestDto;
import com.seng22212project.bitebliss.exceptions.UserNotFoundException;
import com.seng22212project.bitebliss.exceptions.UserServiceLogicException;
import com.seng22212project.bitebliss.services.OrderServices;
import com.seng22212project.bitebliss.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bitebliss/user")
@CrossOrigin(origins = "http://localhost:3000")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private OrderServices orderService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @PostMapping("/changePassword")
    public ResponseEntity<ApiResponseDto<?>> changePassword(@RequestBody @Valid ResetPasswordRequestDto resetPasswordRequestDto)
            throws UserNotFoundException, UserServiceLogicException {
        return userService.resetPassword(resetPasswordRequestDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/orders")
    public ResponseEntity<ApiResponseDto<?>> getOrdersByUser(@Param("email") String email)
            throws UserNotFoundException, UserServiceLogicException {
        return orderService.getOrders(email);
    }
}
