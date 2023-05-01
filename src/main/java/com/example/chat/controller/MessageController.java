package com.example.chat.controller;

import com.example.chat.entity.ChatMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.messaging.handler.annotation.DestinationVariable;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.stereotype.Controller;
import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.messaging.SessionConnectedEvent;
import org.springframework.web.socket.messaging.SessionDisconnectEvent;

import java.io.IOException;

@Controller
@MessageMapping("/chat")
public class MessageController {

    @MessageMapping("/chat/{roomId}/sendMessage")
  @SendTo("/topic/{roomId}")
  public ChatMessage sendMessage(@DestinationVariable String roomId, @Payload ChatMessage chatMessage) {
    // Send the chat message to the specified room
    return chatMessage;
  }

  @MessageMapping("/chat/{roomId}/join")
  @SendTo("/topic/{roomId}")
  public ChatMessage joinRoom(@DestinationVariable String roomId, @Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
    // Add the user to the room
    headerAccessor.getSessionId();
    headerAccessor.getSessionAttributes().put("username", chatMessage.getSender());
    headerAccessor.getSessionAttributes().put("room_id", roomId);
    //chatSessionRepository.save(new ChatSession(username, roomId, sessionId));
    // Notify all clients in the room that a new user has joined
    return chatMessage;
  }

  @MessageMapping("/chat/{roomId}/leave")
  @SendTo("/topic/{roomId}")
  public ChatMessage leaveRoom(@DestinationVariable String roomId, @Payload ChatMessage chatMessage, SimpMessageHeaderAccessor headerAccessor) {
    // Remove the user from the room
    headerAccessor.getSessionAttributes().remove("username");
    headerAccessor.getSessionAttributes().remove("room_id");

    // Notify all clients in the room that a user has left
    return chatMessage;
  }

    @MessageExceptionHandler
    @SendToUser("/queue/errors")
    public String handleException(Throwable exception) {
        return exception.getMessage();
    }

    @EventListener
    public void handleWebSocketConnectListener(SessionConnectedEvent event) throws IOException {
        String greeting = "Hello, Spring WebSocket!";
        ;

        // Create a new TextMessage with the greeting payload
        TextMessage message = new TextMessage(greeting);

        System.out.println("Received a new web socket connection " + event.getSource() + event.getUser());
    }

    @EventListener
    public void handleWebSocketDisconnectListener(SessionDisconnectEvent event) {
        System.out.println("Disconnected a web socket connection");
    }

}
