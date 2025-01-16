package com.assignment.domain.threads.service;

import com.assignment.domain.chat.domain.Chat;
import com.assignment.domain.chat.repository.ChatRepository;
import com.assignment.domain.feedback.repository.ChatFeedBackRepository;
import com.assignment.domain.threads.domain.Threads;
import com.assignment.domain.threads.repository.ThreadsRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
@RequiredArgsConstructor
public class ThreadsService {
    private final ThreadsRepository threadsRepository;
    private final ChatFeedBackRepository chatFeedBackRepository;
    private final ChatRepository chatRepository;

    public void deleteByThreadId(Long threadId) {
        Threads thread = threadsRepository.findById(threadId)
                        .orElseThrow(() -> new RuntimeException("Thread not found"));
        List<String> chatIds = chatRepository.findAllByThreadId(threadId)
                        .stream()
                                .map(Chat::getId)
                                        .toList();

        chatFeedBackRepository.deleteAllByChatIds(chatIds);
        chatRepository.deleteAllByThreadId(threadId);
        threadsRepository.deleteById(threadId);
    }
}
