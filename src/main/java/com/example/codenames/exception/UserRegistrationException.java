package com.example.codenames.exception;

public class UserRegistrationException extends RuntimeException{
    public UserRegistrationException(String message) {
        super("Can not register user | " + message);
    }
}
