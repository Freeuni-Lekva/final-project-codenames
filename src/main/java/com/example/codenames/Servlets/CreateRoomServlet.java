package com.example.codenames.Servlets;

import com.example.codenames.listener.NameConstants;
import com.example.codenames.model.Player;
import com.example.codenames.model.PlayerRole;
import com.example.codenames.model.Room;
import com.example.codenames.model.User;
import com.google.gson.Gson;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;
import java.util.Map;

import static com.example.codenames.listener.NameConstants.CATEGORIES_LIST;
import static com.example.codenames.listener.NameConstants.ROOM;

@WebServlet(name = "CreateRoomServlet", value = "/CreateRoomServlet")
public class CreateRoomServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
        room.setCategories((List<String>) request.getSession().getAttribute(CATEGORIES_LIST));
        request.getSession().removeAttribute(CATEGORIES_LIST);
        roomMap.put(randomID, room);
        request.getSession().setAttribute(ROOM, room);
        String json = new Gson().toJson(room);
        request.setAttribute(NameConstants.JSON, json);
        request.getRequestDispatcher("/JSP/waitingRoom.jsp?" + NameConstants.ROOM_ID + "=" + randomID).forward(request, response);
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
