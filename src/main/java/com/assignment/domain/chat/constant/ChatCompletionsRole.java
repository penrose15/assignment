package com.assignment.domain.chat.constant;

import lombok.Getter;

public enum ChatCompletionsRole {
    DEVELOPER("developer"),
    USER("user");

    @Getter
    private final String role;

    ChatCompletionsRole(String role) {
        this.role = role;
    }
}
