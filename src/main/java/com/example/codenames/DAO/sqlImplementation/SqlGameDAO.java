package com.example.codenames.DAO.sqlImplementation;

import com.example.codenames.model.Game;
import com.example.codenames.DAO.GameDAO;
import com.example.codenames.database.DBConnection;
import com.example.codenames.DTO.GameInfoDTO;
import com.example.codenames.exception.GameNotAddedException;
import com.example.codenames.exception.GameNotFoundException;

import java.sql.*;
import java.util.Date;

public class SqlGameDAO implements GameDAO {
    public static final String TABLE_NAME = "game_history";
    private DBConnection dbconnection;

    public SqlGameDAO(DBConnection connection) {
        this.dbconnection = connection;
    }

    @Override
    public int addGame(GameInfoDTO gameInfo) throws GameNotAddedException{
        Connection connection = dbconnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + TABLE_NAME + " (winner, loser, black_word_selected) " +
                    "VALUES (?, ?, ?);", Statement.RETURN_GENERATED_KEYS);
            preparedStatement.setString(1, gameInfo.getWinner());
            preparedStatement.setString(2, gameInfo.getLoser());
            preparedStatement.setBoolean(3, gameInfo.isBlackWordSelected());
            preparedStatement.executeUpdate();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            int lastInsertedID = 0;
            if (resultSet.next()) {
                lastInsertedID = resultSet.getInt(1);
            }
            resultSet.close();
            return lastInsertedID;
        } catch (SQLException e) {
            throw new GameNotAddedException("");
        }
    }

    @Override
    public Game getGameByID(int gameID) throws GameNotFoundException {
        Connection connection = dbconnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM " + TABLE_NAME + " WHERE id = ?;"
            );
            statement.setInt(1, gameID);
            ResultSet resultSet = statement.executeQuery();
            Game game = null;
            if(resultSet.next()){
                game = new Game(resultSet.getInt(1), resultSet.getString(2),resultSet.getString(3),
                        resultSet.getBoolean(4), new Date(resultSet.getDate(5).getTime()));
            } else {
                resultSet.close();
                throw new GameNotFoundException("Game with an ID " + gameID + " was not found");
            }
            resultSet.close();
            return game;
        } catch (SQLException e) {
            throw new GameNotFoundException("Game with an ID " + gameID + " was not found");
        }
    }
}
