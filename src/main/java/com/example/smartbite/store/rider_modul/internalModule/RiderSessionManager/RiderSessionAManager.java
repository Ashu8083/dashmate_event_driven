package com.example.smartbite.store.rider_modul.internalModule.RiderSessionManager;

import org.springframework.stereotype.Component;
import org.springframework.web.socket.WebSocketSession;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;


@Component
public class RiderSessionAManager {

    private final Map<UUID, WebSocketSession> sessions =
            new ConcurrentHashMap<>();
    public void add(UUID riderId, WebSocketSession session) {
        sessions.put(riderId, session);
    }
    public void remove(UUID riderId) {
        sessions.remove(riderId);
    }
    public WebSocketSession get(UUID riderId) {

        return sessions.get(riderId);

    }
}
