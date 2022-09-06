package com.example.codenames.DAO;


import com.example.codenames.DTO.PlayerHistoryDto;
import com.example.codenames.DTO.UserGamesDto;
import com.example.codenames.model.Game;
import com.example.codenames.model.PlayerHistory;

import java.util.List;

public interface PlayerHistoryDao {
    List<Integer> getGames(int num, int userID);
    PlayerHistory addPlayerHistoryEntry(PlayerHistoryDto data);

    List<UserGamesDto> getSortedGames(int userId);
}
