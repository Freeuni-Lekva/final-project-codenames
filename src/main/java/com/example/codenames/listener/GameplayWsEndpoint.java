package com.example.codenames.listener;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@ServerEndpoint(value = "/gameplay_ws", configurator = EndpointConfigurator.class)
public class GameplayWsEndpoint {

    private static final ConcurrentHashMap<String, ConcurrentHashMap<Session, Boolean>> sessionsByRoomId = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, ConcurrentHashMap<String, Boolean>> eventStoreByRoomId = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) throws Exception {
        String roomId = getRoomId(session);
        ConcurrentHashMap<Session, Boolean> roomSessions = sessionsByRoomId.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>());
        roomSessions.put(session, true);
        ConcurrentHashMap<String, Boolean> events = eventStoreByRoomId.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>());
        RemoteEndpoint.Basic remote = session.getBasicRemote();
        for (String event : events.keySet()) {
            remote.sendText(event);
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws Exception {
        String roomId = getRoomId(session);
        ConcurrentHashMap<Session, Boolean> roomSessions = sessionsByRoomId.get(roomId);
        eventStoreByRoomId.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>())
                        .put(message, true);
        roomSessions.forEach((roomMemberSession, b) -> {
            roomMemberSession.getAsyncRemote().sendText(message);
        });
    }

    @OnClose
    public void onClose(Session session) throws Exception {
        String roomId = getRoomId(session);
        ConcurrentHashMap<Session, Boolean> roomSessions = sessionsByRoomId.get(roomId);
        roomSessions.remove(session);
        if (roomSessions.isEmpty()) {
            eventStoreByRoomId.remove(roomId);
        }
    }

    private static String getRoomId(Session session) {
        List<String> roomIdList = session.getRequestParameterMap().get(NameConstants.ROOM_ID);
        if (roomIdList.size() != 1) {
            throw new IllegalStateException("Room id should be passed during socket creation");
        }
        String roomId = roomIdList.get(0);
        return roomId;
    }
}
