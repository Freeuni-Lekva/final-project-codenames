package com.example.codenames.service.implementation;

import com.example.codenames.Model.Game;
import com.example.codenames.DAO.GameDAO;
import com.example.codenames.DTO.GameInfoDTO;
import com.example.codenames.exception.InvalidTeamsException;
import com.example.codenames.service.GameService;

public class GameServiceImpl implements GameService {
    private final GameDAO gameDAO;

    public GameServiceImpl(GameDAO gameDAO) {
        this.gameDAO = gameDAO;
    }

    @Override
    public Game addGame(String winner, String loser, boolean blackWordSelected) throws InvalidTeamsException {
        if (!((winner.equals("RED") && loser.equals("BLUE")) || (winner.equals("BLUE") && loser.equals("RED")))) {
            throw new InvalidTeamsException(winner + " " + loser);
        }
        GameInfoDTO gameInfoDto = new GameInfoDTO(winner, loser, blackWordSelected);
        return gameDAO.addGame(gameInfoDto);
    }

    @Override
    public Game getGameByID(Long gameID) {
        return gameDAO.getGameByID(gameID);
    }
}
