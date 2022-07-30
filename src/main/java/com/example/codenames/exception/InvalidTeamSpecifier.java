package com.example.codenames.exception;

public class InvalidTeamSpecifier extends RuntimeException {
    public InvalidTeamSpecifier(String message){
        super("Team named " + message +" doesn't exist");
    }
}
