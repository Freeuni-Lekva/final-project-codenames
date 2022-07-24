package com.example.codenames.dao.sqlImplementation;

import com.example.codenames.Model.Role;
import com.example.codenames.Model.User;
import com.example.codenames.dao.UserDao;
import com.example.codenames.database.DBConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Stack;

public class SqlUserDao implements UserDao {
    public static final String TABLE_NAME = "users";
    private DBConnection dbconnection;

    public SqlUserDao(DBConnection connection) {
        this.dbconnection = connection;
    }

    @Override
    public User registerUser(User user) {
        return null;
    }

    @Override
    public User getByUserName(String username) {
        try {
            Connection connection = dbconnection.getConnection();
            PreparedStatement statement = connection.prepareStatement(
                    "SELECT * FROM " + TABLE_NAME + "WHERE user_name = ?; ");
            statement.setString(1, username);
            ResultSet resultSet = statement.executeQuery();
            if(resultSet.next()){

                return new User(resultSet.getLong(1), resultSet.getString(2),resultSet.getString(3),
                        resultSet.getLong(4), resultSet.getLong(5), resultSet.getLong(6),
                        resultSet.getDouble(7), resultSet.getLong(8), resultSet.getDate(9),
                        Role.valueOf(resultSet.getString(10)));
            }
        }
        catch (Exception e){
            throw new RuntimeException();
        }
        return null;
    }
}
