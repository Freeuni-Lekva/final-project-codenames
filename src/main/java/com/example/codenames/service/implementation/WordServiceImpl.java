package com.example.codenames.service.implementation;

import com.example.codenames.DAO.WordDAO;
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
    private static final int BEIGE_WORDS_NUM = 7;
    private static final int START_TEAM_NUM = 9;
    private static final int SECOND_TEAM_NUM = 8;

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
    public List<WordColor> getScheme() {
        List<WordColor> lst = new ArrayList<>(WORDS_NUM);
        Set<Integer> exclude = new HashSet<>();
        int blackInd = rgen.nextInt(0, WORDS_NUM);
        exclude.add(blackInd);
        lst.set(blackInd, WordColor.BLACK);
        for(int i = 0; i < BEIGE_WORDS_NUM; i++){
            while(true){
                int beigeInd = rgen.nextInt(0, WORDS_NUM);
                if(!exclude.contains(beigeInd)){
                    lst.set(beigeInd, WordColor.BEIGE);
                    exclude.add(beigeInd);
                    break;
                }
            }
        }
        WordColor startTeam = rgen.nextBoolean() ? WordColor.BLUE : WordColor.RED;
        WordColor secondTeam = startTeam.equals(WordColor.BLUE) ? WordColor.RED : WordColor.BLUE;
        for(int i = 0; i < START_TEAM_NUM; i++) {
            int startTeamInd = rgen.nextInt(0, WORDS_NUM);
            if (!exclude.contains(startTeamInd)) {
                lst.set(startTeamInd, startTeam);
                exclude.add(startTeamInd);
                break;
            }
        }
        for(int i = 0; i < SECOND_TEAM_NUM; i++) {
            int secondTeamInd = rgen.nextInt(0, WORDS_NUM);
            if (!exclude.contains(secondTeamInd)) {
                lst.set(secondTeamInd, startTeam);
                exclude.add(secondTeamInd);
                break;
            }
        }
        return lst;
    }
}