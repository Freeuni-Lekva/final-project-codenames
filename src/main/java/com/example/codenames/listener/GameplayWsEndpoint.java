package com.example.codenames.listener;

import com.example.codenames.DTO.GameInfoDTO;
import com.example.codenames.Servlets.GameplayServlet;
import com.example.codenames.engine.GameEngine;
import com.example.codenames.engine.GameEvent;
import com.example.codenames.model.*;
import com.example.codenames.service.GameService;
import com.example.codenames.service.PlayerHistoryService;
import com.example.codenames.service.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.print.attribute.standard.MediaSize;
import javax.servlet.ServletContext;
import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.codenames.listener.NameConstants.*;

@ServerEndpoint(value = "/gameplay_ws", configurator = EndpointConfigurator.class)
public class GameplayWsEndpoint {

    private static final ConcurrentHashMap<String, ConcurrentHashMap<Session, Boolean>> sessionsByRoomId = new ConcurrentHashMap<>();

//    private static final ConcurrentHashMap<String, ConcurrentHashMap<GameEvent, Boolean>> eventStoreByRoomId = new ConcurrentHashMap<>();

    private ObjectMapper om = new ObjectMapper();

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) throws Exception {
        String roomId = getRoomId(session);
        ConcurrentHashMap<Session, Boolean> roomSessions = sessionsByRoomId.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>());
        roomSessions.put(session, true);
        GameEngine gameEngine = GameplayServlet.gameEngineByRoomId.get(roomId);
        HttpSession httpSession = (HttpSession) session.getUserProperties().get(SESSION);
        GameEvent startEvent = gameEngine.startEvent(httpSession);
        session.getAsyncRemote().sendText(om.writeValueAsString(startEvent));
    }

    @OnMessage
    public void onMessage(Session session, String message) throws Exception {
        String roomId = getRoomId(session);
        GameEngine gameEngine = GameplayServlet.gameEngineByRoomId.get(roomId);
        int index = Integer.parseInt(message);
        HttpSession httpSession = (HttpSession) session.getUserProperties().get(SESSION);
        GameEvent gameEvent = gameEngine.registerMove(httpSession, index);
        if (gameEvent != null) {
            ConcurrentHashMap<Session, Boolean> roomSessions = sessionsByRoomId.get(roomId);
            for (Session roomMemberSession : roomSessions.keySet()) {
                roomMemberSession.getAsyncRemote().sendText(om.writeValueAsString(gameEvent));
            }
        }
        if (gameEvent != null && gameEvent.getWinner() != null) {
            registerGame(gameEvent, gameEngine, httpSession);

        }

    }

    private void registerGame(GameEvent gameEvent, GameEngine gameEngine, HttpSession httpSession) {
        //adds game
        WordColor whoWon = gameEvent.getWinner();
        WordColor whoLost = gameEvent.getWinner() == WordColor.RED ? WordColor.BLUE : WordColor.RED;
        boolean lostWithBlackTile = gameEvent.getColorOfIndex() == WordColor.BLACK;
        ServletContext servletContext = httpSession.getServletContext();
        GameService gameService = (GameService) servletContext.getAttribute(GAME_SERVICE);
        int gameID = gameService.addGame(whoWon.toString(), whoLost.toString(), lostWithBlackTile);
        UserService userService = (UserService) servletContext.getAttribute(USER_SERVICE);

        PlayerHistoryService playerHistoryService = (PlayerHistoryService) servletContext.getAttribute(NameConstants.PLAYER_HISTORY_SERVICE);
        Set<Player> playersSet = gameEngine.getRoom().getAllPlayers();
        for(Player player : playersSet){
            User user = userService.getUserByUsername(player.getUser().getUsername());
            String team = "";
            if(player.getPlayerRole() == PlayerRole.BLUE_OPERATIVE ||
                    player.getPlayerRole() == PlayerRole.BLUE_SPYMASTER){
                team = "BLUE";
            } else {
                team = "RED";
            }
            playerHistoryService.addPlayerHistoryEntry(gameID, user.getUserID(), team);
            boolean didWin = false;
            if(gameEvent.getWinner().toString() == team){
                didWin = true;
            }
            Long gamesWon = user.getGamesWon() + (didWin ? 1 : 0);
            Long gamesLost = user.getGamesLost() + (didWin ? 0 : 1);
            Long gamesPlayed = user.getGamesPlayed() + 1;
            boolean endedWithBlackTile = gameEvent.getColorOfIndex() == WordColor.BLACK;
            Long blackWordCounter = user.getBlackWordCounter();
            Integer points = user.getPoints();

            if(endedWithBlackTile && !didWin){
                blackWordCounter++;
                points--;
            } else if(didWin){
                points++;
            }
            double winningRate = (double) gamesWon / (double) gamesPlayed;



            User updatedUser = new User(user.getUserID(), user.getUsername(), user.getHashedPassword(),
                    gamesWon, gamesLost, gamesPlayed, winningRate, blackWordCounter,
                    user.getRegistrationDate(), user.getRole(), points);
            userService.updateUser(updatedUser);
            httpSession.removeAttribute(User.ATTRIBUTE);
            User newUser = userService.getUserByUsername(user.getUsername());
            httpSession.setAttribute(User.ATTRIBUTE, newUser);
        }

    }

    @OnClose
    public void onClose(Session session) throws Exception {
        String roomId = getRoomId(session);
        ConcurrentHashMap<Session, Boolean> roomSessions = sessionsByRoomId.get(roomId);
        roomSessions.remove(session);
//        if (roomSessions.isEmpty()) {
//            eventStoreByRoomId.remove(roomId);
//        }
    }

    private static String getRoomId(Session session) {
        List<String> roomIdList = session.getRequestParameterMap().get(NameConstants.ROOM_ID);
        if (roomIdList.size() != 1) {
            throw new IllegalStateException("Room id should be passed during socket creation");
        }
        return roomIdList.get(0);
    }
}
