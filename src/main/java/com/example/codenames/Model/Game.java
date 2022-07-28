package com.example.codenames.Model;

import java.util.Date;

public class Game {
    private Long gameID;
    private String winner;
    private String loser;
    private boolean blackWordSelected;
    private Date date;
    
    public Game(Long gameID, String winner, String loser, boolean blackWordSelected, Date date) {
        this.gameID = gameID;
        this.winner = winner;
        this.loser = loser;
        this.blackWordSelected = blackWordSelected;
        this.date = date;
    }

    public Long getGameID() {
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
