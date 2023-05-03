package com.example.chat.configuration;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.handler.invocation.HandlerMethodReturnValueHandler;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;
import org.springframework.web.socket.config.annotation.WebSocketMessageBrokerConfigurer;

@Configuration
@EnableWebSocketMessageBroker
public class ChatConfiguration  implements WebSocketMessageBrokerConfigurer {

    @Override
    public void configureMessageBroker(MessageBrokerRegistry config) {
        config.enableSimpleBroker("/chat");// broker will listen to updates and provide new messages for a client
        config.setCacheLimit(1);
        config.setApplicationDestinationPrefixes("/app"); // simple prefix
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/chat-websocket").withSockJS(); // here we need to connect
    }
    
    /*
     * @Bean public SimpMessagingTemplate brokerMessagingTemplate() {
     * SimpMessagingTemplate template = new SimpMessagingTemplate(brokerChannel());
     * String prefix = getBrokerRegistry().getUserDestinationPrefix(); if (prefix !=
     * null) { template.setUserDestinationPrefix(prefix); }
     */

}
