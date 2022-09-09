package com.example.codenames.listener;

import com.example.codenames.Servlets.GameplayServlet;
import com.example.codenames.engine.GameEngine;
import com.example.codenames.engine.GameEvent;
import com.example.codenames.model.Game;
import com.example.codenames.model.WordColor;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

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
        GameEvent startEvent = gameEngine.startEvent();
        for (Session roomMemberSession : roomSessions.keySet()) {
            roomMemberSession.getAsyncRemote().sendText(om.writeValueAsString(startEvent));
        }
//        ConcurrentHashMap<GameEvent, Boolean> events = eventStoreByRoomId.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>());
//        RemoteEndpoint.Basic remote = session.getBasicRemote();
//        for (GameEvent event : events.keySet()) {
//            remote.sendText(om.writeValueAsString(event));
//        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws Exception {
        String roomId = getRoomId(session);
        GameEngine gameEngine = GameplayServlet.gameEngineByRoomId.get(roomId);
        int index = Integer.parseInt(message);
        GameEvent gameEvent = gameEngine.registerMove(index);
        if (gameEvent != null) {
            ConcurrentHashMap<Session, Boolean> roomSessions = sessionsByRoomId.get(roomId);
            for (Session roomMemberSession : roomSessions.keySet()) {
                roomMemberSession.getAsyncRemote().sendText(om.writeValueAsString(gameEvent));
            }
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
