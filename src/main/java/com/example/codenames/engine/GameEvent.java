package com.example.codenames.engine;

import com.example.codenames.model.WordColor;

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


    public GameEvent(WordColor sideNow, int remainingRed, int remainingBlue) {
        this.sideNow = sideNow;
        this.colorOfIndex = null;
        this.winner = null;
        this.openedIndex = -1;
        this.remainingRed = remainingRed;
        this.remainingBlue = remainingBlue;
    }

    public GameEvent(int openedIndex, WordColor colorOfIndex, WordColor sideNow, int remainingRed, int remainingBlue) {
        this.openedIndex = openedIndex;
        this.colorOfIndex = colorOfIndex;
        this.sideNow = sideNow;
        this.winner = null;
        this.remainingRed = remainingRed;
        this.remainingBlue = remainingBlue;
    }

    public GameEvent(int openedIndex, WordColor colorOfIndex, WordColor sideNow, WordColor winner, int remainingRed, int remainingBlue) {
        this.openedIndex = openedIndex;
        this.colorOfIndex = colorOfIndex;
        this.sideNow = sideNow;
        this.winner = winner;
        this.remainingRed = remainingRed;
        this.remainingBlue = remainingBlue;
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
