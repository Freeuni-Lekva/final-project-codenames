package com.example.codenames.dao.sqlImplementation;

import com.example.codenames.Model.Game;
import com.example.codenames.dao.GameDao;
import com.example.codenames.database.DBConnection;
import com.example.codenames.dto.GameInfoDto;
import com.example.codenames.exception.GameNotAddedException;
import com.example.codenames.exception.GameNotFoundException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Date;

public class SqlGameDao implements GameDao {
    public static final String TABLE_NAME = "game_history";
    private DBConnection dbconnection;

    public SqlGameDao(DBConnection connection) {
        this.dbconnection = connection;
    }

    @Override
    public Game addGame(GameInfoDto gameInfo) throws GameNotAddedException{
        Connection connection = dbconnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "INSERT INTO " + TABLE_NAME + " (winner, loser, black_word_selected, date_played) " +
                    "VALUES (?, ?, ?, sysdate());");
                statement.setString(1, gameInfo.getWinner());
                statement.setString(2, gameInfo.getLoser());
                statement.setString(3, "" + gameInfo.isBlackWordSelected());
        } catch (SQLException e) {
            throw new GameNotAddedException("");
        }
        return null;
    }

    @Override
    public Game getGameByID(Long gameID) throws GameNotFoundException {
        Connection connection = dbconnection.getConnection();
        try {
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM " + TABLE_NAME + " WHERE id = ?;"
            );
            statement.setLong(1, gameID);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){

                return new Game(resultSet.getLong(1), resultSet.getString(2),resultSet.getString(3),
                        resultSet.getBoolean(4), new Date(resultSet.getDate(5).getTime()));
            }
        } catch (SQLException e) {
            throw new GameNotFoundException("Game with an ID " + gameID + " was not found");
        }
        return null;
    }
}
