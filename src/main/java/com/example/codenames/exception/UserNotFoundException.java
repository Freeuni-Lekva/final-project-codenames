package com.example.codenames.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super("Could not find user with username - " + message);
    }
}
