package com.example.codenames.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

public class DBConnection {
    private Connection connection;

    public DBConnection(){
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/codenames", "root",  "RameParoli#11");
            Statement statement = connection.createStatement();
            statement.execute("USE codenames");
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