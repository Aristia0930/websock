//package com.example.demo.config;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.messaging.handler.annotation.MessageMapping;
//import org.springframework.messaging.handler.annotation.Payload;
//import org.springframework.messaging.handler.annotation.SendTo;
//import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
//import org.springframework.messaging.simp.SimpMessagingTemplate;
//import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
//import org.springframework.web.bind.annotation.*;
//import jakarta.servlet.http.HttpSession;
//
//import java.util.Map;
//
//@RestController
//public class TestController {
//
//    @Autowired
//    private SimpMessagingTemplate webSocket;
//
//
//
//    //먼저 client 소켓에서 sendMessage 함수로메시지를 보낼 경우에는
//    // @MessageMapping 어노테이션으로 받을 수 있다.
//    // @RequestMapping을 사용할 경우에는 웹 소켓이 연동된
//    // Client와 무관하게 외부의 GET 질의로 이벤트를 Trigger 할 수 있다.
//
//
//    //sendto로 받는경우는 리턴을 통해 클라로 전달해야한다.
//    @MessageMapping("/sendTo") //이 경로로 날라오면
//    //구독중인곳으로 돌려주는 주소가 아래이다
//    @SendTo("/topics/sendTo")
//    public String SendToMessage() throws Exception {
//
//        return "SendTo";
//    }
//
//    //SimpMessagingTemplate의convertAndSend 함수를 사용하면
//    // 특정 유저에게만 메시지를 보낼 수 있다.
//    //이방식은 리턴값이 없는 void로 처리한다.
//
//    @MessageMapping("/Template")
//    public void SendTemplateMessage(@RequestBody Map<String, String> payload) {
//        String message = payload.get("name");
//        webSocket.convertAndSend("/topics/template/"+message , "Template");
//    }
//
//    @RequestMapping(value="/api")
//    public void SendAPI() {
//        webSocket.convertAndSend("/topics/api" , "API");
//    }
//
//
//
//
//    @MessageMapping("/sendToUser")
//    public void sendToUser(StompHeaderAccessor headerAccessor, @RequestBody Map<String, String> payload) {
//        Map<String, Object> sessionAttributes = headerAccessor.getSessionAttributes();
//        String sessionId = sessionAttributes != null ? (String) sessionAttributes.get("sessionId") : null;
//        String message = payload.get("message");
//
//        if (sessionId != null) {
//            webSocket.convertAndSendToUser(sessionId, "/queue/reply", message);
//        } else {
//            System.out.println("Session ID is not found");
//        }
//    }
//
//    @PostMapping("/name")
//    public void setName(HttpSession session, @RequestBody Map<String, String> data) {
//        String name = data.get("name");
//        System.out.println("Session saved for user: " + name);
//        session.setAttribute("username", name);
//    }
//    @GetMapping("/sessionId")
//    public Map<String, String> getSessionId(HttpSession session) {
//        System.out.println(session.getId());
//        session.setAttribute("sessionId", session.getId());
//        return Map.of("sessionId", session.getId());
//    }
//}
