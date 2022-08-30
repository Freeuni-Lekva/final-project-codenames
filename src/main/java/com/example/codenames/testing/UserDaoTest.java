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
import java.util.List;

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

    public void testUsersByPoints() {
        User user1 = new User("User1", "rootroot");
        User user2 = new User("User2", "rootroot");
        User user3 = new User("User3", "rootroot");
        user1 = userDao.registerUser(user1);
        user2 = userDao.registerUser(user2);
        user3 = userDao.registerUser(user3);
        user1.setPoints(10);
        user2.setPoints(2);
        user3.setPoints(9);
        assertTrue(userDao.updateUser(user1));
        assertEquals(Integer.valueOf(10), userDao.getByUserName(user1.getUsername()).getPoints());
        assertTrue(userDao.updateUser(user2));
        assertTrue(userDao.updateUser(user3));
        assertEquals(Integer.valueOf(2), userDao.getByUserName(user2.getUsername()).getPoints());
        assertEquals(Integer.valueOf(9), userDao.getByUserName(user3.getUsername()).getPoints());
        List<User> userList = userDao.getUsersByPoints(String.valueOf("ASC"));
        assertEquals(user1.getUsername(), userList.get(2).getUsername());
        assertEquals(user2.getUsername(), userList.get(0).getUsername());
        assertEquals(user3.getUsername(), userList.get(1).getUsername());



    }




}