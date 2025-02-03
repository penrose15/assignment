package com.assignment.domain.chat.dto.chatcompletions;

import com.assignment.domain.chat.domain.Chat;

public record ChatMessage(String id, String message, Chat chat) {
}
