package com.example.codenames.service.test;

import com.example.codenames.DAO.GameDAO;
import com.example.codenames.DAO.PlayerHistoryDao;
import com.example.codenames.DAO.sqlImplementation.SqlPlayerHistoryDao;
import com.example.codenames.DAO.sqlImplementation.SqlGameDAO;
import com.example.codenames.model.PlayerHistory;
import com.example.codenames.database.DBConnection;
import com.example.codenames.service.GameService;
import com.example.codenames.service.PlayerHistoryService;
import com.example.codenames.service.implementation.GameServiceImpl;
import com.example.codenames.service.implementation.PlayerHistoryServiceImpl;
import junit.framework.TestCase;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

public class PlayerHistoryServiceImplTest extends TestCase {

    private PlayerHistoryService playerHistoryService;
    private GameService gameService;

    protected void setUp() throws Exception {
        super.setUp();
        DBConnection connection = new DBConnection("testingdb");
        ScriptRunner runner = new ScriptRunner(connection.getConnection());
        runner.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/For_Testing.sql"));
        runner.runScript(reader);
        PlayerHistoryDao playerHistoryDAO = new SqlPlayerHistoryDao(connection);
        playerHistoryService = new PlayerHistoryServiceImpl(playerHistoryDAO);
        GameDAO gameDAO = new SqlGameDAO(connection);
        gameService = new GameServiceImpl(gameDAO);
    }

    public void testAddEntriesAndGetGames(){
        //must add users
        int gameID1 = gameService.addGame("RED", "BLUE", false);
        PlayerHistory ph1 = playerHistoryService.addPlayerHistoryEntry(gameID1, 1, "RED");
        assertEquals(ph1.getGameID(), gameID1);
        assertEquals(ph1.getUserID(), 1);
        assertEquals(ph1.getTeam(), "RED");

        PlayerHistory ph2 = playerHistoryService.addPlayerHistoryEntry(gameID1, 2, "RED");
        assertEquals(ph2.getGameID(), gameID1);
        assertEquals(ph2.getUserID(), 2);
        assertEquals(ph2.getTeam(), "RED");

        PlayerHistory ph3 = playerHistoryService.addPlayerHistoryEntry(gameID1, 3, "BLUE");
        assertEquals(ph3.getGameID(), gameID1);
        assertEquals(ph3.getUserID(), 3);
        assertEquals(ph3.getTeam(), "BLUE");

        PlayerHistory ph4 = playerHistoryService.addPlayerHistoryEntry(gameID1, 4, "BLUE");
        assertEquals(ph4.getGameID(), gameID1);
        assertEquals(ph4.getUserID(), 4);
        assertEquals(ph4.getTeam(), "BLUE");



        int gameID2 = gameService.addGame("RED", "BLUE", true);
        PlayerHistory ph5 = playerHistoryService.addPlayerHistoryEntry(gameID2, 1, "BLUE");
        assertEquals(ph5.getGameID(), gameID2);
        assertEquals(ph5.getUserID(), 1);
        assertEquals(ph5.getTeam(), "BLUE");

        PlayerHistory ph6 = playerHistoryService.addPlayerHistoryEntry(gameID2, 3, "BLUE");
        assertEquals(ph6.getGameID(), gameID2);
        assertEquals(ph6.getUserID(), 3);
        assertEquals(ph6.getTeam(), "BLUE");

        PlayerHistory ph7 = playerHistoryService.addPlayerHistoryEntry(gameID2, 2, "RED");
        assertEquals(ph7.getGameID(), gameID2);
        assertEquals(ph7.getUserID(), 2);
        assertEquals(ph7.getTeam(), "RED");

        PlayerHistory ph8 = playerHistoryService.addPlayerHistoryEntry(gameID2, 4, "RED");
        assertEquals(ph8.getGameID(), gameID2);
        assertEquals(ph8.getUserID(), 4);
        assertEquals(ph8.getTeam(), "RED");



        int gameID3 = gameService.addGame("RED", "BLUE", true);
        PlayerHistory ph9 = playerHistoryService.addPlayerHistoryEntry(gameID3, 1, "RED");
        assertEquals(ph9.getGameID(), gameID3);
        assertEquals(ph9.getUserID(), 1);
        assertEquals(ph9.getTeam(), "RED");

        PlayerHistory ph10 = playerHistoryService.addPlayerHistoryEntry(gameID3, 4, "RED");
        assertEquals(ph10.getGameID(), gameID3);
        assertEquals(ph10.getUserID(), 4);
        assertEquals(ph10.getTeam(), "RED");

        PlayerHistory ph11 = playerHistoryService.addPlayerHistoryEntry(gameID3, 2, "BLUE");
        assertEquals(ph11.getGameID(), gameID3);
        assertEquals(ph11.getUserID(), 2);
        assertEquals(ph11.getTeam(), "BLUE");

        PlayerHistory ph12 = playerHistoryService.addPlayerHistoryEntry(gameID3, 3, "BLUE");
        assertEquals(ph12.getGameID(), gameID3);
        assertEquals(ph12.getUserID(), 3);
        assertEquals(ph12.getTeam(), "BLUE");

        List<Integer> games = playerHistoryService.getGames(3, 1);
        assertEquals(games.size(), 3);
    }

//    public void testExceptions(){
//
//    }
}
