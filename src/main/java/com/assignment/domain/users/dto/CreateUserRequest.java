package com.assignment.domain.users.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;

public record CreateUserRequest(
        @NotNull
        String username,
        @NotNull
        @Email
        String email,
        @NotNull
        String password) {
}
