package com.example.codenames.Servlets;


import com.example.codenames.DTO.UserCredentialsDto;
import com.example.codenames.exception.InvalidCredentialsException;
import com.example.codenames.exception.UserNotFoundException;
import com.example.codenames.listener.NameConstants;
import com.example.codenames.model.User;
import com.example.codenames.service.UserService;
import com.example.codenames.service.WordService;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "DeleteSingleWordServlet", value = "/DeleteSingleWordServlet")
public class DeleteSingleWordServlet extends HttpServlet {


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext servletContext = getServletContext();
        WordService service = (WordService) servletContext.getAttribute(NameConstants.WORD_SERVICE);
        String word = request.getParameter(NameConstants.WORDS);
        try {
            service.removeWord(word);
            response.sendRedirect(ServletUtils.DELETE_WORDS_PAGE);
        } catch (UserNotFoundException e){
            e.printStackTrace();
        }
    }



}
