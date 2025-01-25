package com.assignment.domain.feedback.repository;

import com.assignment.domain.feedback.domain.ChatFeedback;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface ChatFeedBackRepository extends JpaRepository<ChatFeedback, Long> {

    @Query("SELECT cf " +
            "from ChatFeedback cf " +
            "WHERE cf.chat.id = :chatId AND cf.users.id = :userId")
    Optional<ChatFeedback> findByChatIdAndUserId(String chatId, String userId);

    @Query("SELECT cf FROM ChatFeedback cf " +
            "WHERE cf.users.id = :userId " +
            "AND (:goodOrBad IS NULL OR cf.goodOrBad = :goodOrBad)" +
            "order by cf.createdAt DESC")
    Page<ChatFeedback> findAllByUserIdByGoodOrBadOrderByCreatedAtDesc(String userId, Boolean goodOrBad, Pageable pageable);

    @Query("SELECT cf FROM ChatFeedback cf " +
            "WHERE cf.users.id = :userId " +
            "AND (:goodOrBad IS NULL OR cf.goodOrBad = :goodOrBad)" +
            "order by cf.createdAt")
    Page<ChatFeedback> findAllByUserIdByGoodOrBadOrderByCreatedAt(String userId, Boolean goodOrBad,Pageable pageable);

    @Query("SELECT cf FROM ChatFeedback cf " +
            "WHERE (:goodOrBad IS NULL OR cf.goodOrBad = :goodOrBad)" +
            "order by cf.createdAt DESC")
    Page<ChatFeedback> findAllOrderByCreatedAtDesc(Boolean goodOrBad,Pageable pageable);

    @Query("SELECT cf FROM ChatFeedback cf " +
            "WHERE (:goodOrBad IS NULL OR cf.goodOrBad = :goodOrBad)" +
            "order by cf.createdAt")
    Page<ChatFeedback> findAllOrderByCreatedAt(Boolean goodOrBad,Pageable pageable);
}
