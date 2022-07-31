package com.example.codenames.service.implementation;

import com.example.codenames.Model.Game;
import com.example.codenames.dao.GameDao;
import com.example.codenames.dto.GameInfoDto;
import com.example.codenames.exception.InvalidTeamsException;
import com.example.codenames.service.GameService;

public class GameServiceImpl implements GameService {
    private final GameDao gameDao;

    public GameServiceImpl(GameDao gameDao) {
        this.gameDao = gameDao;
    }

    @Override
    public Game addGame(String winner, String loser, boolean blackWordSelected) throws InvalidTeamsException {
        if (!((winner.equals("RED") && loser.equals("BLUE")) || (winner.equals("BLUE") && loser.equals("RED")))) {
            throw new InvalidTeamsException(winner + " " + loser);
        }
        GameInfoDto gameInfoDto = new GameInfoDto(winner, loser, blackWordSelected);
        return gameDao.addGame(gameInfoDto);
    }

    @Override
    public Game getGameByID(Long gameID) {
        return gameDao.getGameByID(gameID);
    }
}
