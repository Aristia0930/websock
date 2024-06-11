package com.example.demo.config;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Controller;

import java.security.Principal;
import java.util.Map;
import java.util.Set;

@Controller
public class MessageController {

    private final SimpMessagingTemplate messagingTemplate;
    private final SessionRegistry sessionRegistry;

    public MessageController(SimpMessagingTemplate messagingTemplate, SessionRegistry sessionRegistry) {
        this.messagingTemplate = messagingTemplate;
        this.sessionRegistry = sessionRegistry;
    }

    @MessageMapping("/sendMessage")
    public void sendMessage(Principal principal,ChatController.Messages message, SimpMessageHeaderAccessor headerAccessor, Map<String, String> payload) {
//        String sessionId = headerAccessor.getSessionId();
        String messages = payload.get("content");
//        String username = sessionRegistry.getUsername(sessionId);
//        System.out.println("Message from " + username + ": " + message.getContent());
        //        sessionRegistry.unregisterSession(sessionId);
//        sessionRegistry.registerSession(username,principal.getName()); // 일단 다시 만듬.
//        String find=message.getFrom();// 상대방 이름 꺼내기
//
//        if(sessionRegistry.getSessionIds(find)!=null){
//            messagingTemplate.convertAndSendToUser(sessionRegistry.getUsername(find),"/topic/sendMessage/user123", message.getContent());
//        }


//        if (username != null) {
//            System.out.println("존재함");
//            Set<String> sessionIds = sessionRegistry.getSessionIds(username);
////            for (String id : sessionIds) {
////                System.out.println("실행됨, sessionId: " + id);
            System.out.println(principal.getName());
            String na=payload.get("from");
//                System.out.println(message.getFrom());
            messagingTemplate.convertAndSendToUser(na,"/topic/sendMessage/user123", message);
            System.out.println("보내기 완료");
            System.out.println(message);
//            }
//        } else {
//            System.out.println("Username not found for session ID " + sessionId);
//        }
    }
}

//간단ame()을 내용물로 저장하여 사용해본다.