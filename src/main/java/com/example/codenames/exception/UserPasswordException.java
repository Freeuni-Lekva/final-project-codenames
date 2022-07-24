package com.example.codenames.exception;

public class UserPasswordException extends UserRegistrationException{
    public UserPasswordException(String message) {
        super("Minimum length of password should be: 8");
    }
}
