package com.example.codenames.listener.chat;

import com.example.codenames.listener.EndpointConfigurator;
import com.example.codenames.listener.NameConstants;
import com.example.codenames.listener.chat.dto.ChatMessage;
import com.example.codenames.model.User;
import com.fasterxml.jackson.databind.ObjectMapper;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.codenames.listener.NameConstants.SESSION;

@ServerEndpoint(value = "/chat_ws", configurator = EndpointConfigurator.class)
public class ChatWsEndpoint {

    private static final ObjectMapper om = new ObjectMapper();

    private static final ConcurrentHashMap<String, ConcurrentHashMap<Session, Boolean>> sessionsByRoomId = new ConcurrentHashMap<>();

    private static final ConcurrentHashMap<String, ConcurrentHashMap<ChatMessage, Boolean>> messageStoreByRoomId = new ConcurrentHashMap<>();

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) throws Exception {
        String roomId = getRoomId(session);
        ConcurrentHashMap<Session, Boolean> roomSessions = sessionsByRoomId.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>());
        roomSessions.put(session, true);
        ConcurrentHashMap<ChatMessage, Boolean> events = messageStoreByRoomId.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>());
        RemoteEndpoint.Basic remote = session.getBasicRemote();
        for (ChatMessage event : events.keySet()) {
            remote.sendText(om.writeValueAsString(event));
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws Exception {
        ChatMessage chatMessage = new ChatMessage(getCallerUsername(session), message);
        String roomId = getRoomId(session);
        ConcurrentHashMap<Session, Boolean> roomSessions = sessionsByRoomId.get(roomId);
        messageStoreByRoomId.computeIfAbsent(roomId, k -> new ConcurrentHashMap<>())
                .put(chatMessage, true);
        for (Session roomMemberSession : roomSessions.keySet()) {
            roomMemberSession.getAsyncRemote().sendText(om.writeValueAsString(chatMessage));
        }
    }

    @OnClose
    public void onClose(Session session) throws Exception {
        String roomId = getRoomId(session);
        ConcurrentHashMap<Session, Boolean> roomSessions = sessionsByRoomId.get(roomId);
        roomSessions.remove(session);
        if (roomSessions.isEmpty()) {
            messageStoreByRoomId.remove(roomId);
        }
    }

    private static String getRoomId(Session session) {
        List<String> roomIdList = session.getRequestParameterMap().get(NameConstants.ROOM_ID);
        if (roomIdList.size() != 1) {
            throw new IllegalStateException("Room id should be passed during socket creation");
        }
        return roomIdList.get(0);
    }

    private static String getCallerUsername(Session session) {
        HttpSession httpSession = (HttpSession) session.getUserProperties().get(SESSION);
        User user = (User) httpSession.getAttribute(User.ATTRIBUTE);
        return user.getUsername();
    }
}
