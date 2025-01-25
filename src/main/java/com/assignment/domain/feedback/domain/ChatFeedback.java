package com.assignment.domain.feedback.domain;

import com.assignment.domain.chat.domain.Chat;
import com.assignment.domain.users.domain.Users;
import jakarta.persistence.*;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;

@Getter
@Entity
@EntityListeners(AuditingEntityListener.class)
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class ChatFeedback {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private Users users;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "chat_id", foreignKey = @ForeignKey(ConstraintMode.NO_CONSTRAINT))
    private Chat chat;

    private boolean goodOrBad;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime createdAt;

    private String status;

    @Builder
    public ChatFeedback(Users users, Chat chat, boolean goodOrBad, LocalDateTime createdAt) {
        this.users = users;
        this.chat = chat;
        this.goodOrBad = goodOrBad;
        this.createdAt = createdAt;
        this.status = "PENDING";
    }

    public void updateStatus() {
        if(this.status.equals("PENDING")) {
            this.status = "RESOLVED";
        }
    }
}
