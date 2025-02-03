package com.assignment.global.ai.dto;

import java.util.List;

public record ChatCompletionsResponse(
        String id,
        String object,
        Long created,
        String model,
        String system_fingerprint,
        List<Choice> choices) {

    public record Choice(
            Delta delta,
            Integer index,
            String finish_reason,
            String logprobs) {
        public record Delta(String content) {

        }

    }
}
