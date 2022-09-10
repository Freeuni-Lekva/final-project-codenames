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

@WebServlet(name = "DeleteSingleWordFromCategoryServlet", value = "/DeleteSingleWordFromCategoryServlet")
public class DeleteSingleWordFromCategoryServlet extends HttpServlet {


    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        ServletContext servletContext = getServletContext();
        WordService service = (WordService) servletContext.getAttribute(NameConstants.WORD_SERVICE);
        String word = request.getParameter(NameConstants.WORDS);
        String category =request.getParameter(NameConstants.DELETE_FROM_CATEGORY);
        try {
            service.removeWordFromCategory(word, category);
            response.sendRedirect(ServletUtils.DELETE_WORDS_FROM_CAT_PAGE);
        } catch (UserNotFoundException e){
            e.printStackTrace();
        }
    }



}
