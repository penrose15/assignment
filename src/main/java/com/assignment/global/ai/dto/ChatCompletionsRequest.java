package com.assignment.global.ai.dto;

import com.assignment.global.ai.constant.ChatCompletionsPrompt;
import com.assignment.global.ai.constant.ChatCompletionsRole;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatCompletionsRequest {
    private String role;
    private String content;

    @Builder
    public ChatCompletionsRequest(String role, String content) {
        this.role = role;
        this.content = content;
    }

    public static ChatCompletionsRequest prompt() {
        return ChatCompletionsRequest.builder()
                .role(ChatCompletionsRole.DEVELOPER.getRole())
                .content(ChatCompletionsPrompt.PROMPT.getPrompt())
                .build();
    }
}
