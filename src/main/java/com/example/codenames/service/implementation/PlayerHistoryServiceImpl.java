package com.example.codenames.service.implementation;

import com.example.codenames.DAO.PlayerHistoryDao;
import com.example.codenames.DAO.UserDao;
import com.example.codenames.DAO.sqlImplementation.SqlUserDao;
import com.example.codenames.DTO.PlayerHistoryDto;
import com.example.codenames.DTO.UserGamesDto;
import com.example.codenames.exception.GameNotFoundException;
import com.example.codenames.exception.InvalidNumberOfGamesException;
import com.example.codenames.exception.InvalidTeamSpecifier;
import com.example.codenames.model.Game;
import com.example.codenames.model.PlayerHistory;
import com.example.codenames.service.PlayerHistoryService;

import java.util.List;

public class PlayerHistoryServiceImpl implements PlayerHistoryService {
    private final PlayerHistoryDao playerHistoryDao;

    public PlayerHistoryServiceImpl(PlayerHistoryDao playerHistoryDao) {
        this.playerHistoryDao = playerHistoryDao;
    }

    @Override
    public PlayerHistory addPlayerHistoryEntry(int gameID, int userID, String team) throws InvalidTeamSpecifier {
        if(!team.equals("RED") && !team.equals("BLUE")){
            throw new InvalidTeamSpecifier(team);
        }
        PlayerHistoryDto data = new PlayerHistoryDto(gameID, userID, team);
        PlayerHistory playerHistory = playerHistoryDao.addPlayerHistoryEntry(data);
        return playerHistory;
    }

    @Override
    public List<Integer> getGames(int num, int userID) throws InvalidNumberOfGamesException {
        if(num <= 0){
            throw new InvalidNumberOfGamesException(String.valueOf(num));
        }
        List<Integer> result = playerHistoryDao.getGames(num, userID);
        return result;
    }

    @Override
    public List<UserGamesDto> getSortedGames(int userID) {
        List<UserGamesDto> result = playerHistoryDao.getSortedGames(userID);
        if(result == null){
            throw  new GameNotFoundException("No games found for this userID");
        }
        else{
            return result;
        }
    }
}
