package com.example.codenames.exception;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String message) {
        super("Username or password isn't valid.");
    }
}
