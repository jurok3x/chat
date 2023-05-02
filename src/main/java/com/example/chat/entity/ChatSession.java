package com.example.chat.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class ChatSession {
    @Id 
    private String roomId;
    @Column(name = "username")
    private String username;
    @Column(name = "session_id")
    private String sessionId;
    @Column(name = "status")
    private SessionStatus status;
}
