package com.example.chat.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.chat.entity.ChatSession;

@Repository
public interface ChatSessionRepository extends JpaRepository<ChatSession, String>{
    
}
