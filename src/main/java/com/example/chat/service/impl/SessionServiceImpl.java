package com.example.chat.service.impl;

import java.util.List;

import org.springframework.stereotype.Service;

import com.example.chat.entity.ChatSession;
import com.example.chat.repository.ChatSessionRepository;
import com.example.chat.service.SessionService;

import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SessionServiceImpl implements SessionService {

    private ChatSessionRepository sessionRepository;

    @Override
    public ChatSession save(ChatSession chatSession) {
        return sessionRepository.save(chatSession);
    }

    @Override
    public ChatSession findById(String id) {
        return sessionRepository.findById(id).orElse(null);
    }

    @Override
    public List<ChatSession> findAll() {
        return sessionRepository.findAll();
    }
    
}
