package com.example.codenames.Servlets;

import com.example.codenames.listener.NameConstants;
import com.example.codenames.model.Player;
import com.example.codenames.model.PlayerRole;
import com.example.codenames.model.Room;
import com.example.codenames.model.User;
import com.google.gson.Gson;

import javax.naming.Name;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

import static com.example.codenames.listener.NameConstants.ROOM;

@WebServlet(name = "JoinRandomRoomServlet", value = "/JoinRandomRoomServlet")
public class JoinRandomRoomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = request.getServletContext();
        Map<String, Room> roomMap = (Map<String, Room>) servletContext.getAttribute(NameConstants.ROOM_MAP);
        User user = (User) request.getSession().getAttribute(User.ATTRIBUTE);
        if(request.getSession().getAttribute(ROOM) != null){
            response.getWriter().println("You are already in a different room.");
            return;
        }
        Player player = new Player(user, null, PlayerRole.NOT_SELECTED);
        boolean roomFound = false;
        for (String ID : roomMap.keySet()) {
            if (!roomMap.get(ID).isAvailable()) continue;
            if (roomMap.get(ID).addPlayer(player)) {
                roomFound = true;
                player.setRoomID(ID);
                Room room = roomMap.get(ID);
                request.getSession().setAttribute(ROOM, room);
                String json = new Gson().toJson(room);
                request.setAttribute(NameConstants.JSON, json);
                request.getRequestDispatcher("/JSP/waitingRoom.jsp?" + NameConstants.ROOM_ID + "=" + ID).forward(request, response);
                break;
            }
        }
        if (!roomFound) response.getWriter().print("No available room found, please try again");
    }
}
