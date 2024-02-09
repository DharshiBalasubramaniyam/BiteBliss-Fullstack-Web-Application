package com.seng22212project.bitebliss.Exception;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(ResourceNotFoundException.class)
    public String HandleResourseNotFoundException(ResourceNotFoundException exception){
        return exception.getMessage();
    }

}
