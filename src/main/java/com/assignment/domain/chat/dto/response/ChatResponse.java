package com.assignment.domain.chat.dto.response;

import com.assignment.domain.chat.domain.Chat;
import lombok.Builder;

public record ChatResponse(
        String id,
        Long threadId,
        String question,
        String answer,
        String createdAt) {

    @Builder
    public ChatResponse {
    }

    public static ChatResponse of(Chat chat) {
        return ChatResponse.builder()
                .id(chat.getId())
                .threadId(chat.getThreads().getId())
                .question(chat.getQuestion())
                .answer(chat.getAnswer())
                .createdAt(chat.getCreatedAtToString())
                .build();
    }
}
