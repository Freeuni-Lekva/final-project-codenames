package com.example.codenames.listener;
import com.example.codenames.model.*;
import com.example.codenames.model.Player;
import com.example.codenames.model.Room;
import com.example.codenames.model.User;
import com.google.gson.Gson;

import javax.servlet.http.HttpSession;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

import static com.example.codenames.listener.NameConstants.ROOM;
import static com.example.codenames.listener.NameConstants.SESSION;

@ServerEndpoint(value = "/room", configurator = EndpointConfigurator.class)
public class Endpoint {

    private static Map<String, Set<Session>> sessionsMap = Collections.synchronizedMap(new LinkedHashMap<>());

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) throws Exception {
        Map<String, List<String>> parameterMap = session.getRequestParameterMap();
        String roomID = parameterMap.get(NameConstants.ROOM_ID).get(0);
        Set<Session> currentRoomSessions;
        if(sessionsMap.containsKey(roomID)) {
            currentRoomSessions = sessionsMap.get(roomID);
            currentRoomSessions.add(session);
        } else {
            currentRoomSessions = Collections.synchronizedSet(new HashSet<>());
            currentRoomSessions.add(session);
            sessionsMap.put(roomID, currentRoomSessions);
        }
        HttpSession httpSession = (HttpSession) session.getUserProperties().get(SESSION);
        Map<String, Room> roomMap = (Map<String, Room>) httpSession.getServletContext().getAttribute(NameConstants.ROOM_MAP);
        Room room = roomMap.get(roomID);
        String json = new Gson().toJson(room);
        for(Session playerSession : currentRoomSessions){
            playerSession.getAsyncRemote().sendText("NewUser " + json);
        }
    }

    @OnMessage
    public void onMessage(Session session, String message) throws Exception{
        String roomID = session.getRequestParameterMap().get(NameConstants.ROOM_ID).get(0);
        HttpSession httpSession = (HttpSession) session.getUserProperties().get(SESSION);
        Map<String, Room> roomMap = (Map<String, Room>) httpSession.getServletContext().getAttribute(NameConstants.ROOM_MAP);
        Room room = roomMap.get(roomID);
        User user = (User) httpSession.getAttribute(User.ATTRIBUTE);
        String role = message.substring(message.indexOf(':') + 2, message.length() - 2);
        role = role.substring(0, role.indexOf(' ')) + '_' + role.substring(role.indexOf(' ') + 1);
        PlayerRole playerRole = PlayerRole.NOT_SELECTED;
        switch (role) {
            case "Red_Spymaster":
                playerRole = PlayerRole.RED_SPYMASTER;
                break;
            case "Red_Operative":
                playerRole = PlayerRole.RED_OPERATIVE;
                break;
            case "Blue_Spymaster":
                playerRole = PlayerRole.BLUE_SPYMASTER;
                break;
            case "Blue_Operative":
                playerRole = PlayerRole.BLUE_OPERATIVE;
                break;
        }
        room.getPlayerByUsername(user.getUsername()).setPlayerRole(playerRole);
        String json = new Gson().toJson(room);
        Set<Session> sessions = sessionsMap.get(roomID);
        for (Session playerSession : sessions) {
            playerSession.getAsyncRemote().sendText("AddUserRole " + json);
        }
    }

    @OnClose
    public void onClose(Session session) throws Exception {
        String roomID = session.getRequestParameterMap().get(NameConstants.ROOM_ID).get(0);
        Set<Session> sessions = sessionsMap.get(roomID);
        sessions.remove(session);
        HttpSession httpSession = (HttpSession) session.getUserProperties().get(SESSION);
        try {
            Map<String, Room> roomMap = (Map<String, Room>) httpSession.getServletContext().getAttribute(NameConstants.ROOM_MAP);
            Room room = roomMap.get(roomID);
            User user = (User) httpSession.getAttribute(User.ATTRIBUTE);
//            room.removePlayer(room.getPlayerByUsername(user.getUsername()));
            httpSession.removeAttribute(ROOM);
            String json = new Gson().toJson(room);
            for(Session playerSession : sessions){
                playerSession.getAsyncRemote().sendText("RemoveUser " + json);
            }
        } catch (NullPointerException ignored) {

        }
    }
}
