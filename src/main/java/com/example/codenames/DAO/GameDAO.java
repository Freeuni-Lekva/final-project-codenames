package com.example.codenames.DAO;

import com.example.codenames.model.Game;
import com.example.codenames.DTO.GameInfoDTO;
import com.example.codenames.exception.GameNotAddedException;
import com.example.codenames.exception.GameNotFoundException;

public interface GameDAO {
    /**
     @return ID of the game added
     */
    int addGame(GameInfoDTO gameInfo) throws GameNotAddedException;
    /**
     @return Game object with the given ID
     */
    Game getGameByID(int gameID) throws GameNotFoundException;
}
