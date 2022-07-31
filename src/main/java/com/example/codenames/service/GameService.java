package com.example.codenames.service;

import com.example.codenames.Model.Game;
import com.example.codenames.exception.InvalidTeamsException;

import java.util.Date;

public interface GameService {
    Game addGame(String winner, String loser, boolean blackWordSelected) throws InvalidTeamsException;
    Game getGameByID(Long gameID);
}
