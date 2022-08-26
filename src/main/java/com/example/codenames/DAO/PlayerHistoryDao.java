package com.example.codenames.DAO;


import com.example.codenames.Model.PlayerHistory;
import com.example.codenames.DTO.PlayerHistoryDto;

import java.util.List;

public interface PlayerHistoryDao {
    List<Integer> getGames(int num, int userID);
    PlayerHistory addPlayerHistoryEntry(PlayerHistoryDto data);
}
