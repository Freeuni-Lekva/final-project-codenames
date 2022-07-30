package com.example.codenames.dao;

import com.example.codenames.Model.Game;
import com.example.codenames.dto.GameInfoDto;

public interface GameDao {
    Game addGame(GameInfoDto gameInfo);
    Game getGameByID(Long gameID);
}
