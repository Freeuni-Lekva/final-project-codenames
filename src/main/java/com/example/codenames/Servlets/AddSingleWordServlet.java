package com.example.codenames.Servlets;


import com.example.codenames.DTO.UserCredentialsDto;
import com.example.codenames.exception.InvalidCredentialsException;
import com.example.codenames.exception.UserNotFoundException;
import com.example.codenames.listener.NameConstants;
import com.example.codenames.model.Role;
import com.example.codenames.model.User;
import com.example.codenames.service.UserService;
import com.example.codenames.service.WordService;
import com.example.codenames.service.implementation.WordServiceImpl;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "AddSingleWordServlet", value = "/AddSingleWordServlet")
public class AddSingleWordServlet extends HttpServlet {


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
        else{
            WordServiceImpl wordService = (WordServiceImpl) servletContext.getAttribute(NameConstants.WORD_SERVICE);
            String word = request.getParameter(NameConstants.ADD_WORD_PARAMETER);
            String category = request.getParameter(NameConstants.ADD_CATEGORY_PARAMETER);
            try {
                wordService.addWord(word,category);
                request.getRequestDispatcher(ServletUtils.ADD_WORDS_PAGE).forward(request, response);
            } catch (UserNotFoundException e){
                e.printStackTrace();
            }
        }
    }



}
