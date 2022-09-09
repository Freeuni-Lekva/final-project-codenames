package com.example.codenames.service;

import com.example.codenames.model.Word;
import com.example.codenames.model.WordColor;

import java.util.List;

public interface WordService {
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
     * @param categories
     * @return 25 randomized words from selected categories
     */
    List<String> getRandomizedWords(List<String> categories);

    /**
     *
     * @param name
     * @return Word object with the name
     */
    Word getWordByName(String name);

    /**
     *
     * @return List of all categories in the database
     */
    List<String> getAllCategories();
}
