package com.example.codenames.exception;

public class NotEnoughWordsException extends RuntimeException {
    public NotEnoughWordsException(String message) {
        super(message);
    }
}
