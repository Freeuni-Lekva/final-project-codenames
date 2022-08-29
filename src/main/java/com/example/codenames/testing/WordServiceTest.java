package com.example.codenames.testing;

import com.example.codenames.DAO.WordDAO;
import com.example.codenames.DAO.sqlImplementation.SqlWordDAO;
import com.example.codenames.database.DBConnection;
import com.example.codenames.exception.NotEnoughWordsException;
import com.example.codenames.service.WordService;
import com.example.codenames.service.implementation.WordServiceImpl;
import junit.framework.TestCase;
import org.apache.ibatis.jdbc.ScriptRunner;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.Reader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class WordServiceTest extends TestCase {

    private WordService wordService;
    protected void setUp() throws Exception {
        super.setUp();

        Class.forName("com.mysql.cj.jdbc.Driver");
        Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306", "root",  "rootroot");
        ScriptRunner runner = new ScriptRunner(connection);
        runner.setLogWriter(null);
        Reader reader = new BufferedReader(new FileReader("src/main/resources/For_Testing.sql"));
        runner.runScript(reader);

        DBConnection dbConnection = new DBConnection("testingdb");
        WordDAO wordDAO = new SqlWordDAO(dbConnection);
        wordService = new WordServiceImpl(wordDAO);
    }

    public void testAddAndGetWord() {
        assertTrue(wordService.addWord("a", "A"));
        assertFalse(wordService.addWord("a", "A"));
        assertEquals(1, wordService.getWordByName("a").getCategories().size());
        assertEquals("A", wordService.getWordByName("a").getCategories().get(0));
        assertTrue(wordService.addWord("a", "B"));
        assertEquals(2, wordService.getWordByName("a").getCategories().size());
    }

    public void testRemoveWord() {
        assertTrue(wordService.addWord("a", "A"));
        assertTrue(wordService.removeWord("a"));
        assertNull(wordService.getWordByName("a"));
        assertFalse(wordService.removeWord("a"));

        assertTrue(wordService.addWord("a", "A"));
        assertTrue(wordService.addWord("a", "B"));
        assertEquals(2, wordService.getWordByName("a").getCategories().size());
        assertTrue(wordService.removeWordFromCategory("a", "A"));
        assertEquals(1, wordService.getWordByName("a").getCategories().size());
        assertEquals("B", wordService.getWordByName("a").getCategories().get(0));
    }

    public void testGetRandomizedWords() {
        assertTrue(wordService.addWord("a", "A"));
        assertTrue(wordService.addWord("b", "A"));
        assertTrue(wordService.addWord("c", "A"));
        assertTrue(wordService.addWord("d", "A"));
        assertTrue(wordService.addWord("e", "A"));
        assertTrue(wordService.addWord("f", "A"));
        assertTrue(wordService.addWord("g", "A"));
        assertTrue(wordService.addWord("h", "A"));
        assertTrue(wordService.addWord("i", "A"));
        assertTrue(wordService.addWord("j", "A"));
        assertTrue(wordService.addWord("k", "A"));
        assertTrue(wordService.addWord("l", "A"));
        assertTrue(wordService.addWord("m", "A"));
        assertTrue(wordService.addWord("n", "A"));
        assertTrue(wordService.addWord("o", "A"));
        assertTrue(wordService.addWord("p", "A"));
        assertTrue(wordService.addWord("q", "A"));
        assertTrue(wordService.addWord("r", "A"));
        assertTrue(wordService.addWord("s", "A"));
        assertTrue(wordService.addWord("t", "A"));
        assertTrue(wordService.addWord("u", "A"));
        assertTrue(wordService.addWord("v", "A"));
        assertTrue(wordService.addWord("w", "A"));
        assertTrue(wordService.addWord("x", "A"));
        assertTrue(wordService.addWord("y", "A"));
        List<String> categories = new ArrayList<>();
        categories.add("A");
        assertEquals(25, wordService.getRandomizedWords(categories).size());
        assertTrue(wordService.removeWord("y"));
        assertTrue(wordService.addWord("x", "B"));
        categories.add("B");
        assertThrows(NotEnoughWordsException.class, () -> wordService.getRandomizedWords(categories));
    }
}
