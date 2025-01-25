package com.assignment.domain.feedback.service;

import com.assignment.domain.chat.domain.Chat;
import com.assignment.domain.chat.repository.ChatRepository;
import com.assignment.domain.feedback.domain.ChatFeedback;
import com.assignment.domain.feedback.dto.request.ChatFeedbackRequest;
import com.assignment.domain.feedback.dto.response.ChatFeedbackResponse;
import com.assignment.domain.feedback.repository.ChatFeedBackRepository;
import com.assignment.domain.users.domain.Users;
import com.assignment.domain.users.repository.UsersRepository;
import com.assignment.global.dto.PageResponse;
import com.assignment.global.exception.BusinessException;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.assignment.global.exception.errortype.ChatErrorCode.CHAT_NOT_FOUND;
import static com.assignment.global.exception.errortype.ChatFeedbackErrorCode.CHAT_FEEDBACK_ALREADY_EXIST;
import static com.assignment.global.exception.errortype.UserErrorCode.USER_NOT_FOUND;

@Service
@Transactional
@RequiredArgsConstructor
public class ChatFeedbackService {
    private final ChatFeedBackRepository chatFeedBackRepository;
    private final UsersRepository usersRepository;
    private final ChatRepository chatRepository;

    public void createChatFeedback(String userId, String chatId, ChatFeedbackRequest request) {
        Users users = usersRepository.findById(userId)
                .orElseThrow(() -> new BusinessException(USER_NOT_FOUND));
        Chat chat = chatRepository.findById(chatId)
                .orElseThrow(() -> new BusinessException(CHAT_NOT_FOUND));
        Optional<ChatFeedback> chatFeedback = chatFeedBackRepository.findByChatIdAndUserId(chatId, userId);
        if (chatFeedback.isPresent()) {
            throw new BusinessException(CHAT_FEEDBACK_ALREADY_EXIST);
        }

        chatFeedBackRepository.save(ChatFeedback.builder()
                .users(users)
                .chat(chat)
                .goodOrBad(request.goodOrBad())
                .build());
    }

    public PageResponse<ChatFeedbackResponse> getChatFeedback(String userId,
                                                              int page, int size,
                                                              boolean isDesc, Boolean goodOrBad) {
        Page<ChatFeedback> chatFeedbackPage;
        if (isDesc) {
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.DESC, "createdAt");
            chatFeedbackPage = chatFeedBackRepository.findAllByUserIdByGoodOrBadOrderByCreatedAtDesc(userId, goodOrBad, pageable);
        } else {
            Pageable pageable = PageRequest.of(page, size, Sort.Direction.ASC, "createdAt");
            chatFeedbackPage = chatFeedBackRepository.findAllByUserIdByGoodOrBadOrderByCreatedAt(userId, goodOrBad, pageable);
        }
        Page<ChatFeedbackResponse> responses = chatFeedbackPage.map(ChatFeedbackResponse::from);

        return PageResponse.from(responses);
    }
}
