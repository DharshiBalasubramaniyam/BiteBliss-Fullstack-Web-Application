package com.seng22212project.bitebliss.services;

import com.seng22212project.bitebliss.dtos.ApiResponseDto;
import com.seng22212project.bitebliss.dtos.SignUpRequestDto;
import com.seng22212project.bitebliss.exceptions.UserAlreadyExistsException;
import com.seng22212project.bitebliss.exceptions.UserNotFoundException;
import com.seng22212project.bitebliss.exceptions.UserServiceLogicException;
import com.seng22212project.bitebliss.exceptions.UserVerificationFailedException;
import com.seng22212project.bitebliss.models.User;
import jakarta.mail.MessagingException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;

@Service
public interface UserService {

    boolean existsByUsername(String username);

    boolean existsByEmail(String Email);

    ResponseEntity<ApiResponseDto<?>> save(SignUpRequestDto signUpRequestDto) throws MessagingException, UnsupportedEncodingException, UserAlreadyExistsException, UserServiceLogicException;

    ResponseEntity<ApiResponseDto<?>> verifyVerificationCode(String code) throws UserVerificationFailedException;

    User findByEmail(String email) throws UserNotFoundException;

    ResponseEntity<ApiResponseDto<?>> resendVerificationCode(String email) throws MessagingException, UnsupportedEncodingException, UserNotFoundException, UserServiceLogicException;
}
