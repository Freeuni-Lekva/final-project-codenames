package com.example.codenames.engine;

import com.example.codenames.model.WordColor;
import com.google.gson.Gson;

import java.util.List;

public class GameEvent {

    private Integer openedIndex;

    private WordColor colorOfIndex;

    private WordColor sideNow;

    private WordColor winner;

    private Integer remainingRed;

    public Integer getRemainingRed() {
        return remainingRed;
    }

    public Integer getRemainingBlue() {
        return remainingBlue;
    }

    private Integer remainingBlue;

    public boolean isSpy() {
        return isSpy;
    }

    private boolean isSpy;




    public GameEvent(WordColor sideNow, int remainingRed, int remainingBlue, boolean isSpy) {
        this.sideNow = sideNow;
        this.colorOfIndex = null;
        this.winner = null;
        this.openedIndex = -1;
        this.remainingRed = remainingRed;
        this.remainingBlue = remainingBlue;
        this.isSpy = isSpy;
    }

    public GameEvent(int openedIndex, WordColor colorOfIndex, WordColor sideNow, int remainingRed, int remainingBlue) {
        this.openedIndex = openedIndex;
        this.colorOfIndex = colorOfIndex;
        this.sideNow = sideNow;
        this.winner = null;
        this.remainingRed = remainingRed;
        this.remainingBlue = remainingBlue;
        this.isSpy = false;
    }

    public GameEvent(int openedIndex, WordColor colorOfIndex, WordColor sideNow, WordColor winner, int remainingRed, int remainingBlue) {
        this.openedIndex = openedIndex;
        this.colorOfIndex = colorOfIndex;
        this.sideNow = sideNow;
        this.winner = winner;
        this.remainingRed = remainingRed;
        this.remainingBlue = remainingBlue;
        this.isSpy = false;
    }

    public Integer getOpenedIndex() {
        return openedIndex;
    }

    public WordColor getColorOfIndex() {
        return colorOfIndex;
    }

    public WordColor getSideNow() {
        return sideNow;
    }

    public WordColor getWinner() {
        return winner;
    }
}
