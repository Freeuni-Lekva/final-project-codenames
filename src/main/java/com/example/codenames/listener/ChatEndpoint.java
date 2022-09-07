package com.example.codenames.listener;
import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

@ServerEndpoint(
        value = "/chat", configurator = ChatConfigurator.class)
public class ChatEndpoint {

    @OnOpen
    public void onOpen(Session session) throws SessionException {


    }

    @OnMessage
    public void onMessage(Session session, String message) {

    }

    @OnClose
    public void onClose(Session session) {

    }

    @OnError
    public void OnError(Session session, Throwable t) {

    }


}
