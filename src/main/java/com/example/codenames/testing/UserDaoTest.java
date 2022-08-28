package com.example.codenames.testing;

import com.example.codenames.DAO.UserDao;
import com.example.codenames.DAO.sqlImplementation.SqlUserDao;
import com.example.codenames.database.DBConnection;
import com.example.codenames.model.User;
import junit.framework.TestCase;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class UserDaoTest extends TestCase {

    private UserDao userDao;

    protected void setUp() throws Exception {
        super.setUp();

        DBConnection connection = new DBConnection("testingdb");
        ScriptRunner runner = new ScriptRunner(connection.getConnection());
        runner.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/For_Testing.sql"));
        runner.runScript(reader);

        userDao = new SqlUserDao(connection);
    }

    public void testRegisterAndLoginUser() {
        User user = new User("Ruska", "12345678");
        User added = userDao.registerUser(user);
        assertEquals(false, (added == null));
        assertEquals(added.getUsername(), "Ruska");
        assertTrue(added.getGamesPlayed() == 0);
        assertTrue(added.getHashedPassword() != "12345678");

        User user1 = new User("Ruska", "rameparoli");
        User tmp1 = userDao.loginUser(user1);
        assertEquals(null, tmp1);

        User user2 = new User("Ruska", "12345678");
        User tmp2 = userDao.loginUser(user2);
        assertTrue(user2 != null);
        assertEquals(tmp2.getUsername(), user2.getUsername());
        assertEquals(tmp2.getHashedPassword(), user2.getHashedPassword());


        assertTrue(userDao.getByUserName("Ruska") != null);
        assertEquals(null, userDao.getByUserName("Rame"));
        userDao.registerUser(new User("Rame", "RameParoli"));
        assertTrue(userDao.getByUserName("Rame") != null);
    }
}