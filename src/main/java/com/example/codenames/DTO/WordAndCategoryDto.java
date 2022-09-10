package com.example.codenames.DTO;

public class WordAndCategoryDto {
    private String word;
    private String category;

    public WordAndCategoryDto(String word, String category) {
        this.word = word;
        this.category = category;
    }

    public String getWord() {
        return word;
    }

    public void setWord(String word) {
        this.word = word;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }
}
