package com.adote.dto;

import java.util.List;
public class ChatDTOs {
    public record CreateChatRequest(List<String> participantUserIds) {}
    public record SendMessageRequest(String chatId, String content) {}
}
