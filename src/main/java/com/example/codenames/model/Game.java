package com.example.codenames.model;

import java.util.Date;

public class Game {
    private int gameID;
    private String winner;
    private String loser;
    private boolean blackWordSelected;
    private Date date;
    
    public Game(int gameID, String winner, String loser, boolean blackWordSelected, Date date) {
        this.gameID = gameID;
        this.winner = winner;
        this.loser = loser;
        this.blackWordSelected = blackWordSelected;
        this.date = date;
    }

    public int getGameID() {
        return gameID;
    }

    public String getWinner() {
        return winner;
    }

    public String getLoser() {
        return loser;
    }

    public boolean isBlackWordSelected() {
        return blackWordSelected;
    }

    public Date getDate() {
        return date;
    }
}
