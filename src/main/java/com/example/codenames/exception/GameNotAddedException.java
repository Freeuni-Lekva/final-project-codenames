package com.example.codenames.exception;

public class GameNotAddedException extends RuntimeException {
    public GameNotAddedException(String message) {
        super("Game not added");
    }
}
