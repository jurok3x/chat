package com.example.chat.bot;

import com.example.chat.controller.MessageController;
import com.example.chat.entity.ChatMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

import jakarta.annotation.PostConstruct;

@Component
public class TelegramBot extends TelegramLongPollingBot {
    
    @Autowired
    private SimpMessagingTemplate messagingTemplate;

    @PostConstruct
    private void init() {
        registerBot();
    }

    @Override
    public String getBotUsername() {
        return "ykotsiuba_testbot";
    }

    @Override
    public String getBotToken() {
        return "5941930280:AAH3q00kLTk0c0jTiEHBvfz1zleSpbIJMFc";
    }

    @Override
    public void onUpdateReceived(Update update) {
        ChatMessage message = new ChatMessage();
        message.setSender(update.getMessage().getFrom().getUserName());
        message.setContent(update.getMessage().getText());
        messagingTemplate.convertAndSend("/chat/messages", message);
    }
    
    private void registerBot() {
        try {
            TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
            telegramBotsApi.registerBot(this);
          } catch (Exception e) {
            e.printStackTrace();
          }
    }

}
