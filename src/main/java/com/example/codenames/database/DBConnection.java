package com.example.codenames.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    public static final String PASSWORD = "RameParoli#11";
    private Connection connection;

    public DBConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/codenames", "root", PASSWORD);
            Statement statement = connection.createStatement();
            statement.execute("USE codenames");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public DBConnection(String databaseName){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root", PASSWORD);
            Statement statement = connection.createStatement();
            statement.execute("USE " + databaseName);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    public void close() throws SQLException {
        connection.close();
    }
    public Connection getConnection() {
        return connection;
    }
}
