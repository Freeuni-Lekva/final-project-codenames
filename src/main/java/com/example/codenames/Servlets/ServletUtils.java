package com.example.codenames.Servlets;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.UnsupportedEncodingException;

public class ServletUtils {

    public static final String LEADERBOARD_PAGE = "JSP/leaderboard.jsp";
    public static final String USER_PAGE = "JSP/userPage.jsp";




    protected static void setEncoding(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException {
        response.setContentType("text/html;charset=UTF-8");
        request.setCharacterEncoding("UTF-8");
    }
}
