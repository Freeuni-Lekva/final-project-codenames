package com.example.codenames.model;

public class PlayerHistory {

    private int gameID;
    private int userID;
    private String team;

    public PlayerHistory(int gameID, int userID, String team) {
        this.gameID = gameID;
        this.userID = userID;
        this.team = team;
    }

    public int getGameID() {
        return gameID;
    }

    public int getUserID() {
        return userID;
    }

    public String getTeam() {
        return team;
    }
}
