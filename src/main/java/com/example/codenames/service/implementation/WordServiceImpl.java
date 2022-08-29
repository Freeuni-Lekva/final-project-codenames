package com.example.codenames.service.implementation;

import com.example.codenames.DAO.WordDAO;
import com.example.codenames.exception.NotEnoughWordsException;
import com.example.codenames.exception.WordNotAddedException;
import com.example.codenames.exception.WordNotFoundException;
import com.example.codenames.exception.WordNotRemovedException;
import com.example.codenames.model.Word;
import com.example.codenames.service.WordService;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class WordServiceImpl implements WordService {

    private static final int WORDS_NUM = 25;

    private WordDAO wordDAO;

    public WordServiceImpl(WordDAO wordDAO) {
        this.wordDAO = wordDAO;
    }

    @Override
    public boolean addWord(String name, String category) {
        try {
            return wordDAO.addWord(name, category);
        } catch (WordNotAddedException e) {
            return false;
        }
    }

    @Override
    public boolean removeWord(String name) {
        try {
            return wordDAO.removeWord(name);
        } catch (WordNotRemovedException e) {
            return false;
        }
    }

    @Override
    public boolean removeWordFromCategory(String name, String category) {
        try {
            return wordDAO.removeWordFromCategory(name, category);
        } catch (WordNotRemovedException e) {
            return false;
        }
    }

    @Override
    public List<String> getRandomizedWords(List<String> categories) {
        Set<String> allWords = new HashSet<>();
        for (String category : categories) {
            allWords.addAll(wordDAO.getWordsFromCategory(category));
        }
        List<String> finalWords = new ArrayList<>();
        for (String word : allWords) {
            finalWords.add(word);
            if (finalWords.size() == WORDS_NUM) return finalWords;
        }
        throw new NotEnoughWordsException("You cannot randomize " + WORDS_NUM + " words from selected categories");
    }

    @Override
    public Word getWordByName(String name) {
        try {
            return wordDAO.getWordByName(name);
        } catch (WordNotFoundException e) {
            return null;
        }
    }
}
