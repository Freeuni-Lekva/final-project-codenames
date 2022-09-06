package com.example.codenames.model;

import java.util.List;

public class Board {

    private List<String> words;
    private List<WordColor> colors;

    public List<String> getWords() {
        return words;
    }

    public List<WordColor> getColors() {
        return colors;
    }

    public Board(List<String> words, List<WordColor> colors) {
        this.words = words;
        this.colors = colors;
    }

}
