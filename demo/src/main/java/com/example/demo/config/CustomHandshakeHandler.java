package com.example.demo.config;

//
//import jakarta.servlet.http.HttpSession;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.event.EventListener;
//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.stereotype.Component;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.messaging.SessionConnectEvent;
//import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
//
//import java.security.Principal;
//import java.util.Map;
//import java.util.UUID;
//
//
//public class CustomHandshakeHandler extends DefaultHandshakeHandler {
//
//
//
//    private String id;
//
//
//    @Override
//    protected Principal determineUser(ServerHttpRequest request,
//                                      WebSocketHandler wsHandler,
//                                      Map<String, Object> attributes) {
//
//        System.out.println("aaaa");
//
//
//        return new StompPrincipal(UUID.randomUUID().toString());
//    }
//}

//import org.springframework.http.server.ServerHttpRequest;
//import org.springframework.http.server.ServletServerHttpRequest;
//import org.springframework.web.socket.WebSocketHandler;
//import org.springframework.web.socket.server.support.DefaultHandshakeHandler;
//
//import jakarta.servlet.http.HttpServletRequest;
//import java.security.Principal;
//import java.util.Map;
//import java.util.UUID;
//
//public class CustomHandshakeHandler extends DefaultHandshakeHandler {
//
//    @Override
//    protected Principal determineUser(ServerHttpRequest request,
//                                      WebSocketHandler wsHandler,
//                                      Map<String, Object> attributes) {
//
//        String token = null;
//
//        if (request instanceof ServletServerHttpRequest) {
//            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();
//
//            System.out.println(request.getHeaders());
//            token = servletRequest.getHeader("Authorization"); // Corrected header name
//            System.out.println(token);
//            if (token!= null && token.startsWith("Bearer ")) {
//                token = token.substring(7); // Remove "Bearer " prefix
//                System.out.println(token);
//            }
//        }
//
//        if (token != null ) {
//            String username = token.toString();
//            System.out.println("성공");
//            return new StompPrincipal(username);
//        } else {
//            System.out.println("실패");
//            return new StompPrincipal(UUID.randomUUID().toString());
//        }
//    }
//}

import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import jakarta.servlet.http.HttpServletRequest;
import java.security.Principal;
import java.util.Map;
import java.util.UUID;
public class CustomHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request,
                                      WebSocketHandler wsHandler,
                                      Map<String, Object> attributes) {

        String token = null;

        if (request instanceof ServletServerHttpRequest) {
            HttpServletRequest servletRequest = ((ServletServerHttpRequest) request).getServletRequest();

            token = servletRequest.getHeader("Authorization");
            if (token != null && token.startsWith("Bearer ")) {
                token = token.substring(7); // "Bearer " 접두사 제거
            }
        }

        if (token != null) {
            String username = token; // 간단하게 토큰을 사용자 이름으로 가정
            return new StompPrincipal(username);
        } else {
            return new StompPrincipal(UUID.randomUUID().toString());
        }
    }
}
