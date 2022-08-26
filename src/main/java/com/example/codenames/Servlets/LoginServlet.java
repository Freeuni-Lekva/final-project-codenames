package com.example.codenames.Servlets;

import com.example.codenames.DTO.UserCredentialsDto;
import com.example.codenames.listener.NameConstants;
import com.example.codenames.model.User;
import com.example.codenames.service.UserService;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "LoginServlet", value = "/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        String username = request.getParameter("Username");
        String password = request.getParameter("Password");
        System.out.println("Username: " + username);
        System.out.println("Password: " + password);
        UserService userService = (UserService) getServletContext()
                .getAttribute(NameConstants.USER_SERVICE);
        User user = userService.loginUser(new UserCredentialsDto(username, password));
        if (user == null){
            System.out.println("-------------------------------------------ERROR--------------");
        }else{
            System.out.println(user);
        }
    }
}