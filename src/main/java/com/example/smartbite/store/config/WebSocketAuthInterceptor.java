package com.example.smartbite.store.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;

import java.util.Map;
import java.util.UUID;



@Slf4j
@Component
public class WebSocketAuthInterceptor  implements HandshakeInterceptor {

    @Override
    public boolean beforeHandshake(
            ServerHttpRequest request,
            ServerHttpResponse response,
            WebSocketHandler wsHandler,
            Map<String, Object> attributes){

        String authHeader =
                request.getHeaders().getFirst("userId");
        // For now, assume you extracted riderId from the header/JWT
        log.info("Inside before handshake ");
        UUID riderId =  UUID.fromString(authHeader) ;
        attributes.put("riderId", riderId);
        return true;

    }
    @Override
    public void afterHandshake(ServerHttpRequest request
                            , ServerHttpResponse response,
                               WebSocketHandler wsHandler, Exception exception) {

    }

}
