package com.assignment.global.ai.constant;

import lombok.Getter;

@Getter
public enum OpenAiModel {
    GPT_4O("gpt-4o");

    private final String model;

    OpenAiModel(String model) {
        this.model = model;
    }
}
