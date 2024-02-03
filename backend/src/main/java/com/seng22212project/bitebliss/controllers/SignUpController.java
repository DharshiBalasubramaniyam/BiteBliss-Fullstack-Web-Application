package com.seng22212project.bitebliss.controllers;

import com.seng22212project.bitebliss.dtos.SignUpRequestDto;
import com.seng22212project.bitebliss.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.repository.query.Param;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/bitebliss/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class SignUpController {
    @Autowired
    private UserService userService;
    @PostMapping("/signup")
    public ResponseEntity<String> registerUser(@RequestBody SignUpRequestDto signUpRequestDto) {
        try {
            return userService.save(signUpRequestDto);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration Failed: Something went wrong!");
        }
    }

    @GetMapping("/signup/verify")
    public ResponseEntity<String> verifyUserRegistration(@Param("code") String code) {
        if (userService.verifyVerificationCode(code)) {
            return ResponseEntity.ok("User verified successfully!");
        } else {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Verification failed: Verification code is invalid or expired!");
        }
    }

    @GetMapping("/signup/resend")
    public ResponseEntity<String> resendVerificationCode(@Param("email") String email) {
        try {
            return userService.resendVerificationCode(email);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Registration Failed: Something went wrong!");
        }
    }
}
