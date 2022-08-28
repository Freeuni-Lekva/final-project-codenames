package com.example.codenames.DAO;

import com.example.codenames.model.Word;

import java.util.List;

public interface WordDAO {
    /**
     *
     * @param name
     * @param category
     * @return boolean about whether the word was added in the category
     */
    boolean addWord(String name, String category);

    /**
     *
     * @param name
     * @return boolean about whether the word was deleted
     */
    boolean removeWord(String name);

    /**
     *
     * @param name
     * @param category
     * @return boolean about whether the word was deleted from the category
     */
    boolean removeWordFromCategory(String name, String category);

    /**
     *
     * @param category
     * @return list of words from category
     */
    List<String> getWordsFromCategory(String category);

    /**
     *
     * @param name
     * @return Word object with the name
     */
    Word getWordByName(String name);
}