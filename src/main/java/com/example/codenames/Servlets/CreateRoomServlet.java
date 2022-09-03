package com.example.codenames.Servlets;

import com.example.codenames.listener.NameConstants;
import com.example.codenames.model.Player;
import com.example.codenames.model.PlayerRole;
import com.example.codenames.model.Room;
import com.example.codenames.model.User;

import javax.servlet.ServletContext;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

@WebServlet(name = "CreateRoomServlet", value = "/CreateRoomServlet")
public class CreateRoomServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) {
        User user = (User) request.getSession().getAttribute(User.ATTRIBUTE);
        ServletContext servletContext = request.getServletContext();
        Map<String, Room> roomMap = (Map<String, Room>) servletContext.getAttribute(NameConstants.ROOM_MAP);
        String randomID;
        while (true) {
            randomID = randomizeID(5);
            if (!roomMap.containsKey(randomID)) break;
        }
        Player player = new Player(user, randomID, PlayerRole.NOT_SELECTED);
        Room room = new Room(player, randomID);
        roomMap.put(randomID, room);
        request.getRequestDispatcher("JSP/waitingRoom.jsp?roomID=" + randomID);
    }

    private String randomizeID(int length) {
        String str = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
                + "0123456789"
                + "abcdefghijklmnopqrstuvxyz";
        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int index = (int)(str.length() * Math.random());

            sb.append(str.charAt(index));
        }
        return sb.toString();
    }
}
