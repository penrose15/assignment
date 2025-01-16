package com.assignment.domain.chat.api;

import com.assignment.domain.chat.dto.request.ChatQuestionRequest;
import com.assignment.domain.chat.dto.response.ChatResponse;
import com.assignment.domain.chat.service.ChatService;
import com.assignment.domain.users.annotation.LoginUser;
import com.assignment.global.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<?> createChat(
            @LoginUser String userId,
            @RequestBody ChatQuestionRequest request) {
        ChatResponse response = chatService.createChat(userId, request);

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChat(
            @LoginUser String userId,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam boolean desc) {
        PageResponse<ChatResponse> response = chatService.findChatPage(userId, page-1, size, desc);

        return ResponseEntity.ok(response);
    }
}
