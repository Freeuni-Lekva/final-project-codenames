package com.example.codenames.exception;

public class InvalidTeamsException extends RuntimeException {
    public InvalidTeamsException(String message) {
        super("Team colors are invalid, teams entered: " + message);
    }
}
