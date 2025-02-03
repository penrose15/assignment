package com.assignment.domain.chat.dto.chatcompletions;

import com.assignment.domain.chat.constant.ChatCompletionsPrompt;
import com.assignment.domain.chat.constant.ChatCompletionsRole;
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
