package com.assignment.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.reactive.function.client.WebClient;

@Configuration
public class WebClientConfig {
    @Value("${openai.secret-key}")
    private String secretKey;
    @Value("${openai.url.prompt}")
    private String openAiUrl;

    @Bean
    public WebClient webClient() {
        return WebClient.builder()
                .defaultHeader("Content-Type", "application/json")
                .defaultHeader("Authorization", "Bearer " + secretKey)
                .baseUrl(openAiUrl)
                .build();
    }
}
