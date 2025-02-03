package com.assignment.domain.feedback.api;

import com.assignment.domain.feedback.dto.request.ChatFeedbackRequest;
import com.assignment.domain.feedback.dto.response.ChatFeedbackResponse;
import com.assignment.domain.feedback.service.ChatFeedbackService;
import com.assignment.domain.users.annotation.LoginUser;
import com.assignment.global.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/feedback")
@RequiredArgsConstructor
public class ChatFeedbackController {
    private final ChatFeedbackService chatFeedbackService;

    @PostMapping
    public ResponseEntity<?> createFeedback(@LoginUser String userId, @RequestParam String chatId,
                                            @RequestBody ChatFeedbackRequest request) {
        chatFeedbackService.createChatFeedback(userId, chatId, request);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    public ResponseEntity<?> getFeedback(@LoginUser String userId,
                                         @RequestParam int page,
                                         @RequestParam int size,
                                         @RequestParam(name = "is-desc", defaultValue = "true") boolean isDesc,
                                         @RequestParam(name = "good-or-bad", required = false) Boolean goodOrBad) {
        PageResponse<ChatFeedbackResponse> responses = chatFeedbackService.getChatFeedback(userId, page - 1, size, isDesc, goodOrBad);

        return ResponseEntity.ok(responses);
    }
}
