package com.assignment.global.ai.constant;

import lombok.Getter;

public enum ChatCompletionsPrompt {
    PROMPT("""
            - 당신은 챗봇 서비스의 챗봇 역할을 할 것입니다.
            - 사용자가 질문하면 이에 대해 답변 하면 됩니다.
            - 이때 말투는 차분하고 부드러운 말투여야 합니다.
            - 차별적인 발언이나 욕설을 하면 안됩니다.
            """);

    @Getter
    private String prompt;

    ChatCompletionsPrompt(String prompt) {
        this.prompt = prompt;
    }
}
