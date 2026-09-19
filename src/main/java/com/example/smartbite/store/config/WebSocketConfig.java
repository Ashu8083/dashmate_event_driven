package com.example.smartbite.store.config;

import com.example.smartbite.store.rider_modul.internalModule.ws.RiderWebSocket;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    private final RiderWebSocket riderWebSocket;
    public WebSocketConfig(RiderWebSocket riderWebSocket) {
        this.riderWebSocket = riderWebSocket;
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(riderWebSocket, "/ws/rider").setAllowedOrigins("*");

    }

}
