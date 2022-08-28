package com.example.codenames.testing;

import com.example.codenames.DAO.GameDAO;
import com.example.codenames.DAO.PlayerHistoryDao;
import com.example.codenames.DAO.UserDao;
import com.example.codenames.DAO.sqlImplementation.SqlGameDAO;
import com.example.codenames.DAO.sqlImplementation.SqlPlayerHistoryDao;
import com.example.codenames.DAO.sqlImplementation.SqlUserDao;
import com.example.codenames.DTO.GameInfoDTO;
import com.example.codenames.DTO.PlayerHistoryDto;
import com.example.codenames.database.DBConnection;
import com.example.codenames.model.PlayerHistory;
import com.example.codenames.model.User;
import junit.framework.TestCase;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

public class PlayerHistoryDaoTest extends TestCase {
    private PlayerHistoryDao playerHistoryDao;
    private GameDAO gameDAO;
    private UserDao userDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DBConnection connection = new DBConnection("testingdb");
        ScriptRunner runner = new ScriptRunner(connection.getConnection());
        runner.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/For_Testing.sql"));
        runner.runScript(reader);
        playerHistoryDao = new SqlPlayerHistoryDao(connection);
        gameDAO = new SqlGameDAO(connection);
        userDao = new SqlUserDao(connection);
    }

    public void testAddingEntries(){
        User user1 = userDao.registerUser(new User("MariamT", "Password!"));
        User user2 = userDao.registerUser(new User("EnolaT", "Login123"));
        User user3 = userDao.registerUser(new User("JohnP", "Letme1234"));
        User user4 = userDao.registerUser(new User("RogerL", "Unlock1234"));
        int user1ID = user1.getUserID();
        int user2ID = user2.getUserID();
        int user3ID = user3.getUserID();
        int user4ID = user4.getUserID();
        int gameID = gameDAO.addGame(new GameInfoDTO("RED", "BLUE", true));
        PlayerHistory ph1 = playerHistoryDao.addPlayerHistoryEntry(new PlayerHistoryDto(gameID, user1ID, "RED"));
        assertEquals(ph1.getGameID(), gameID);
        assertEquals(ph1.getTeam(), "RED");
        assertEquals(ph1.getUserID(), user1ID);
        playerHistoryDao.addPlayerHistoryEntry(new PlayerHistoryDto(gameID, user2ID, "RED"));
        playerHistoryDao.addPlayerHistoryEntry(new PlayerHistoryDto(gameID, user3ID, "BLUE"));
        playerHistoryDao.addPlayerHistoryEntry(new PlayerHistoryDto(gameID, user4ID, "BLUE"));
        List<Integer> games = playerHistoryDao.getGames(1, user1ID);
        assertEquals(games.get(0), (Integer) gameID);
    }
}