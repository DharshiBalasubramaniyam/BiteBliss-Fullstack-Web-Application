package com.seng22212project.bitebliss.handlers;

import com.seng22212project.bitebliss.exceptions.CategoryNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
    public class CategoryServiceGlobalExceptionHandler {
        @ExceptionHandler(CategoryNotFoundException.class)
        public ResponseEntity<String> handleCategoryNotFoundException(CategoryNotFoundException ex) {
            return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
        }
    }
