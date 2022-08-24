package com.example.codenames.DTO;

public class GameInfoDTO {
    private String winner;
    private String loser;
    private boolean blackWordSelected;

    public GameInfoDTO(String winner, String loser, boolean blackWordSelected) {
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
