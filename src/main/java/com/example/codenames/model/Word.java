package com.example.codenames.model;

import java.util.List;

public class Word {
    private String name;
    public static final String TABLE_WORDS_COLUMN = "word";
    public static final String TABLE_NAME = "words";

    private List<String> categories;

    public Word(String name, List<String> categories) {
        this.name = name;
        this.categories = categories;
    }

    public String getName() {
        return name;
    }

    public List<String> getCategories() {
        return categories;
    }
}
