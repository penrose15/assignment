package com.assignment.domain.chat.constant;

import lombok.Getter;

@Getter
public enum OpenAiModel {
    GPT_4O("gpt-4o");

    private final String model;

    OpenAiModel(String model) {
        this.model = model;
    }
}
