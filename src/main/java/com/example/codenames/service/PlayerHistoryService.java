package com.example.codenames.service;

import com.example.codenames.Model.PlayerHistory;
import com.example.codenames.exception.InvalidNumberOfGamesException;
import com.example.codenames.exception.InvalidTeamSpecifier;

import java.util.List;

public interface PlayerHistoryService {
    PlayerHistory addPlayerHistoryEntry(int gameID, int userID, String team) throws InvalidTeamSpecifier;
    List<Integer> getGames(int num, int userID) throws InvalidNumberOfGamesException;
}
