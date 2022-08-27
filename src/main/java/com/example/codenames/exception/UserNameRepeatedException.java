package com.example.codenames.exception;

public class UserNameRepeatedException extends UserRegistrationException {
    public UserNameRepeatedException(String message) {
        super("UserName: " + message + " already used!");
    }
}
