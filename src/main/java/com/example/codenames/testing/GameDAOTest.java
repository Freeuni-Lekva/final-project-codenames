package com.example.codenames.testing;

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
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class GameDAOTest extends TestCase {

    private GameDAO gameDAO;

    protected void setUp() throws Exception {
        super.setUp();

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root",  "rootroot");
        ScriptRunner runner = new ScriptRunner(connection);
        runner.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/For_Testing.sql"));
        runner.runScript(reader);

        DBConnection dbConnection = new DBConnection("testingdb");
        gameDAO = new SqlGameDAO(dbConnection);
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
    }

    public void testGameNotFoundException() {
        assertThrows(GameNotFoundException.class, () -> gameDAO.getGameByID(-3));
        assertThrows(GameNotFoundException.class, () -> gameDAO.getGameByID(-1));
        assertThrows(GameNotFoundException.class, () -> gameDAO.getGameByID(2));
        assertThrows(GameNotFoundException.class, () -> gameDAO.getGameByID(0));
    }
}
