package com.example.apigateway.client;

import com.example.apigateway.dto.TokenIntrospectionResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

@Component
public class AuthServiceClient {

    private final WebClient.Builder webClientBuilder;

    public AuthServiceClient(WebClient.Builder webClientBuilder) {
        this.webClientBuilder = webClientBuilder;
    }

    public Mono<TokenIntrospectionResponse> introspect(String authorizationHeader) {
        return webClientBuilder.build()
                .post()
                .uri("http://auth-service/internal/auth/introspect")
                .header(HttpHeaders.AUTHORIZATION, authorizationHeader)
                .contentType(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(TokenIntrospectionResponse.class);
    }
}
