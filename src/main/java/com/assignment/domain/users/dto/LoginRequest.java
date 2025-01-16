package com.assignment.domain.users.dto;

public record LoginRequest(String email, String password) {
    public LoginRequest {
        if(email == null || password == null) {
            throw new IllegalArgumentException("Email and password cannot be null");
        }
    }
}
