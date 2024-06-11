package com.example.demo.config;


import jakarta.servlet.http.HttpSession;
//import org.springframework.context.event.EventListener;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.messaging.SessionConnectEvent;
//import org.springframework.web.socket.messaging.SessionDisconnectEvent;
//
//@Component
//public class WebSocketEventListener {
//
//    private final SessionRegistry sessionRegistry;
//
//
//    public WebSocketEventListener(SessionRegistry sessionRegistry) {
//        this.sessionRegistry = sessionRegistry;
//
//    }
//
//
//
//    @EventListener
//    public void handleWebSocketConnectListener(SessionConnectEvent event) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
//        String sessionId = accessor.getSessionId();
//        String username = accessor.getNativeHeader("Authorization").get(0);
//        sessionRegistry.registerSession(sessionId, username);
//    }
//
//    @EventListener
//    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
//        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
//        String sessionId = accessor.getSessionId();
//        sessionRegistry.unregisterSession(sessionId);
//
//
//    }
//}

import org.springframework.context.event.EventListener;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.messaging.SessionConnectEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.util.List;


@Component
public class WebSocketEventListener {

    private final SessionRegistry sessionRegistry;

    public WebSocketEventListener(SessionRegistry sessionRegistry) {
        this.sessionRegistry = sessionRegistry;
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        List<String> authorizationHeaders = accessor.getNativeHeader("Authorization");
        if (authorizationHeaders != null && !authorizationHeaders.isEmpty()) {
            String username = authorizationHeaders.get(0).substring(7); // Remove "Bearer " prefix
            sessionRegistry.registerSession(sessionId, username);
        }
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        StompHeaderAccessor accessor = StompHeaderAccessor.wrap(event.getMessage());
        String sessionId = accessor.getSessionId();
        sessionRegistry.unregisterSession(sessionId);
    }
}