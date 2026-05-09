package com.example.apigateway.security;

import com.example.apigateway.client.AuthServiceClient;
import com.example.apigateway.dto.TokenIntrospectionResponse;
import org.springframework.core.Ordered;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

@Component
public class AuthGatewayFilter implements GlobalFilter, Ordered {

    private final AuthServiceClient authServiceClient;

    public AuthGatewayFilter(AuthServiceClient authServiceClient) {
        this.authServiceClient = authServiceClient;
    }

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            return reject(exchange.getResponse(), HttpStatus.UNAUTHORIZED, "Missing or invalid Authorization header");
        }

        return authServiceClient.introspect(authHeader)
                .flatMap(result -> continueOrReject(exchange, chain, result))
                .onErrorResume(ex -> reject(exchange.getResponse(), HttpStatus.FORBIDDEN, "Token introspection failed"));
    }

    private Mono<Void> continueOrReject(ServerWebExchange exchange, GatewayFilterChain chain, TokenIntrospectionResponse result) {
        if (result == null || !result.active()) {
            return reject(exchange.getResponse(), HttpStatus.UNAUTHORIZED, "Token is not active");
        }

        String roles = result.roles() == null ? "" : String.join(",", result.roles());

        return chain.filter(exchange.mutate()
                .request(request -> request.headers(headers -> {
                    headers.set("X-User-Id", result.userId() == null ? "" : result.userId());
                    headers.set("X-User-Roles", roles);
                }))
                .build());
    }

    private boolean isPublicPath(String path) {
        return path.startsWith("/api/auth/")
                || path.equals("/actuator/health")
                || path.equals("/actuator/info")
                || path.startsWith("/eureka");
    }

    private Mono<Void> reject(ServerHttpResponse response, HttpStatus status, String message) {
        response.setStatusCode(status);
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON);
        byte[] body = ("{\"error\":\"" + message + "\"}").getBytes();
        return response.writeWith(Mono.just(response.bufferFactory().wrap(body)));
    }

    @Override
    public int getOrder() {
        return -100;
    }
}
