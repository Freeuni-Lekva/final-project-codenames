package com.example.codenames.listener;

import com.example.codenames.DAO.UserDao;
import com.example.codenames.DAO.WordDAO;
import com.example.codenames.DAO.sqlImplementation.SqlUserDao;
import com.example.codenames.DAO.sqlImplementation.SqlWordDAO;
import com.example.codenames.DTO.UserCredentialsDto;
import com.example.codenames.database.DBConnection;
import com.example.codenames.model.User;
import com.example.codenames.database.DBConnection;
import com.example.codenames.service.UserService;
import com.example.codenames.service.WordService;
import com.example.codenames.service.implementation.UserServiceImpl;
import com.example.codenames.service.implementation.WordServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import java.sql.SQLException;

@WebListener
public class Listener implements ServletContextListener, HttpSessionListener, HttpSessionAttributeListener {
    private DBConnection dbConnection;

    public Listener(){

    }
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        dbConnection = new DBConnection();
        ServletContext servletContext = sce.getServletContext();
        UserDao userDao = new SqlUserDao(dbConnection);
        UserService userService = new UserServiceImpl(userDao);
        servletContext.setAttribute(NameConstants.USER_SERVICE, userService);
        WordDAO wordDAO = new SqlWordDAO(dbConnection);
        WordService wordService = new WordServiceImpl(wordDAO);
        servletContext.setAttribute(NameConstants.WORD_SERVICE, wordService);


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