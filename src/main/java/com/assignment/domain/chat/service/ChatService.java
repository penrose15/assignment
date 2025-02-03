package com.assignment.domain.chat.service;

import com.assignment.domain.chat.domain.Chat;
import com.assignment.domain.chat.dto.request.ChatQuestionRequest;
import com.assignment.domain.chat.dto.response.ChatResponse;
import com.assignment.domain.chat.repository.ChatRepository;
import com.assignment.domain.threads.domain.Threads;
import com.assignment.domain.threads.repository.ThreadsRepository;
import com.assignment.domain.users.domain.Users;
import com.assignment.domain.users.repository.UsersRepository;
import com.assignment.global.ai.ChatCompletions;
import com.assignment.global.ai.ChatCompletions2;
import com.assignment.global.ai.ChatCompletions3;
import com.assignment.global.ai.dto.ChatMessage;
import com.assignment.global.dto.PageResponse;
import com.assignment.global.exception.BusinessException;
import com.assignment.global.sse.SseEmitterRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.time.LocalDateTime;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;

import static com.assignment.global.exception.errortype.UserErrorCode.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatService {
    private final ChatRepository chatRepository;
    private final ThreadsRepository threadsRepository;
    private final UsersRepository usersRepository;
    private final ChatCompletions chatCompletions;
    private final ChatCompletions2 chatCompletions2;
    private final ChatCompletions3 chatCompletions3;
    private final SseEmitterRepository sseEmitterRepository;
    private final ApplicationEventPublisher eventPublisher;

    public ChatResponse createChat(String userId, ChatQuestionRequest request) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
        Optional<Chat> optionalChat = chatRepository.findByUsersIdOrderByCreatedAtDesc(user.getId());

        Threads thread = getThreads(optionalChat, user);
        String answer = chatCompletions.getAnswer(request.question());
        Chat chat = chatRepository.save(Chat.builder()
                .users(user)
                .threads(thread)
                .question(request.question())
                .answer(answer)
                .build());
        return ChatResponse.of(chat);
    }

    public ChatResponse createChat2(String userId, ChatQuestionRequest request) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
        Optional<Chat> optionalChat = chatRepository.findByUsersIdOrderByCreatedAtDesc(user.getId());

        Threads thread = getThreads(optionalChat, user);
        String answer = chatCompletions2.getAnswer(request.question());

        Chat chat = chatRepository.save(Chat.builder()
                .users(user)
                .threads(thread)
                .question(request.question())
                .answer("")
                .build());
        return ChatResponse.of(chat);
    }


    public SseEmitter createChat3(String userId, ChatQuestionRequest request) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
        Optional<Chat> optionalChat = chatRepository.findByUsersIdOrderByCreatedAtDesc(user.getId());

        Threads thread = getThreads(optionalChat, user);
        Chat chat = Chat.builder()
                .users(user)
                .threads(thread)
                .question(request.question())
                .build();

        ChatMessage chatMessage = new ChatMessage(userId, request.question(), chat);
        SseEmitter emitter = sseEmitterRepository.subscribe(userId);
        eventPublisher.publishEvent(chatMessage);

        return emitter;
    }

    private Threads getThreads(Optional<Chat> optionalChat, Users user) {
        LocalDateTime createdChat = null;
        Threads thread = null;
        if (optionalChat.isPresent()) {
            Chat chat = optionalChat.get();
            createdChat = chat.getCreatedAt();
            thread = chat.getThreads();
        }
        if (createdChat == null || LocalDateTime.now().isAfter(createdChat.plusMinutes(30))) {
            thread = threadsRepository.save(
                    Threads.builder()
                            .users(user)
                            .build()
            );
        }
        return thread;
    }

    public PageResponse<ChatResponse> findChatPage(String userId, int page, int size, boolean isDesc) {
        Users user = usersRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
        Page<Chat> chatPage;
        if (isDesc) {
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
            chatPage = chatRepository.findAllByUsersIdOrderByCreatedAtDesc(userId, pageable);
        } else {
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");
            chatPage = chatRepository.findAllByUsersIdOrderByCreatedAt(userId, pageable);
        }

        Page<ChatResponse> chatResponses = chatPage.map(ChatResponse::of);
        return PageResponse.from(chatResponses);
    }
}
