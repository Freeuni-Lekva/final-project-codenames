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

@WebServlet(name = "DeleteUserServlet", value = "/DeleteUserServlet")
public class DeleteUserServlet extends HttpServlet {


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext servletContext = getServletContext();
        User user = (User)request.getSession().getAttribute(User.ATTRIBUTE);
        if(user == null){
            request.getRequestDispatcher("index.jsp").forward(request, response);
        }
        else if(user.getRole().equals(Role.PLAYER)){
            response.sendRedirect(ServletUtils.USER_PAGE);
        }
        else {
            UserService userService = (UserService) servletContext.getAttribute(NameConstants.USER_SERVICE);
            int id = Integer.valueOf(request.getParameter(NameConstants.USER_ID));
            try {
                userService.deleteUser(id);
                response.sendRedirect(ServletUtils.DELETE_PAGE);
            } catch (UserNotFoundException e) {
                e.printStackTrace();
            }
        }
    }



}
