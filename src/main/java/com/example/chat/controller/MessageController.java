package com.example.chat.controller;

import com.example.chat.entity.ChatMessage;
import com.example.chat.service.MessageService;

import org.springframework.context.event.EventListener;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.converter.MappingJackson2MessageConverter;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

@Controller
//@Slf4j
public class MessageController {
    
    private MessageService messageService;
    private SimpMessagingTemplate messagingTemplate;

    public MessageController(MessageService messageService, SimpMessagingTemplate messagingTemplate) {
        this.messageService = messageService;
        this.messagingTemplate = messagingTemplate;
    }

    @MessageMapping("/message") // client send message here + app prefix
    @SendTo("/chat/messages") // here we listen to new updates so we need to notify broker for a new message been sent
    public ChatMessage send(ChatMessage message) throws Exception {
        System.out.println(message.getContent());
        messageService.sendMessage(message);
        return message;
    }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) {
        messagingTemplate.convertAndSend("/app/message", "Hello");
        System.out.println("Received a new web socket connection");
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        System.out.println("Disconnected a web socket connection");
    }

}
