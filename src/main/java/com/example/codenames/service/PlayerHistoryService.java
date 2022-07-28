package com.example.codenames.service;

import com.example.codenames.Model.PlayerHistory;

import java.util.List;

public interface PlayerHistoryService {
    PlayerHistory addPlayerHistoryEntry(int gameID, int userID, String team);
    List<Integer> getGames(int num, int userID);
}
