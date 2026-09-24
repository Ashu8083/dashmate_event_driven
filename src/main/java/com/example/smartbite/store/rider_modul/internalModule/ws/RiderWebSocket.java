package com.example.smartbite.store.rider_modul.internalModule.ws;

import com.example.smartbite.store.config.webScoket.WebSocketRequest;
import com.example.smartbite.store.rider_modul.internalModule.RiderSessionManager.RiderSessionAManager;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;
import java.util.UUID;


@Slf4j
@Component
public class RiderWebSocket extends TextWebSocketHandler {

    @Autowired
    RiderSessionAManager riderSessionManager;

    @Autowired
    RiderWebSocketMessageDispatcher  dispatcher;

    @Autowired
    ObjectMapper objectMapper;

    @Override
    public void afterConnectionEstablished(WebSocketSession session) {
        UUID riderId =
                (UUID) session.getAttributes().get("riderId");
        riderSessionManager.add(riderId, session);
        log.info("Rider session established with riderId={}", riderId);
    }

    @Override
    protected void handleTextMessage(
            WebSocketSession session, TextMessage message
            ) throws IOException {

        String payload = message.getPayload();
        log.info("Received message: {}", payload);
        WebSocketRequest request = objectMapper.readValue(payload, WebSocketRequest.class);
        dispatcher.dispatch(session,request);
        session.sendMessage(
                new TextMessage(payload)
        );
    }


}
