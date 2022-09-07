package com.example.codenames.engine;

import com.example.codenames.model.*;

import java.util.*;

public class GameEngine {

    private static final int WORDS_NUM = 25;
    private static final int BEIGE_WORDS_NUM = 7;
    private static final int SECOND_TEAM_NUM = 8;

    private static final Random rand = new Random(System.currentTimeMillis());

    private final Room room;
    private final Board board;
    private WordColor sideToPlay;
    private final Set<Integer> opened;

    private int remainingRed;

    private int remainingBlue;

    public GameEngine(List<String> words) {
        this.room = null; // TODO: 08.09.22 will need this during housekeeping
        WordColor startsTheGame = rand.nextBoolean() ? WordColor.RED : WordColor.BLUE;
        List<WordColor> colors = generateColors(startsTheGame);
        this.sideToPlay = startsTheGame;
        this.board = new Board(words, colors);
        this.remainingRed = SECOND_TEAM_NUM + (startsTheGame == WordColor.RED ? 1 : 0);
        this.remainingBlue = SECOND_TEAM_NUM + (startsTheGame == WordColor.BLUE ? 1 : 0);
        this.opened = new HashSet<>();
    }


    public GameEvent registerMove(int index) {
        GameEvent gameEvent = registerMoveInternal(index);
        if (gameEvent != null && gameEvent.getWinner() != null) {
            // TODO: 08.09.22 do the all housekeeping for the ended game
        }
        return gameEvent;
    }

    public synchronized GameEvent skipTheMove() {
        twistSide();
        return new GameEvent(this.sideToPlay);
    }

    public List<String> getWords() {
        return this.board.getWords();
    }

     private synchronized GameEvent registerMoveInternal(int index) {
        if (opened.contains(index)) {
            return null;
        }
        opened.add(index);

        WordColor wordColor = this.board.getColors().get(index);
        switch (wordColor) {
            case BLACK:
                twistSide();
                return new GameEvent(index, WordColor.BLACK, this.sideToPlay, this.sideToPlay);
            case BEIGE:
                twistSide();
                return new GameEvent(index, WordColor.BEIGE, this.sideToPlay);
            default:
                int remaining = 0;
                if (wordColor == WordColor.RED) {
                    remainingRed--;
                    remaining = remainingRed;
                } else {
                    remainingBlue--;
                    remaining = remainingBlue;
                }
                if (wordColor != sideToPlay) {
                    twistSide();
                }
                WordColor winner = remaining == 0 ? wordColor : null;
                return new GameEvent(index, wordColor, sideToPlay, winner);
        }
    }

    private void twistSide() {
        sideToPlay = sideToPlay != WordColor.RED ? WordColor.RED : WordColor.BLUE;
    }

    private List<WordColor> generateColors(WordColor startTeam) {
        WordColor[] wordColors = new WordColor[WORDS_NUM];
        int counter = 0;
        wordColors[counter++] = WordColor.BLACK;
        wordColors[counter++] = startTeam;
        for (int i = 0; i < SECOND_TEAM_NUM; i++) {
            wordColors[counter++] = WordColor.RED;
            wordColors[counter++] = WordColor.BLUE;
            if (i < BEIGE_WORDS_NUM) {
                wordColors[counter++] = WordColor.BEIGE;
            }
        }

        List<WordColor> colors = Arrays.asList(wordColors);
        Collections.shuffle(colors);
        return colors;
    }



}
