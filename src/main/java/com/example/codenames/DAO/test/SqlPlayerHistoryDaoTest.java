package com.example.codenames.DAO.test;

import com.example.codenames.DAO.PlayerHistoryDao;
import com.example.codenames.DAO.sqlImplementation.SqlPlayerHistoryDao;
import com.example.codenames.database.DBConnection;
import junit.framework.TestCase;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;

public class SqlPlayerHistoryDaoTest extends TestCase {
    private PlayerHistoryDao playerHistoryDao;

    @Override
    protected void setUp() throws Exception {
        super.setUp();
        DBConnection connection = new DBConnection("testingdb");
        ScriptRunner runner = new ScriptRunner(connection.getConnection());
        runner.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/For_Testing.sql"));
        runner.runScript(reader);
        playerHistoryDao = new SqlPlayerHistoryDao(connection);
    }
}
