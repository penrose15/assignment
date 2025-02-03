package com.assignment.domain.chat.service;

import com.assignment.domain.chat.domain.Chat;
import com.assignment.domain.chat.repository.ChatRepository;
import com.assignment.domain.chat.constant.ChatCompletionsRole;
import com.assignment.domain.chat.dto.chatcompletions.ChatCompletionsRequest;
import com.assignment.domain.chat.dto.chatcompletions.ChatCompletionsResponse;
import com.assignment.domain.chat.dto.chatcompletions.ChatMessage;
import com.assignment.domain.chat.dto.chatcompletions.OpenAiRequest;
import com.assignment.global.sse.SseEmitterRepository;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.transaction.event.TransactionalEventListener;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@Slf4j
@Component
@RequiredArgsConstructor
public class ChatCompletionsServiceV2 {
    private final WebClient webClient;
    private final ChatRepository chatRepository;
    private final SseEmitterRepository sseEmitterRepository;

    @TransactionalEventListener
    public void sendResponse(ChatMessage message) {
        ChatCompletionsRequest userMessage = ChatCompletionsRequest.builder()
                .role(ChatCompletionsRole.USER.getRole())
                .content(message.message())
                .build();
        List<ChatCompletionsRequest> messages = List.of(ChatCompletionsRequest.prompt(), userMessage);
        OpenAiRequest request = OpenAiRequest.builder()
                .messages(messages)
                .stream(true)
                .build();

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        Mono<String> mono = webClient
                .post()
                .body(BodyInserters.fromValue(request))
                .retrieve()
                .bodyToFlux(String.class)
                .timeout(Duration.ofSeconds(10))
                .filter(result -> !result.equals("[DONE]"))
                .map(result -> {
                    try {
                        ChatCompletionsResponse response = mapper.readValue(result, ChatCompletionsResponse.class);
                        ChatCompletionsResponse.Choice.Delta delta = response.choices().get(0).delta();
                        if (delta != null && delta.content() != null) {
                            return delta.content();
                        }
                    } catch (JsonProcessingException e) {
                        log.error("JSON 파싱 오류 발생: {}", result, e);
                    }
                    return "";
                })
                .doOnNext(res -> sseEmitterRepository.sendChatResponse(message.id(), res))
                .reduce(new StringBuffer(), StringBuffer::append)
                .map(StringBuffer::toString);

        mono.subscribe(m -> {
            Chat chat = message.chat();
            chat.updateAnswer(m);
            chatRepository.save(chat);
        });
    }
}
