package com.assignment.domain.chat.dto.request;

public record ChatQuestionRequest(String question) {
    public ChatQuestionRequest {
        if (question == null || question.isEmpty()) {
            throw new IllegalArgumentException("Question cannot be null or empty");
        }
    }
}
