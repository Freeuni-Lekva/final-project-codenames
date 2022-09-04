package com.example.codenames.model;

import java.util.List;

public class Board {

    private List<String> words;
    private List<WordColor> colors;

    public Board(List<String> words, List<WordColor> colors) {
        this.words = words;
        this.colors = colors;
    }

}
