package com.example.codenames.Servlets;


import com.example.codenames.DTO.UserCredentialsDto;
import com.example.codenames.exception.InvalidCredentialsException;
import com.example.codenames.exception.UserNotFoundException;
import com.example.codenames.listener.NameConstants;
import com.example.codenames.model.Role;
import com.example.codenames.model.User;
import com.example.codenames.service.UserService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddWordsServlet", value = "/AddWordsServlet")
public class AddWordsServlet extends HttpServlet {


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        User user = (User)request.getSession().getAttribute(User.ATTRIBUTE);
        if(user == null){
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        else if(user.getRole().equals(Role.PLAYER)){
            response.sendRedirect(ServletUtils.USER_PAGE);
        }
        else {
            try {
                request.getRequestDispatcher(ServletUtils.ADD_WORDS_PAGE).forward(request, response);
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



}
