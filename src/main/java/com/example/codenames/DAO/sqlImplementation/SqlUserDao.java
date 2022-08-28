package com.example.codenames.DAO.sqlImplementation;


import com.example.codenames.DAO.UserDao;
import com.example.codenames.database.DBConnection;
import com.example.codenames.model.Role;
import com.example.codenames.model.User;

import java.sql.*;
import java.util.Date;

public class SqlUserDao implements UserDao {
    public static final String TABLE_NAME = "users";
    private DBConnection dbconnection;

    public SqlUserDao(DBConnection connection) {
        this.dbconnection = connection;
    }

    @Override
    public User registerUser(User user) {
        try{
            Connection connection = dbconnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    String.format("INSERT INTO %s (%s, %s, %s, %s, %s, %s, %s, %s) VALUES (?, ?, ?, ?, ?, ?, ?, ?);",
                            User.TABLE_NAME,
                            User.TABLE_USER_NAME,
                            User.TABLE_PASSWORD,
                            User.TABLE_GAMES_WON,
                            User.TABLE_GAMES_LOST,
                            User.TABLE_GAMES_PLAYED,
                            User.TABLE_WINNING_RATE,
                            User.TABLE_BLACK_WORD_SELECTED,
                            User.TABLE_STATUS), Statement.RETURN_GENERATED_KEYS);
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getHashedPassword());
            statement.setLong(3, 0L);
            statement.setLong(4, 0L);
            statement.setLong(5, 0);
            statement.setDouble(6, 0.0);
            statement.setLong(7, 0L);
            statement.setString(8, Role.PLAYER.toString());


            if(statement.executeUpdate() == 1){
                ResultSet resultSet = statement.getGeneratedKeys();
               /* if(resultSet.next()) {
                    System.out.println(resultSet.getString(2));
                    return toUser(resultSet);
                }*/
                return getByUserName(user.getUsername());
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public User getByUserName(String username) {
        try {
            Connection connection = dbconnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM " + TABLE_NAME + " WHERE user_name = ?; ");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                return toUser(resultSet);
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
    private Long gamesWon = 0L;
    private Long gamesLost = 0L;
    private Long gamesPlayed = 0L;
    private double winningRate = 0.0;
    private Long blackWordCounter = 0L;
    private Date registrationDate;
    private Role role = Role.PLAYER;

    private User toUser(ResultSet resultSet) throws SQLException {
        return new User(resultSet.getInt(1), resultSet.getString(2), resultSet.getString(3),
                resultSet.getLong(4), resultSet.getLong(5), resultSet.getLong(6),
                resultSet.getDouble(7), resultSet.getLong(8), resultSet.getTimestamp(9),
                Role.valueOf(resultSet.getString(10)));
    }

    private void changeUser(ResultSet resultSet, User user) throws SQLException {
        user.setUserID(resultSet.getInt(1));
        user.setGamesWon(resultSet.getLong(4));
        user.setGamesLost(resultSet.getLong(5));
        user.setGamesPlayed(resultSet.getLong(6));
        user.setWinningRate(resultSet.getDouble(7));
        user.setBlackWordCounter(resultSet.getLong(8));
        user.setRegistrationDate(resultSet.getTimestamp(9));
        user.setRole(Role.valueOf(resultSet.getString(10)));


    }
    @Override
    public User loginUser(User user) {
        try {
            Connection connection = dbconnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM " + TABLE_NAME + " WHERE user_name = ? and hashed_password = ?; ");
            statement.setString(1, user.getUsername());
            statement.setString(2, user.getHashedPassword());
            ResultSet resultSet = statement.executeQuery();
            if (resultSet.next()){
                changeUser(resultSet, user);
                return user;
            }
        }
        catch (SQLException e){
            e.printStackTrace();
        }
        return null;
    }
}
