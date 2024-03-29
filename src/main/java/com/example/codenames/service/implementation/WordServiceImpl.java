package com.example.codenames.service.implementation;

import com.example.codenames.DAO.WordDAO;
import com.example.codenames.DTO.WordAndCategoryDto;
import com.example.codenames.exception.NotEnoughWordsException;
import com.example.codenames.exception.WordNotAddedException;
import com.example.codenames.exception.WordNotFoundException;
import com.example.codenames.exception.WordNotRemovedException;
import com.example.codenames.model.Word;
import com.example.codenames.model.WordColor;
import com.example.codenames.service.WordService;

import java.util.*;

public class WordServiceImpl implements WordService {

    private static final int WORDS_NUM = 25;

    private WordDAO wordDAO;
    private Random rgen;

    public WordServiceImpl(WordDAO wordDAO) {
        this.wordDAO = wordDAO;
        this.rgen = new Random();
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
        List<String> allWordsList = new ArrayList<>(allWords);
        if (allWordsList.size() < WORDS_NUM) {
            throw new NotEnoughWordsException("You cannot randomize " + WORDS_NUM + " words from selected categories");
        }
        List<String> finalWords = new ArrayList<>();
        while(finalWords.size() < WORDS_NUM){
            int index = rgen.nextInt(allWordsList.size());
            finalWords.add(allWordsList.get(index));
            allWordsList.remove(index);
        }
        return finalWords;
    }

    @Override
    public Word getWordByName(String name) {
        try {
            return wordDAO.getWordByName(name);
        } catch (WordNotFoundException e) {
            return null;
        }
    }

    @Override
    public List<String> getAllCategories() {
        try {
            return wordDAO.getAllCategories();
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    @Override
    public List<String> getAllWords() {
        List<String> ans = wordDAO.getAllWords();
        if(ans != null){
            return ans;
        }
        else{
            throw new WordNotFoundException("");
        }
    }

    @Override
    public List<WordAndCategoryDto> getWordsWithCategories() {
        List<WordAndCategoryDto> ans = wordDAO.getWordsWithCategories();
        if(ans != null){
            return ans;
        }
        else{
            throw new WordNotFoundException("");
        }
    }
}