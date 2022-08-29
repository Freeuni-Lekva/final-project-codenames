package com.example.codenames.Servlets;

import com.example.codenames.DTO.UserCredentialsDto;
import com.example.codenames.exception.UserNameRepeatedException;
import com.example.codenames.exception.UserPasswordException;
import com.example.codenames.exception.UserRegistrationException;
import com.example.codenames.listener.NameConstants;
import com.example.codenames.model.User;
import com.example.codenames.service.UserService;

import java.io.*;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "RegistrationServlet", value = "/RegistrationServlet")
public class RegistrationServlet extends HttpServlet {


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext servletContext = getServletContext();
        UserService userService = (UserService) servletContext.getAttribute(NameConstants.USER_SERVICE);
        String username = request.getParameter(NameConstants.USERNAME_PARAMETER);
        String password = request.getParameter(NameConstants.PASSWORD_PARAMETER);
        if(username.isBlank() || password.isBlank()){
            request.setAttribute(NameConstants.REGISTRATION_ERROR, "Blank Fields");
            request.getRequestDispatcher("JSP/register.jsp").forward(request, response);
        }
        try {
            UserCredentialsDto userCredentialsDto = new UserCredentialsDto(username, password);
            User user = userService.registerUser(userCredentialsDto);
            request.getSession().setAttribute(User.ATTRIBUTE, user);
            //??
        } catch (UserRegistrationException e){
            request.setAttribute(NameConstants.REGISTRATION_ERROR, e.getMessage());
            request.getRequestDispatcher("JSP/register.jsp").forward(request, response);
        }
    }
}