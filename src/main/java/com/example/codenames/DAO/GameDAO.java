package com.example.codenames.DAO;

import com.example.codenames.Model.Game;
import com.example.codenames.DTO.GameInfoDTO;
import com.example.codenames.exception.GameNotAddedException;
import com.example.codenames.exception.GameNotFoundException;

public interface GameDAO {
    Game addGame(GameInfoDTO gameInfo) throws GameNotAddedException;
    Game getGameByID(Long gameID) throws GameNotFoundException;
}
