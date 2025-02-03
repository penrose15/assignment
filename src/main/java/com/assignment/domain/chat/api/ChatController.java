package com.assignment.domain.chat.api;

import com.assignment.domain.chat.dto.request.ChatQuestionRequest;
import com.assignment.domain.chat.dto.response.ChatResponse;
import com.assignment.domain.chat.service.ChatService;
import com.assignment.domain.users.annotation.LoginUser;
import com.assignment.global.dto.PageResponse;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import reactor.core.publisher.Flux;

import java.util.concurrent.ExecutionException;

@RestController
@RequestMapping("/chat")
@RequiredArgsConstructor
public class ChatController {
    private static final Logger log = LoggerFactory.getLogger(ChatController.class);
    private final ChatService chatService;

    @PostMapping
    public ResponseEntity<?> createChat(
            @LoginUser String userId,
            @RequestBody ChatQuestionRequest request) {
        ChatResponse response = chatService.createChat(userId, request);

        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/v1/sse")
    public ResponseEntity<?> createChat2(
            @LoginUser String userId,
            @RequestBody ChatQuestionRequest request) {

        ChatResponse res = chatService.createChat2(userId, request);

        return ResponseEntity.ok(res);
    }

    @PostMapping(value = "/v2/sse", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public ResponseEntity<SseEmitter> createChat3(
            @LoginUser String userId,
            @RequestBody ChatQuestionRequest request) {

        SseEmitter res = chatService.createChat3(userId, request);
        return ResponseEntity.ok(res);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getChat(
            @LoginUser String userId,
            @RequestParam int page,
            @RequestParam int size,
            @RequestParam boolean desc) {
        PageResponse<ChatResponse> response = chatService.findChatPage(userId, page - 1, size, desc);

        return ResponseEntity.ok(response);
    }
}
