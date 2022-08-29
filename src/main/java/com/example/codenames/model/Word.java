package com.example.codenames.model;

import java.util.List;

public class Word {
    private String name;
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
