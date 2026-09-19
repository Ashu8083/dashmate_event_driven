package com.example.smartbite.store.config;

import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.UUID;

import static org.apache.kafka.common.serialization.Serdes.UUID;


@Component
public class WebSocketAuthInterceptor  implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes){

        String authHeader =
                request.getHeaders().getFirst("Authorization");
        // For now, assume you extracted riderId from the header/JWT
        UUID riderId =  UUID.fromString(authHeader) ;
        attributes.put("userID", riderId);
        return true;

    }
    @Override
    public void afterHandshake(ServerHttpRequest request
                            , ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {

    }

}
