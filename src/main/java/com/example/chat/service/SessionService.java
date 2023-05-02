package com.example.chat.service;

import java.util.List;

import com.example.chat.entity.ChatSession;

public interface SessionService {
    
    ChatSession save(ChatSession chatSession);
    ChatSession findById(String id);
    List<ChatSession> findAll();
}
