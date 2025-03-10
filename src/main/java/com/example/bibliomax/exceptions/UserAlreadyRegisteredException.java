package com.example.bibliomax.exceptions;

public class UserAlreadyRegisteredException extends RuntimeException{

    public UserAlreadyRegisteredException(String message) {
        super(message);
    }
}
