package com.seng22212project.bitebliss.controllers;

import com.seng22212project.bitebliss.dtos.responses.ApiResponseDto;
import com.seng22212project.bitebliss.dtos.requests.ResetPasswordRequestDto;
import com.seng22212project.bitebliss.exceptions.UserNotFoundException;
import com.seng22212project.bitebliss.exceptions.UserServiceLogicException;
import com.seng22212project.bitebliss.exceptions.UserVerificationFailedException;
import com.seng22212project.bitebliss.services.UserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bitebliss/auth/forgotpassword")
@CrossOrigin(origins = "http://localhost:3000")
public class ForgotPasswordController {
    @Autowired
    private UserService userService;

    @GetMapping("/verifyEmail")
    public ResponseEntity<ApiResponseDto<?>> verifyEmail(@Param("email") String email)
            throws UserNotFoundException, UserServiceLogicException {
        return userService.verifyEmailAndSendForgotPasswordVerificationEmail(email);
    }

    @GetMapping("/verifyCode")
    public ResponseEntity<ApiResponseDto<?>> verifyCode(@Param("code") String code)
            throws UserVerificationFailedException, UserServiceLogicException {
        return userService.verifyForgotPasswordVerification(code);
    }

    @PostMapping("/resetPassword")
    public ResponseEntity<ApiResponseDto<?>> resetPassword(@RequestBody @Valid ResetPasswordRequestDto resetPasswordDto)
            throws UserNotFoundException, UserServiceLogicException {
        return userService.resetPassword(resetPasswordDto);
    }

    @GetMapping("/resendEmail")
    public ResponseEntity<ApiResponseDto<?>> resendEmail(@Param("email") String email)
            throws UserNotFoundException, UserServiceLogicException {
        return userService.verifyEmailAndSendForgotPasswordVerificationEmail(email);
    }
}
