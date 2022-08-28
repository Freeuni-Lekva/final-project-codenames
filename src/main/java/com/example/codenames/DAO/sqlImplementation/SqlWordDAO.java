package com.example.codenames.DAO.sqlImplementation;

import com.example.codenames.DAO.WordDAO;
import com.example.codenames.database.DBConnection;
import com.example.codenames.exception.WordNotAddedException;
import com.example.codenames.exception.WordNotFoundException;
import com.example.codenames.exception.WordNotRemovedException;
import com.example.codenames.model.Word;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class SqlWordDAO implements WordDAO {

    public static final String TABLE_NAME = "words";
    private DBConnection dbconnection;

    public SqlWordDAO(DBConnection dbConnection) {
        this.dbconnection = dbConnection;
    }

    @Override
    public boolean addWord(String name, String category) {
        Connection connection = dbconnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "INSERT INTO " + TABLE_NAME + " (word, category) " +
                    "VALUES (?, ?);");
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, category);
            if (preparedStatement.executeUpdate() == 1) {
                return true;
            } else return false;
        } catch (SQLException e) {
            throw new WordNotAddedException("Word " + name + " was not added in category " + category);
        }
    }

    @Override
    public boolean removeWord(String name) {
        Connection connection = dbconnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM " + TABLE_NAME + " WHERE word = ?;"
            );
            preparedStatement.setString(1, name);
            return preparedStatement.executeUpdate() == 1;
        } catch (SQLException e) {
            throw new WordNotRemovedException("Word " + name + " was not removed");
        }
    }

    @Override
    public boolean removeWordFromCategory(String name, String category) {
        Connection connection = dbconnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM " + TABLE_NAME + " WHERE word = ? and category = ?;"
            );
            preparedStatement.setString(1, name);
            preparedStatement.setString(2, category);
            if (preparedStatement.executeUpdate() == 1) return true;
            return true;
        } catch (SQLException e) {            throw new WordNotRemovedException("Word " + name + " was not removed");
        }
    }

    @Override
    public List<String> getWordsFromCategory(String category) {
        Connection connection = dbconnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM " + TABLE_NAME + " WHERE category = ?;",
                    Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, category);
            if (preparedStatement.executeUpdate() != 1) return new ArrayList<>();
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            List<String> toReturn = new ArrayList<>();
            while (resultSet.next()) {
                toReturn.add(resultSet.getString(1));
            }
            return toReturn;
        } catch (SQLException e) {
            throw new WordNotFoundException("Words from category " + category + " not found");
        }
    }

    @Override
    public Word getWordByName(String name) {
        Connection connection = dbconnection.getConnection();
        try {
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "SELECT * FROM " + TABLE_NAME + " WHERE word = ?;",
                    Statement.RETURN_GENERATED_KEYS
            );
            preparedStatement.setString(1, name);
            if (preparedStatement.executeUpdate() != 1) return null;
            ResultSet resultSet = preparedStatement.getGeneratedKeys();
            List<String> categories = new ArrayList<>();
            while (resultSet.next()) {
                categories.add(resultSet.getString(2));
            }
            return new Word(name, categories);
        } catch (SQLException e) {
            throw new WordNotFoundException("Word " + name + " not found");
        }
    }
}
