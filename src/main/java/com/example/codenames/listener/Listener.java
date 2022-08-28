package com.example.codenames.listener;

import com.example.codenames.database.DBConnection;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import java.sql.SQLException;

public class Listener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {
    private DBConnection dbConnection;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        dbConnection = new DBConnection();

    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            dbConnection.close();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}
