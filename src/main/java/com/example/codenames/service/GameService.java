package com.example.codenames.service;

import com.example.codenames.Model.Game;
import java.util.Date;

public interface GameService {
    Game addGame(String winner, String loser, boolean blackWordSelected, Date date);
    Game getGameByID(Long gameID);
}
