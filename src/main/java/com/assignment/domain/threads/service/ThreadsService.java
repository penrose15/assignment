package com.assignment.domain.threads.service;

import com.assignment.domain.chat.repository.ChatRepository;
import com.assignment.domain.feedback.repository.ChatFeedBackRepository;
import com.assignment.domain.threads.domain.Threads;
import com.assignment.domain.threads.repository.ThreadsRepository;
import com.assignment.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static com.assignment.global.exception.errortype.ThreadsErrorCode.THREAD_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class ThreadsService {
    private final ThreadsRepository threadsRepository;
    private final ChatFeedBackRepository chatFeedBackRepository;
    private final ChatRepository chatRepository;

    public void deleteByThreadId(Long threadId) {
        Threads thread = threadsRepository.findById(threadId)
                .orElseThrow(() -> new BusinessException(THREAD_NOT_FOUND));

        chatRepository.deleteAllByThreadId(threadId);
        threadsRepository.deleteById(threadId);
    }
}
