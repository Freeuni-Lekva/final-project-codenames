package com.example.codenames.testing;

import com.example.codenames.DAO.WordDAO;
import com.example.codenames.DAO.sqlImplementation.SqlWordDAO;
import com.example.codenames.database.DBConnection;
import com.example.codenames.exception.WordNotAddedException;
import junit.framework.TestCase;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class WordDAOTest extends TestCase {

    private WordDAO wordDAO;
    protected void setUp() throws Exception {
        super.setUp();

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root",  "RameParoli#11");
        ScriptRunner runner = new ScriptRunner(connection);
        runner.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/For_Testing.sql"));
        runner.runScript(reader);

        DBConnection dbConnection = new DBConnection("testingdb");
        wordDAO = new SqlWordDAO(dbConnection);
    }

    public void testAddAndGetWord() {
        assertTrue(wordDAO.addWord("apple", "fruits"));
        assertEquals("fruits", wordDAO.getWordByName("apple").getCategories().get(0));
        assertEquals(1, wordDAO.getWordByName("apple").getCategories().size());
        assertTrue(wordDAO.addWord("Rachel McAdams", "Movies"));
        assertTrue(wordDAO.addWord("Rachel McAdams", "Celebrities"));
        assertEquals(2, wordDAO.getWordByName("Rachel McAdams").getCategories().size());

        assertTrue(wordDAO.addWord("a", "A"));
        assertTrue(wordDAO.addWord("a", "B"));
        assertThrows(WordNotAddedException.class, () -> wordDAO.addWord("a", "A"));
        assertThrows(WordNotAddedException.class, () -> wordDAO.addWord("a", "B"));
    }

    public void testRemoveWord() {
        assertTrue(wordDAO.addWord("fork", "kitchen"));
        assertTrue(wordDAO.addWord("fork", "food"));
        assertTrue(wordDAO.addWord("fork", "silver"));
        assertTrue(wordDAO.removeWordFromCategory("fork", "food"));
        assertEquals(2, wordDAO.getWordByName("fork").getCategories().size());
        assertTrue(wordDAO.removeWordFromCategory("fork", "silver"));
        assertEquals("kitchen", wordDAO.getWordByName("fork").getCategories().get(0));
        assertTrue(wordDAO.addWord("fork", "silver"));
        assertTrue(wordDAO.removeWord("fork"));
        assertFalse(wordDAO.removeWord("fork"));
        assertNull(wordDAO.getWordByName("fork"));
    }

    public void testGetWordsFromCategory() {
        assertTrue(wordDAO.addWord("a", "A"));
        assertTrue(wordDAO.addWord("b", "A"));
        assertTrue(wordDAO.addWord("c", "A"));
        assertTrue(wordDAO.addWord("d", "A"));
        assertTrue(wordDAO.addWord("e", "A"));
        assertTrue(wordDAO.addWord("f", "A"));
        assertTrue(wordDAO.addWord("g", "A"));
        assertTrue(wordDAO.addWord("h", "A"));
        assertEquals(8, wordDAO.getWordsFromCategory("A").size());
        assertTrue(wordDAO.addWord("a", "B"));
        assertEquals(1, wordDAO.getWordsFromCategory("B").size());
        assertEquals(0, wordDAO.getWordsFromCategory("C").size());
    }
}