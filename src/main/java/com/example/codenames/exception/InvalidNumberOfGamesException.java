package com.example.codenames.exception;

import java.nio.BufferUnderflowException;

public class InvalidNumberOfGamesException extends RuntimeException {
    public InvalidNumberOfGamesException(String message){
        super(message + " is not a positive integer.");
    }
}
