package com.example.codenames.DTO;

import com.example.codenames.model.Game;

public class UserGamesDto {

    private String userTeam;
    private Game games;
    private int userId;

    public UserGamesDto(String userTeam, Game games, int userId) {
        this.userTeam = userTeam;
        this.games = games;
        this.userId = userId;
    }

    public String getUserTeam() {
        return userTeam;
    }

    public void setUserTeam(String userTeam) {
        this.userTeam = userTeam;
    }

    public Game getGames() {
        return games;
    }

    public void setGames(Game games) {
        this.games = games;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
