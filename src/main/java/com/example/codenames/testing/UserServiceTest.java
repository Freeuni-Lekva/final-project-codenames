package com.example.codenames.testing;

import com.example.codenames.DAO.UserDao;
import com.example.codenames.DAO.sqlImplementation.SqlUserDao;
import com.example.codenames.DTO.UserCredentialsDto;
import com.example.codenames.database.DBConnection;
import com.example.codenames.exception.InvalidCredentialsException;
import com.example.codenames.exception.UserNameRepeatedException;
import com.example.codenames.exception.UserPasswordException;
import com.example.codenames.model.User;
import com.example.codenames.service.UserService;
import com.example.codenames.service.implementation.UserServiceImpl;
import junit.framework.TestCase;
import org.apache.ibatis.jdbc.ScriptRunner;
import org.junit.jupiter.api.BeforeAll;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest extends TestCase {

    private UserDao userDao;
    private UserService userService;
@BeforeAll
    protected void setUp() throws Exception {
        super.setUp();
        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root",  "RameParoli#11");
        ScriptRunner runner = new ScriptRunner(connection);
        runner.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/For_Testing.sql"));
        runner.runScript(reader);

        DBConnection dbConnection = new DBConnection("testingdb");

        userDao = new SqlUserDao(dbConnection);
        userService = new UserServiceImpl(userDao);
    }

    public void testRegisterAndLoginUser() {
        UserCredentialsDto credentialsDto = new UserCredentialsDto("FreeUni", "BendukidzeCampus");
        User registeredUser = userService.registerUser(credentialsDto);
        assertEquals(false, (registeredUser == null));
        assertEquals(registeredUser.getUsername(), credentialsDto.getUsername());
        assertTrue(registeredUser.getHashedPassword() != credentialsDto.getPassword());

        User user = userService.loginUser(credentialsDto);
        assertEquals(false, (user == null));
        UserCredentialsDto tmp = new UserCredentialsDto("Freeuni", "paroli");
        assertThrows(InvalidCredentialsException.class, () -> userService.loginUser(tmp));

        User user1 = userService.getUserByUsername(credentialsDto.getUsername());
        assertEquals(false, (user1 == null));
        assertThrows(InvalidCredentialsException.class, () -> userService.getUserByUsername("random"));



        UserCredentialsDto credentialsDto1 = new UserCredentialsDto("FreeUni", "rameparoli");
        assertThrows(UserNameRepeatedException.class, () -> userService.registerUser(credentialsDto1));

        UserCredentialsDto credentialsDto2 = new UserCredentialsDto("newUser", "agruni");
        assertThrows(UserPasswordException.class, () -> userService.registerUser(credentialsDto2));
    }


    public void testGetUsersByPoints(){
        UserCredentialsDto cred1 = new UserCredentialsDto("User1", "BendukidzeCampus");
        UserCredentialsDto cred2= new UserCredentialsDto("User2", "BendukidzeCampus");
        UserCredentialsDto cred3 = new UserCredentialsDto("User3", "BendukidzeCampus");

        User user1 = userService.registerUser(cred1);
        User user2 = userService.registerUser(cred2);
        User user3 = userService.registerUser(cred3);

        user1.setPoints(1);
        user2.setPoints(2);
        user3.setPoints(3);

        assertTrue(userService.updateUser(user1));
        assertTrue(userService.updateUser(user2));
        assertTrue(userService.updateUser(user3));


        assertEquals(Integer.valueOf(1), userService.getUserByUsername(user1.getUsername()).getPoints());
        assertEquals(Integer.valueOf(2), userService.getUserByUsername(user2.getUsername()).getPoints());
        assertEquals(Integer.valueOf(3), userService.getUserByUsername(user3.getUsername()).getPoints());

        List<User> userList = userService.getUsersByPoints(true);
        assertEquals(user1.getUsername(), userList.get(0).getUsername());
        assertEquals(user2.getUsername(), userList.get(1).getUsername());
        assertEquals(user3.getUsername(), userList.get(2).getUsername());

        List<User> userList2 = userService.getUsersByPoints(false);

        assertEquals(user1.getUsername(), userList2.get(2).getUsername());
        assertEquals(user2.getUsername(), userList2.get(1).getUsername());
        assertEquals(user3.getUsername(), userList2.get(0).getUsername());


    }


}