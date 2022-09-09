package com.example.codenames.listener;

import com.example.codenames.DAO.PlayerHistoryDao;
import com.example.codenames.DAO.UserDao;
import com.example.codenames.DAO.WordDAO;
import com.example.codenames.DAO.sqlImplementation.SqlPlayerHistoryDao;
import com.example.codenames.DAO.sqlImplementation.SqlUserDao;
import com.example.codenames.DAO.sqlImplementation.SqlWordDAO;
import com.example.codenames.DTO.UserCredentialsDto;
import com.example.codenames.database.DBConnection;
import com.example.codenames.model.User;
import com.example.codenames.database.DBConnection;
import com.example.codenames.model.Room;
import com.example.codenames.service.PlayerHistoryService;
import com.example.codenames.service.UserService;
import com.example.codenames.service.WordService;
import com.example.codenames.service.implementation.UserServiceImpl;
import com.example.codenames.service.implementation.WordServiceImpl;
import com.example.codenames.service.implementation.PlayerHistoryServiceImpl;

import javax.naming.Name;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionListener;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
        PlayerHistoryDao playerHistoryDAO = new SqlPlayerHistoryDao(dbConnection);
        PlayerHistoryService playerHistoryService = new PlayerHistoryServiceImpl(playerHistoryDAO);
        servletContext.setAttribute(NameConstants.PLAYER_HISTORY_SERVICE, playerHistoryService);
        Map<String, Room> roomMap = new ConcurrentHashMap<>();
        servletContext.setAttribute(NameConstants.ROOM_MAP, roomMap);
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