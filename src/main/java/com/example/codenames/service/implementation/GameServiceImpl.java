package com.example.codenames.service.implementation;

import com.example.codenames.Model.Game;
import com.example.codenames.dao.GameDao;
import com.example.codenames.dto.GameInfoDto;
import com.example.codenames.service.GameService;

import java.util.Date;

public class GameServiceImpl implements GameService {
    private final GameDao gameDao;

    public GameServiceImpl(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    @Override
    public Game addGame(String winner, String loser, boolean blackWordSelected, Date date) {
        GameInfoDto gameInfoDto = new GameInfoDto(winner, loser, blackWordSelected);
        return gameDao.addGame(gameInfoDto);
    }

    @Override
    public Game getGameByID(Long gameID) {
        return gameDao.getGameByID(gameID);
    }
}
