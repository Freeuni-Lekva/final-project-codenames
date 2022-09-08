package com.example.codenames.Servlets;

import com.example.codenames.DTO.UserCredentialsDto;
import com.example.codenames.exception.InvalidCredentialsException;
import com.example.codenames.exception.UserRegistrationException;
import com.example.codenames.listener.NameConstants;
import com.example.codenames.model.Board;
import com.example.codenames.model.User;
import com.example.codenames.model.Word;
import com.example.codenames.model.WordColor;
import com.example.codenames.service.UserService;
import com.example.codenames.service.WordService;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.json.JSONObject;

import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "GameplayServlet", value = "/GameplayServlet")
public class GameplayServlet extends HttpServlet {

    @Override
    public void doPost(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        List<String> categories = new ArrayList<>();
        categories.add("SPORTS");
        categories.add("FOOD");
        WordService wordService = (WordService) getServletContext().getAttribute(NameConstants.WORD_SERVICE);
        List<String> words = wordService.getRandomizedWords(categories);
        List<WordColor> wordColors = wordService.getScheme();
        Board board = new Board(words, wordColors);
        request.setAttribute("BOARD", board);
        JSONObject json = new JSONObject();
        for(int i = 0; i < 25; i++) {
            String color = "";
            if(wordColors.get(i) == WordColor.BLUE){
                color = "blue";
            } else if(wordColors.get(i) == WordColor.RED){
                color = "red";
            } else if(wordColors.get(i) == WordColor.BLACK){
                color = "black";
            } else if(wordColors.get(i) == WordColor.BEIGE){
                color = "yellow";
            }
            json.put(Integer.toString(i), color);
        }
        request.setAttribute("colors", json);
        request.getRequestDispatcher("/WEB-INF/gameplay.jsp").forward(request, response);
    }
}