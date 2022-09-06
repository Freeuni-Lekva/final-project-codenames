package com.example.codenames.Servlets;

import com.example.codenames.DTO.UserCredentialsDto;
import com.example.codenames.exception.InvalidCredentialsException;
import com.example.codenames.exception.UserRegistrationException;
import com.example.codenames.listener.NameConstants;
import com.example.codenames.model.User;
import com.example.codenames.service.UserService;

import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext servletContext = getServletContext();
        UserService userService = (UserService) servletContext.getAttribute(NameConstants.USER_SERVICE);
        String username = request.getParameter(NameConstants.USERNAME_PARAMETER);
        String password = request.getParameter(NameConstants.PASSWORD_PARAMETER);
        if(username.isBlank() || password.isBlank()){
            request.getSession().setAttribute(NameConstants.LOGIN_ERROR, "Blank Fields");
            response.setHeader("Refresh", "0; URL=http://localhost:8080/Codenames_war_exploded/");
            return;
        }
        try {
            UserCredentialsDto userCredentialsDto = new UserCredentialsDto(username, password);
            User user = userService.loginUser(userCredentialsDto);
            request.getSession().setAttribute(User.ATTRIBUTE, user);
            System.out.println(user.toString());
            request.getRequestDispatcher(ServletUtils.USER_PAGE).forward(request, response);
        } catch (InvalidCredentialsException e){
            request.getSession().setAttribute(NameConstants.LOGIN_ERROR, e.getMessage());
            response.setHeader("Refresh", "0; URL=http://localhost:8080/Codenames_war_exploded/");
        }
    }
}