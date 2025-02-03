package com.assignment.domain.chat.repository;

import com.assignment.domain.chat.domain.Chat;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface ChatRepository extends JpaRepository<Chat, String> {

    @Query("SELECT c FROM Chat c WHERE c.users.id = :userId order By c.createdAt DESC LIMIT 1")
    Optional<Chat> findByUsersIdOrderByCreatedAtDesc(String userId);

    @Query("SELECT c FROM Chat c WHERE c.users.id = :userId ORDER BY c.createdAt")
    Page<Chat> findAllByUsersIdOrderByCreatedAt(String userId, Pageable pageable);

    @Query("SELECT c FROM Chat c WHERE c.users.id = :userId ORDER BY c.createdAt DESC")
    Page<Chat> findAllByUsersIdOrderByCreatedAtDesc(String userId, Pageable pageable);

    @Query("SELECT c FROM Chat c WHERE c.threads.id = :threadId")
    List<Chat> findAllByThreadId(Long threadId);

    @Modifying
    @Query("DELETE FROM Chat c WHERE c.threads.id = :threadId")
    void deleteAllByThreadId(Long threadId);
}
