package com.assignment.domain.chat.service;

import com.assignment.domain.chat.constant.ChatCompletionsRole;
import com.assignment.domain.chat.dto.chatcompletions.ChatCompletionsRequest;
import com.assignment.domain.chat.dto.chatcompletions.OpenAiRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class ChatCompletionsServiceV1 {
    private final RestTemplate restTemplate;
    private final HttpHeaders headers;

    @Value("${openai.url.prompt}")
    private String openAiUrl;

    public String getAnswer(String message) {
        ChatCompletionsRequest userMessage = ChatCompletionsRequest.builder()
                .role(ChatCompletionsRole.USER.getRole())
                .content(message)
                .build();
        List<ChatCompletionsRequest> messages = List.of(ChatCompletionsRequest.prompt(), userMessage);
        OpenAiRequest request = OpenAiRequest.builder()
                .messages(messages)
                .build();
        HttpEntity<OpenAiRequest> entity = new HttpEntity<>(request, headers);

        ResponseEntity<Map> response = restTemplate
                .postForEntity(openAiUrl, entity, Map.class);

        Map<String, Object> body = (Map<String, Object>) response.getBody();
        List<Map<String, Object>> choices = (List<Map<String, Object>>) body.get("choices");
        Map<String, Object> choice = choices.get(0);
        Map<String, Object> result = (Map<String, Object>) choice.get("message");
        return (String) result.get("content");
    }
}
