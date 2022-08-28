package com.example.codenames.DAO.test;

import com.example.codenames.DAO.GameDAO;
import com.example.codenames.DAO.sqlImplementation.SqlGameDAO;
import com.example.codenames.DTO.GameInfoDTO;
import com.example.codenames.model.Game;
import com.example.codenames.database.DBConnection;
import com.example.codenames.exception.GameNotFoundException;
import junit.framework.TestCase;

import org.apache.ibatis.jdbc.ScriptRunner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class SqlGameDaoTest extends TestCase {

    private GameDAO gameDAO;

    protected void setUp() throws Exception {
        super.setUp();

        DBConnection connection = new DBConnection("testingdb");
        ScriptRunner runner = new ScriptRunner(connection.getConnection());
        runner.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/For_Testing.sql"));
        runner.runScript(reader);

        gameDAO = new SqlGameDAO(connection);
    }

    public void testAddAndGetGame() {
        GameInfoDTO gameInfo1 = new GameInfoDTO("RED", "BLUE", true);
        int id1 = gameDAO.addGame(gameInfo1);
        Game game1 = gameDAO.getGameByID(id1);
        assertEquals("RED", game1.getWinner());
        assertEquals("BLUE", game1.getLoser());
        assertTrue(game1.isBlackWordSelected());

        GameInfoDTO gameInfo2 = new GameInfoDTO("RED", "BLUE", false);
        int id2 = gameDAO.addGame(gameInfo2);
        Game game2 = gameDAO.getGameByID(id2);
        assertEquals("RED", game2.getWinner());
        assertEquals("BLUE", game2.getLoser());
        assertFalse(game2.isBlackWordSelected());

        GameInfoDTO gameInfo3 = new GameInfoDTO("BLUE", "RED", true);
        int id3 = gameDAO.addGame(gameInfo3);
        Game game3 = gameDAO.getGameByID(id3);
        assertEquals("BLUE", game3.getWinner());
        assertEquals("RED", game3.getLoser());
        assertTrue(game3.isBlackWordSelected());

        GameInfoDTO gameInfo4 = new GameInfoDTO("BLUE", "RED", false);
        int id4 = gameDAO.addGame(gameInfo4);
        Game game4 = gameDAO.getGameByID(id4);
        assertEquals("BLUE", game4.getWinner());
        assertEquals("RED", game4.getLoser());
        assertFalse(game4.isBlackWordSelected());

        assertNull(gameDAO.getGameByID(-1));
        assertNull(gameDAO.getGameByID(-73));
        assertNull(gameDAO.getGameByID(0));
    }

    public void testGameNotFoundException() {
        assertThrows(GameNotFoundException.class, () -> gameDAO.getGameByID(-3));
        assertThrows(GameNotFoundException.class, () -> gameDAO.getGameByID(-1));
        assertThrows(GameNotFoundException.class, () -> gameDAO.getGameByID(2));
        assertThrows(GameNotFoundException.class, () -> gameDAO.getGameByID(0));
    }
}
