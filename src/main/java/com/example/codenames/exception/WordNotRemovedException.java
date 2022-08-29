package com.example.codenames.exception;

public class WordNotRemovedException extends RuntimeException {
    public WordNotRemovedException(String message) {
        super(message);
    }
}
