package com.example.codenames.listener;

import com.example.codenames.model.Room;
import com.example.codenames.model.User;

import javax.servlet.http.HttpSession;
import javax.websocket.EndpointConfig;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.ServerEndpoint;
import java.util.*;

import static com.example.codenames.listener.NameConstants.SESSION;

@ServerEndpoint(value = "/start-game", configurator = EndpointConfigurator.class)
public class StartGameEndpoint {
    private static Map<String, Set<Session>> sessions = Collections.synchronizedMap(new LinkedHashMap<>());

    @OnOpen
    public void onOpen(Session session, EndpointConfig endpointConfig) throws Exception {
        Map<String, List<String>> parameterMap = session.getRequestParameterMap();
        String roomID = parameterMap.get(NameConstants.ROOM_ID).get(0);
        Set<Session> currentRoomSessions;
        if(sessions.containsKey(roomID)) {
            currentRoomSessions = sessions.get(roomID);
            currentRoomSessions.add(session);
        } else {
            currentRoomSessions = Collections.synchronizedSet(new HashSet<>());
            currentRoomSessions.add(session);
            sessions.put(roomID, currentRoomSessions);
        }
        System.out.println("One user entered");
    }

    @OnMessage
    public void onMessage(Session session, String message){
        if (message.equals("START-GAME")) {
            String roomID = session.getRequestParameterMap().get(NameConstants.ROOM_ID).get(0);
            HttpSession httpSession = (HttpSession) session.getUserProperties().get(SESSION);
            Map<String, Room> roomMap = (Map<String, Room>) httpSession.getServletContext().getAttribute(NameConstants.ROOM_MAP);
            Room room = roomMap.get(roomID);
            User user = (User) httpSession.getAttribute(User.ATTRIBUTE);
            if (room.getOwner().getUser().equals(user)) {
                Set<Session> roomSessions = StartGameEndpoint.sessions.get(roomID);
                for (Session player : roomSessions) {
                    player.getAsyncRemote().sendText("START-GAME");
                }
            } else {
                //
            }
            roomMap.remove(roomID);
        }

    }
}