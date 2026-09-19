package com.example.smartbite.store.rider_modul.internalModule.ws;

import com.example.smartbite.store.rider_modul.internalModule.service.RiderService;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.annotations.Comment;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

import java.io.IOException;


@Slf4j
@Component
public class RiderWebSocket extends TextWebSocketHandler {

    private final RiderService riderService;
    public RiderWebSocket(RiderService riderService) {
        this.riderService = riderService;
    }

    @Override
    protected void handleTextMessage(
            WebSocketSession session,
            TextMessage message) throws IOException {
        String payload = message.getPayload();
        log.info("Received message: {}", payload);

        session.sendMessage(
                new TextMessage(payload)
        );

    }

}
