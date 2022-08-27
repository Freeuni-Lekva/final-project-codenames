package com.example.codenames.service.test;

import com.example.codenames.DAO.GameDAO;
import com.example.codenames.DAO.sqlImplementation.SqlGameDAO;
import com.example.codenames.database.DBConnection;
import com.example.codenames.exception.InvalidTeamsException;
import com.example.codenames.model.Game;
import com.example.codenames.service.GameService;
import com.example.codenames.service.implementation.GameServiceImpl;
import junit.framework.TestCase;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameServiceImplTest extends TestCase {

    private GameService gameService;

    protected void setUp() throws Exception {
        super.setUp();

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root",  "rootroot");
        ScriptRunner runner = new ScriptRunner(connection);
        runner.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/For_Testing.sql"));
        runner.runScript(reader);
        
        DBConnection dbConnection = new DBConnection("testingdb");
        GameDAO gameDAO = new SqlGameDAO(dbConnection);
        GameDAO gameDAO = new SqlGameDAO(connection);
        gameService = new GameServiceImpl(gameDAO);
    }

    public void testAddAndGetGame() {
        int id1 = gameService.addGame("RED", "BLUE", true);
        Game game1 = gameService.getGameByID(id1);
        assertEquals("RED", game1.getWinner());
        assertEquals("BLUE", game1.getLoser());
        assertTrue(game1.isBlackWordSelected());

        int id2 = gameService.addGame("RED", "BLUE", false);
        Game game2 = gameService.getGameByID(id2);
        assertEquals("RED", game2.getWinner());
        assertEquals("BLUE", game2.getLoser());
        assertFalse(game2.isBlackWordSelected());

        int id3 = gameService.addGame("BLUE", "RED", true);
        Game game3 = gameService.getGameByID(id3);
        assertEquals("BLUE", game3.getWinner());
        assertEquals("RED", game3.getLoser());
        assertTrue(game3.isBlackWordSelected());

        int id4 = gameService.addGame("BLUE", "RED", false);
        Game game4 = gameService.getGameByID(id4);
        assertEquals("BLUE", game4.getWinner());
        assertEquals("RED", game4.getLoser());
        assertFalse(game4.isBlackWordSelected());
    }

    public void testInvalidTeamsException() {
        assertThrows(InvalidTeamsException.class, () -> gameService.addGame("A", "BLUE", false));
        assertThrows(InvalidTeamsException.class, () -> gameService.addGame("BLUE", "BLUE", true));
        assertThrows(InvalidTeamsException.class, () -> gameService.addGame("RED", "RED", true));
        assertThrows(InvalidTeamsException.class, () -> gameService.addGame("RED", "ABC", false));
    }
}
