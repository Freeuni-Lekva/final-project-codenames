package com.example.codenames.Servlets;

import com.example.codenames.engine.GameEngine;
import com.example.codenames.engine.GameEvent;
import com.example.codenames.listener.NameConstants;
import com.example.codenames.model.Board;
import com.example.codenames.model.WordColor;
import com.example.codenames.service.WordService;
import org.json.JSONObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

@WebServlet(name = "GameplayServlet", value = "/GameplayServlet")
public class GameplayServlet extends HttpServlet {

    public static final ConcurrentHashMap<String, GameEngine> gameEngineByRoomId = new ConcurrentHashMap<>();

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response) throws IOException, ServletException {
        setRequestAttrs(request);
        request.getRequestDispatcher("/JSP/gameplay.jsp")
               .forward(request, response);
    }

    private void setRequestAttrs(HttpServletRequest request) {
        request.setAttribute(NameConstants.ROOM_ID, "ID"); // temp code this should not be needed
        String roomId = getRoomId(request);
        GameEngine gameEngine = gameEngineByRoomId.computeIfAbsent(roomId, this::createBoard);
        request.setAttribute(NameConstants.WORDS, gameEngine.getWords());
    }

    private String getRoomId(HttpServletRequest request) {
        return (String) request.getAttribute(NameConstants.ROOM_ID);
    }

    private GameEngine createBoard(String roomId) {
        List<String> categories = getRequestedWordCategories();
        WordService wordService = (WordService) getServletContext().getAttribute(NameConstants.WORD_SERVICE);
        List<String> words = wordService.getRandomizedWords(categories);
        return new GameEngine(words);
    }

    private static List<String> getRequestedWordCategories() {
        List<String> categories = new ArrayList<>();
        categories.add("SPORTS");
        categories.add("FOOD");
        return categories;
    }
}