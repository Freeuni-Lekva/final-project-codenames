package com.example.codenames.listener;
import com.example.codenames.model.Player;
import com.example.codenames.model.Room;
import com.example.codenames.model.User;
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

    }

    @OnClose
    public void onClose(Session session) throws Exception {
        String roomID = session.getRequestParameterMap().get(NameConstants.ROOM_ID).get(0);
        Set<Session> sessions = sessionsMap.get(roomID);
        sessions.remove(session);
        HttpSession httpSession = (HttpSession) session.getUserProperties().get(SESSION);
        Map<String, Room> roomMap = (Map<String, Room>) httpSession.getServletContext().getAttribute(NameConstants.ROOM_MAP);
        Room room = roomMap.get(roomID);
        User user = (User) httpSession.getAttribute(User.ATTRIBUTE);
        System.out.println(room.removePlayer(new Player(user, roomID)));
        String json = new Gson().toJson(room);
        for(Session playerSession : sessions){
            playerSession.getAsyncRemote().sendText("RemoveUser " + json);
            System.out.println("SENT!");
        }
    }
}
