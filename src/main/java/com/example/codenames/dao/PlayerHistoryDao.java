package com.example.codenames.dao;


import com.example.codenames.Model.PlayerHistory;
import com.example.codenames.dto.PlayerHistoryDto;

import java.util.List;

public interface PlayerHistoryDao {
    List<Integer> getGames(int num, int userID);
    PlayerHistory addPlayerHistoryEntry(PlayerHistoryDto data);
}
