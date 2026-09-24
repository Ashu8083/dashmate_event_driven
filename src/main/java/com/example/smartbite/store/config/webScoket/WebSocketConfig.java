package com.example.smartbite.store.config.webScoket;

import com.example.smartbite.store.rider_modul.internalModule.ws.RiderWebSocket;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;


@EnableWebSocket
@Configuration
public class WebSocketConfig implements WebSocketConfigurer {

    private final RiderWebSocket riderWebSocket;
    private final WebSocketAuthInterceptor interceptor;
    public WebSocketConfig(RiderWebSocket riderWebSocket
                           , WebSocketAuthInterceptor interceptor
    ) {
        this.riderWebSocket = riderWebSocket;
        this.interceptor = interceptor;
    }


    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry registry) {
        registry.addHandler(riderWebSocket, "/ws/rider")
                .addInterceptors(interceptor)
                .setAllowedOrigins("*");

    }

}
