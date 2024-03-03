package com.seng22212project.bitebliss.handlers;

import com.seng22212project.bitebliss.dtos.responses.ApiResponseDto;
import com.seng22212project.bitebliss.enums.ApiResponseStatus;
import com.seng22212project.bitebliss.exceptions.UserAlreadyExistsException;
import com.seng22212project.bitebliss.exceptions.UserNotFoundException;
import com.seng22212project.bitebliss.exceptions.UserServiceLogicException;
import com.seng22212project.bitebliss.exceptions.UserVerificationFailedException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.ArrayList;
import java.util.List;

@RestControllerAdvice
public class UserServiceExceptionHandler {

    @ExceptionHandler(value = UserNotFoundException.class)
    public ResponseEntity<ApiResponseDto<?>> UserNotFoundExceptionHandler(UserNotFoundException exception) {
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new ApiResponseDto<>(ApiResponseStatus.FAILED.name(), exception.getMessage()));
    }

    @ExceptionHandler(value = UserAlreadyExistsException.class)
    public ResponseEntity<ApiResponseDto<?>> UserAlreadyExistsExceptionHandler(UserAlreadyExistsException exception) {
        return ResponseEntity.status(HttpStatus.CONFLICT).body(new ApiResponseDto<>(ApiResponseStatus.FAILED.name(), exception.getMessage()));
    }

    @ExceptionHandler(value = UserServiceLogicException.class)
    public ResponseEntity<ApiResponseDto<?>> UserServiceLogicExceptionHandler(UserServiceLogicException exception) {
        return ResponseEntity.badRequest().body(new ApiResponseDto<>(ApiResponseStatus.FAILED.name(), exception.getMessage()));
    }

    @ExceptionHandler(value = UserVerificationFailedException.class)
    public ResponseEntity<ApiResponseDto<?>> UserVerificationFailedExceptionHandler(UserVerificationFailedException exception) {
        return ResponseEntity.badRequest().body(new ApiResponseDto<>(ApiResponseStatus.FAILED.name(), exception.getMessage()));
    }

    @ExceptionHandler(value = MethodArgumentNotValidException.class)
    public ResponseEntity<ApiResponseDto<?>> MethodArgumentNotValidExceptionHandler(MethodArgumentNotValidException exception) {

        List<String> errorMessage = new ArrayList<>();

        exception.getBindingResult().getFieldErrors().forEach(error -> {
            errorMessage.add(error.getDefaultMessage());
        });
        return ResponseEntity.badRequest().body(new ApiResponseDto<>(ApiResponseStatus.FAILED.name(), errorMessage.toString()));
    }


}
