package com.example.codenames.testing;

import com.example.codenames.DAO.GameDAO;
import com.example.codenames.DAO.PlayerHistoryDao;
import com.example.codenames.DAO.UserDao;
import com.example.codenames.DAO.sqlImplementation.SqlPlayerHistoryDao;
import com.example.codenames.DAO.sqlImplementation.SqlGameDAO;
import com.example.codenames.DAO.sqlImplementation.SqlUserDao;
import com.example.codenames.DTO.UserCredentialsDto;
import com.example.codenames.exception.InvalidNumberOfGamesException;
import com.example.codenames.exception.InvalidTeamSpecifier;
import com.example.codenames.model.PlayerHistory;
import com.example.codenames.database.DBConnection;
import com.example.codenames.model.User;
import com.example.codenames.service.GameService;
import com.example.codenames.service.PlayerHistoryService;
import com.example.codenames.service.UserService;
import com.example.codenames.service.implementation.GameServiceImpl;
import com.example.codenames.service.implementation.PlayerHistoryServiceImpl;
import com.example.codenames.service.implementation.UserServiceImpl;
import junit.framework.TestCase;
import org.apache.ibatis.jdbc.ScriptRunner;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class PlayerHistoryServiceImplTest extends TestCase {

    private PlayerHistoryService playerHistoryService;
    private GameService gameService;
    private UserService userService;

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
        UserDao userDao = new SqlUserDao(connection);
        userService = new UserServiceImpl(userDao);
    }

    public void testAddEntriesAndGetGames(){
        User user1 = userService.registerUser(new UserCredentialsDto("Pete1999", "Pass12345678"));
        User user2 = userService.registerUser(new UserCredentialsDto("James2001", "Codenames12"));
        User user3 = userService.registerUser(new UserCredentialsDto("Jane1998", "Letmeplay123"));
        User user4 = userService.registerUser(new UserCredentialsDto("Mary1995", "Hey87654321"));
        int user1ID = user1.getUserID();
        int user2ID = user2.getUserID();
        int user3ID = user3.getUserID();
        int user4ID = user4.getUserID();

        int gameID1 = gameService.addGame("RED", "BLUE", false);
        PlayerHistory ph1 = playerHistoryService.addPlayerHistoryEntry(gameID1, user1ID, "RED");
        assertEquals(ph1.getGameID(), gameID1);
        assertEquals(ph1.getUserID(), user1ID);
        assertEquals(ph1.getTeam(), "RED");

        PlayerHistory ph2 = playerHistoryService.addPlayerHistoryEntry(gameID1, user2ID, "RED");
        assertEquals(ph2.getGameID(), gameID1);
        assertEquals(ph2.getUserID(), user2ID);
        assertEquals(ph2.getTeam(), "RED");

        PlayerHistory ph3 = playerHistoryService.addPlayerHistoryEntry(gameID1, user3ID, "BLUE");
        assertEquals(ph3.getGameID(), gameID1);
        assertEquals(ph3.getUserID(), user3ID);
        assertEquals(ph3.getTeam(), "BLUE");

        PlayerHistory ph4 = playerHistoryService.addPlayerHistoryEntry(gameID1, user4ID, "BLUE");
        assertEquals(ph4.getGameID(), gameID1);
        assertEquals(ph4.getUserID(), user4ID);
        assertEquals(ph4.getTeam(), "BLUE");



        int gameID2 = gameService.addGame("RED", "BLUE", true);
        PlayerHistory ph5 = playerHistoryService.addPlayerHistoryEntry(gameID2, user1ID, "BLUE");
        assertEquals(ph5.getGameID(), gameID2);
        assertEquals(ph5.getUserID(), user1ID);
        assertEquals(ph5.getTeam(), "BLUE");

        PlayerHistory ph6 = playerHistoryService.addPlayerHistoryEntry(gameID2, user3ID, "BLUE");
        assertEquals(ph6.getGameID(), gameID2);
        assertEquals(ph6.getUserID(), user3ID);
        assertEquals(ph6.getTeam(), "BLUE");

        PlayerHistory ph7 = playerHistoryService.addPlayerHistoryEntry(gameID2, user2ID, "RED");
        assertEquals(ph7.getGameID(), gameID2);
        assertEquals(ph7.getUserID(), user2ID);
        assertEquals(ph7.getTeam(), "RED");

        PlayerHistory ph8 = playerHistoryService.addPlayerHistoryEntry(gameID2, user4ID, "RED");
        assertEquals(ph8.getGameID(), gameID2);
        assertEquals(ph8.getUserID(), user4ID);
        assertEquals(ph8.getTeam(), "RED");



        int gameID3 = gameService.addGame("RED", "BLUE", true);
        PlayerHistory ph9 = playerHistoryService.addPlayerHistoryEntry(gameID3, user1ID, "RED");
        assertEquals(ph9.getGameID(), gameID3);
        assertEquals(ph9.getUserID(), user1ID);
        assertEquals(ph9.getTeam(), "RED");

        PlayerHistory ph10 = playerHistoryService.addPlayerHistoryEntry(gameID3, user4ID, "RED");
        assertEquals(ph10.getGameID(), gameID3);
        assertEquals(ph10.getUserID(), user4ID);
        assertEquals(ph10.getTeam(), "RED");

        PlayerHistory ph11 = playerHistoryService.addPlayerHistoryEntry(gameID3, user2ID, "BLUE");
        assertEquals(ph11.getGameID(), gameID3);
        assertEquals(ph11.getUserID(), user2ID);
        assertEquals(ph11.getTeam(), "BLUE");

        PlayerHistory ph12 = playerHistoryService.addPlayerHistoryEntry(gameID3, user3ID, "BLUE");
        assertEquals(ph12.getGameID(), gameID3);
        assertEquals(ph12.getUserID(), user3ID);
        assertEquals(ph12.getTeam(), "BLUE");

        checkGamesList(user1ID, gameID1, gameID2, gameID3);
        checkGamesList(user2ID, gameID1, gameID2, gameID3);
        checkGamesList(user3ID, gameID1, gameID2, gameID3);
    }

    private void checkGamesList(int userID, int gameID1, int gameID2, int gameID3) {
        List<Integer> gamesOfUser = playerHistoryService.getGames(3, userID);
        assertEquals(gamesOfUser.size(), 3);
        assertEquals(gamesOfUser.get(0), (Integer) gameID1);
        assertEquals(gamesOfUser.get(1), (Integer) gameID2);
        assertEquals(gamesOfUser.get(2), (Integer) gameID3);
    }

    public void testExceptions(){
        assertThrows(InvalidTeamSpecifier.class, () -> playerHistoryService.addPlayerHistoryEntry(123, 12, "GREEN"));
        assertThrows(InvalidNumberOfGamesException.class, () -> playerHistoryService.getGames(-4, 12));
    }
}
