package com.example.codenames.Servlets;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet(name = "LeaderboardServlet", value = "/LeaderboardServlet")
public class LeaderboardServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        //TODO:check that user is authenticated
        request.getRequestDispatcher(ServletUtils.LEADERBOARD_PAGE);
    }
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse resp) throws ServletException, IOException {
        //TODO:check that user is authenticated
        request.getRequestDispatcher(ServletUtils.LEADERBOARD_PAGE);
    }
}
