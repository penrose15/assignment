package com.assignment.global.ai.dto;

import com.assignment.domain.chat.domain.Chat;

public record ChatMessage(String id, String message, Chat chat) {
}
