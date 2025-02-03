package com.assignment.domain.users.domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.util.UUID;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Users {

    @Id
    private String id;

    @NotNull
    private String email;

    @NotNull
    private String password;

    @NotNull
    private String username;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private String role;

    @Builder
    public Users(String id, String email, String password, String username, LocalDateTime createdAt, String role) {
        this.id = id;
        this.email = email;
        this.password = password;
        this.username = username;
        this.createdAt = createdAt;
        this.role = role;
    }

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString(); // UUID로 랜덤 ID 생성
        }
    }
}
