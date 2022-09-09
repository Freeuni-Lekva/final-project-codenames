package com.example.codenames.Servlets;

import com.example.codenames.listener.NameConstants;
import com.example.codenames.model.Room;
import com.example.codenames.model.User;

import javax.naming.Name;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static com.example.codenames.listener.NameConstants.CATEGORIES_LIST;
import static com.example.codenames.listener.NameConstants.ROOM;

@WebServlet(name = "ChooseCategoriesServlet", value = "/ChooseCategoriesServlet")
public class ChooseCategoriesServlet extends HttpServlet {
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        ServletContext servletContext = request.getServletContext();
        Map<String, Room> roomMap = (Map<String, Room>) servletContext.getAttribute(NameConstants.ROOM_MAP);
        User user = (User) request.getSession().getAttribute(User.ATTRIBUTE);
        if(request.getSession().getAttribute(ROOM) != null){
            response.getWriter().println("You are already in a different room.");
            return;
        }
        Map<String, String[]> parameterMap = request.getParameterMap();
        List<String> categories = parameterMap.keySet().stream().map( e -> parameterMap.get(e)[0]).collect(Collectors.toList());
        if(categories.isEmpty()){
            categories.add("ALL");
        }
        request.getSession().setAttribute(CATEGORIES_LIST, categories);
        response.sendRedirect("CreateRoomServlet");
    }
}
