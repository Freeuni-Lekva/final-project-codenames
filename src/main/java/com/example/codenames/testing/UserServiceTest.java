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

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserServiceTest extends TestCase {

    private UserDao userDao;
    private UserService userService;

    protected void setUp() throws Exception {
        super.setUp();
        DBConnection connection = new DBConnection("testingdb");
        ScriptRunner runner = new ScriptRunner(connection.getConnection());
        runner.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/For_Testing.sql"));
        runner.runScript(reader);

        userDao = new SqlUserDao(connection);
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




}