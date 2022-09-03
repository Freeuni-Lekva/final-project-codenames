package com.example.codenames.Servlets;

import com.example.codenames.listener.NameConstants;
import com.example.codenames.model.Player;
import com.example.codenames.model.Room;
import com.example.codenames.model.User;
import com.google.gson.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Map;

@WebServlet(name = "JoinRoomServlet", value = "/JoinRoomServlet")
public class JoinRoomServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        User user = (User) request.getSession().getAttribute(User.ATTRIBUTE);
        ServletContext servletContext = request.getServletContext();
        Map<String, Room> roomMap = (Map<String, Room>) servletContext.getAttribute(NameConstants.ROOM_MAP);
        String roomID = (String) request.getParameter(NameConstants.ROOM_ID);
        System.out.println(roomID);
        if(roomMap.containsKey(roomID)){
            System.out.println("contains");
            Room room = roomMap.get(roomID);
            Player player = new Player(user, roomID);
            room.addPlayer(player);
            String json = new Gson().toJson(room);
            request.setAttribute(NameConstants.JSON, json);
            request.getRequestDispatcher("/JSP/waitingRoom.jsp?" + NameConstants.ROOM_ID + "=" + roomID).forward(request, response);
        } else {
            System.out.println("doesnt");
        }
    }

}
