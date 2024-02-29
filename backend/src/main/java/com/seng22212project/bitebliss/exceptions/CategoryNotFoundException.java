package com.seng22212project.bitebliss.exceptions;

public class CategoryNotFoundException extends RuntimeException{
    public CategoryNotFoundException(){
        super();
    }

    public CategoryNotFoundException(String message){
        super(message);
    }
}
