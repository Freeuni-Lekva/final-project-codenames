package com.example.codenames.dao.sqlImpementation;

import com.example.codenames.Model.PlayerHistory;
import com.example.codenames.dao.PlayerHistoryDao;
import com.example.codenames.database.DBConnection;
import com.example.codenames.dto.PlayerHistoryDto;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

public class SqlPlayerHistoryDao implements PlayerHistoryDao {
    public static final String TABLE_NAME = "player_history";
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
                    "SELECT * FROM " + TABLE_NAME + " WHERE user_id = ? ;");
            statement.setInt(1, userID);
            ResultSet resultSet = statement.executeQuery();
            while (resultSet.next()) {
                result.add(resultSet.getInt(1));
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
                    "INSERT INTO " + TABLE_NAME + " (game_id, user_id, team) " +
                            "VALUES (?, ?, ?);");
            statement.setInt(1, data.getGameID());
            statement.setInt(2, data.getUserID());
            statement.setString(3, data.getTeam());
            return new PlayerHistory(data.getGameID(), data.getUserID(), data.getTeam());
        }
        catch (Exception e){
            throw new RuntimeException();
        }
    }

}
