package com.assignment.global.ai;

import com.assignment.global.ai.constant.ChatCompletionsRole;
import com.assignment.global.ai.dto.ChatCompletionsRequest;
import com.assignment.global.ai.dto.ChatCompletionsResponse;
import com.assignment.global.ai.dto.OpenAiRequest;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;

import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
public class ChatCompletions2 {
    private final WebClient webClient;

    public String getAnswer(String message) {
        ChatCompletionsRequest userMessage = ChatCompletionsRequest.builder()
                .role(ChatCompletionsRole.USER.getRole())
                .content(message)
                .build();
        List<ChatCompletionsRequest> messages = List.of(ChatCompletionsRequest.prompt(), userMessage);
        OpenAiRequest request = OpenAiRequest.builder()
                .messages(messages)
                .stream(true)
                .build();

        ObjectMapper mapper = new ObjectMapper().configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

        return webClient
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
                .toStream().collect(Collectors.joining(""));
    }
}
