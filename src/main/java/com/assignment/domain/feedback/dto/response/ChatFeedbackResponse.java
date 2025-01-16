package com.assignment.domain.feedback.dto.response;

import com.assignment.domain.feedback.domain.ChatFeedback;
import lombok.Builder;

import java.time.LocalDateTime;

public record ChatFeedbackResponse(
        String userId,
        String chatId,
        boolean goodOrBad,
        LocalDateTime createdAt,
        String status
) {
    @Builder
    public ChatFeedbackResponse {
    }

    public static ChatFeedbackResponse from(ChatFeedback chatFeedback) {
        return ChatFeedbackResponse.builder()
                .userId(chatFeedback.getUsers().getId())
                .chatId(chatFeedback.getChat().getId())
                .goodOrBad(chatFeedback.isGoodOrBad())
                .createdAt(chatFeedback.getCreatedAt())
                .status(chatFeedback.getStatus())
                .build();
    }
}
