package com.example.codenames.service;

import com.example.codenames.model.Game;
import com.example.codenames.exception.InvalidTeamsException;

public interface GameService {
    /**
     @return ID of the game added
     */
    int addGame(String winner, String loser, boolean blackWordSelected) throws InvalidTeamsException;
    /**
     @return Game object with the given ID
     */
    Game getGameByID(int gameID);
}
