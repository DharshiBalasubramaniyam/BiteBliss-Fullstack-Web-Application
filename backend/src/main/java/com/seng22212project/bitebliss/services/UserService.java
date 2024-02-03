package com.seng22212project.bitebliss.services;

import com.seng22212project.bitebliss.dtos.SignUpRequestDto;
import com.seng22212project.bitebliss.models.User;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface UserService {

    boolean existsByUsername(String username);

    boolean existsByEmail(String Email);

    ResponseEntity<String> save(SignUpRequestDto signUpRequestDto) throws MessagingException, UnsupportedEncodingException;

    boolean verifyVerificationCode(String code);

    User findByEmail(String email);

    ResponseEntity<String> resendVerificationCode(String email) throws MessagingException, UnsupportedEncodingException;
}
