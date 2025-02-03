package com.assignment.domain.chat.domain;

import com.assignment.domain.threads.domain.Threads;
import com.assignment.domain.users.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Chat {
    @Id
    private String id;

    @ManyToOne
    @JoinColumn(name = "users_id")
    private Users users;

    @ManyToOne
    @JoinColumn(name = "threads_id")
    private Threads threads;

    private String question;

    @Column(length = 10000)
    private String answer;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    @Builder
    public Chat(String id, Users users, Threads threads, String question, String answer, LocalDateTime createdAt) {
        this.id = id;
        this.users = users;
        this.threads = threads;
        this.question = question;
        this.answer = answer;
        this.createdAt = createdAt;
    }

    @PrePersist
    public void generateId() {
        if (this.id == null) {
            this.id = UUID.randomUUID().toString(); // UUID로 랜덤 ID 생성
        }
    }

    public String getCreatedAtToString() {
        return this.createdAt.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
    }

    public void updateAnswer(String answer) {
        if(this.answer == null) {
            this.answer = answer;
        }
    }
}
