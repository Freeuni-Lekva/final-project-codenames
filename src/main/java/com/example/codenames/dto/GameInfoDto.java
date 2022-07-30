package com.example.codenames.dto;

import java.util.Date;

public class GameInfoDto {
    private String winner;
    private String loser;
    private boolean blackWordSelected;

    public GameInfoDto(String winner, String loser, boolean blackWordSelected, Date date) {
        this.winner = winner;
        this.loser = loser;
        this.blackWordSelected = blackWordSelected;
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
}
