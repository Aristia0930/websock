package com.example.demo.config;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfig implements WebSocketMessageBrokerConfigurer {

    //    @Autowired
//    private SessionRegistry sessionRegistry; // SessionRegistry 주입
    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/topic", "/queue");
        config.setApplicationDestinationPrefixes("/app");
        config.setUserDestinationPrefix("/user");

    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
//        CustomHandshakeHandler customHandshakeHandler = new CustomHandshakeHandler(sessionRegistry); // SessionRegistry 주입
        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:3000/").setHandshakeHandler(new CustomHandshakeHandler()).withSockJS();
//        registry.addEndpoint("/ws").setAllowedOrigins("http://localhost:3000/").withSockJS();
        //
        registry.addEndpoint("/wb").setAllowedOrigins("http://localhost:3000/").withSockJS();
    }
}