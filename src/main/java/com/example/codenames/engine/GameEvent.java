package com.example.codenames.engine;

import com.example.codenames.model.WordColor;

public class GameEvent {

    private Integer openedIndex;

    private WordColor colorOfIndex;

    private WordColor sideNow;

    private WordColor winner;

    

    public GameEvent(WordColor sideNow) {
        this.openedIndex = -1;
        this.sideNow = sideNow;
    }

    public GameEvent(int openedIndex, WordColor colorOfIndex, WordColor sideNow) {
        this.openedIndex = openedIndex;
        this.colorOfIndex = colorOfIndex;
        this.sideNow = sideNow;
        this.winner = null;
    }

    public GameEvent(int openedIndex, WordColor colorOfIndex, WordColor sideNow, WordColor winner) {
        this.openedIndex = openedIndex;
        this.colorOfIndex = colorOfIndex;
        this.sideNow = sideNow;
        this.winner = winner;
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
