package com.example.codenames.dao;

import com.example.codenames.Model.Game;
import com.example.codenames.dto.GameInfoDto;
import com.example.codenames.exception.GameNotAddedException;
import com.example.codenames.exception.GameNotFoundException;

public interface GameDao {
    Game addGame(GameInfoDto gameInfo) throws GameNotAddedException;
    Game getGameByID(Long gameID) throws GameNotFoundException;
}
