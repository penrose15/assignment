package com.assignment.global.ai.dto;

import com.assignment.global.ai.constant.OpenAiModel;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class OpenAiRequest {
    private List<ChatCompletionsRequest> messages;
    private String model;
    private boolean stream;

    @Builder
    public OpenAiRequest(List<ChatCompletionsRequest> messages, boolean stream) {
        this.stream = stream;
        validateChatCompletionsRequest(messages);
        this.messages = messages;
        this.model = OpenAiModel.GPT_4O.getModel();
    }

    private void validateChatCompletionsRequest(List<ChatCompletionsRequest> messages) {
        if (messages == null || messages.isEmpty()) {
            throw new IllegalArgumentException("Chat completions cannot be null or empty");
        }
    }
}
