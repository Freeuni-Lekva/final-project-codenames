package com.example.codenames.Servlets;

import com.example.codenames.engine.GameEngine;
import com.example.codenames.engine.GameEvent;
import com.example.codenames.listener.NameConstants;
import com.example.codenames.model.Board;
import com.example.codenames.model.Room;
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
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import static com.example.codenames.listener.NameConstants.ROOM_MAP;

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
        String roomID = request.getParameter(NameConstants.ROOM_ID);
        request.setAttribute(NameConstants.ROOM_ID, roomID);
        GameEngine gameEngine = gameEngineByRoomId.computeIfAbsent(roomID, (id) -> createBoard(request, id));
        Room room = (Room)(request.getServletContext().getAttribute(roomID));
        gameEngine.setRoom(room);
        List<WordColor> colors = gameEngine.getColors();
        GameEvent startEvent = gameEngine.startEvent(request.getSession());
        boolean isSpy = startEvent.isSpy();
        request.setAttribute("colors", colors);
        request.setAttribute("isSpy", isSpy);
        request.setAttribute("myRoom", gameEngine.getRoom());
        request.setAttribute(NameConstants.WORDS, gameEngine.getWords());
    }

    private String getRoomId(HttpServletRequest request) {
        return (String) request.getParameter(NameConstants.ROOM_ID);
    }

    private GameEngine createBoard(HttpServletRequest request, String roomId) {
        List<String> categories = getRequestedWordCategories(request);
        WordService wordService = (WordService) getServletContext().getAttribute(NameConstants.WORD_SERVICE);
        List<String> words = wordService.getRandomizedWords(categories);
        return new GameEngine(words);
    }

    private List<String> getRequestedWordCategories(HttpServletRequest request) {
        Map<String, Room> roomMap = (Map<String, Room>) request.getServletContext().getAttribute(ROOM_MAP);
        Room room = roomMap.get(getRoomId(request));
        return room.getCategories();
    }
}