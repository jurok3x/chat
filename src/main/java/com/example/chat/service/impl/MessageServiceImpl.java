package com.example.chat.service.impl;

import com.example.chat.bot.TelegramBot;
import com.example.chat.entity.ChatMessage;
import com.example.chat.service.MessageService;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Service
public class MessageServiceImpl implements MessageService {
    
    public static final String CHAT_ID = "-857777285";
    private TelegramBot bot;

    public MessageServiceImpl(TelegramBot bot) {
        this.bot = bot;
    }

    @Override
    public void sendMessage(ChatMessage message) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setText(message.getContent());
        sendMessage.setChatId(CHAT_ID);
        executeCommand(sendMessage);
    }
    
    private void executeCommand(SendMessage sendMessage) {
        try {
            bot.execute(sendMessage);
        } catch (TelegramApiException e) {
            System.out.println(String.format("Error sending message. Reason: %s", e.getMessage()));
        }
    }

}
