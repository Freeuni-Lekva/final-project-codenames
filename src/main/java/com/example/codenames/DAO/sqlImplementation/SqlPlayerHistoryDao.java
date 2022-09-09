package com.example.codenames.DAO.sqlImplementation;

import com.example.codenames.DTO.UserGamesDto;
import com.example.codenames.model.Game;
import com.example.codenames.model.PlayerHistory;
import com.example.codenames.DAO.PlayerHistoryDao;
import com.example.codenames.database.DBConnection;
import com.example.codenames.DTO.PlayerHistoryDto;
import com.example.codenames.model.PlayerHistory;

import java.sql.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class SqlPlayerHistoryDao implements PlayerHistoryDao {
    public static final String PLAYER_TABLE_NAME = "player_history";
    public static final String GAME_TABLE_NAME = "game_history";
    private DBConnection dbconnection;

    public SqlPlayerHistoryDao(DBConnection connection) {
        this.dbconnection = connection;
    }
    @Override
    public List<Integer> getGames(int num, int userID) {
        List<Integer> result = new ArrayList<>();
        try {
            Connection connection = dbconnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM " + PLAYER_TABLE_NAME +
                       " JOIN " + GAME_TABLE_NAME +
                       " ON "+ PLAYER_TABLE_NAME + ".game_id = " + GAME_TABLE_NAME +".id"+
                       " WHERE user_id = " + userID +
                       " ORDER BY " + GAME_TABLE_NAME + " .date_played DESC");
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getInt(1));
                if(result.size() == num){
                    return result;
                }
            }
            return result;
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }

    @Override
    public PlayerHistory addPlayerHistoryEntry(PlayerHistoryDto data) {
        try {
            Connection connection = dbconnection.getConnection();
                    PreparedStatement statement = connection.prepareStatement(
                            "INSERT INTO " + PLAYER_TABLE_NAME + " (game_id, user_id, team) " +
                                    "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
           statement.setInt(1, data.getGameID());
           statement.setInt(2, data.getUserID());
           statement.setString(3, data.getTeam());
           statement.executeUpdate();
            return new PlayerHistory(data.getGameID(), data.getUserID(), data.getTeam());
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }


    @Override
    public List<UserGamesDto> getSortedGames(int userId) {
        Connection connection = dbconnection.getConnection();
        List<UserGamesDto> ans = new ArrayList<>();
        try {
            PreparedStatement statement = connection.prepareStatement(String.format(
                    "SELECT g1.*, p1.team FROM %s g1, %s p1 WHERE g1.id = p1.game_id AND p1.user_id = ?",
                    GAME_TABLE_NAME, PLAYER_TABLE_NAME));
            statement.setInt(1, userId);
            ResultSet resultSet = statement.executeQuery();
            while(resultSet.next()){
                Game currGame = new Game(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3), resultSet.getBoolean(4), new Date(resultSet.getDate(5).getTime()));
                UserGamesDto currUserGameDto = new UserGamesDto(resultSet.getString(6), currGame, userId);
                ans.add(currUserGameDto);
            }
            return ans;

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }


}
