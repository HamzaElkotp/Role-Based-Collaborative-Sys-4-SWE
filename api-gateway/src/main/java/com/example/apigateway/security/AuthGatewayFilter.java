package com.example.apigateway.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
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

    private final Algorithm algo = Algorithm.HMAC256("your_secret");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String path = exchange.getRequest().getURI().getPath();

        // 1. Skip if Public
        if (isPublicPath(path)) {
            return chain.filter(exchange);
        }

        // 2. Extract Header
        String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
        if (!StringUtils.hasText(authHeader) || !authHeader.startsWith("Bearer ")) {
            return reject(exchange.getResponse(), HttpStatus.UNAUTHORIZED, "Missing Token!!");
        }

        try {
            // 3. Decode & Verify (The "Node.js" way)
            String token = authHeader.substring(7);
            DecodedJWT decoded = JWT.require(algo).build().verify(token);

            // 4. Inject Headers and Continue (Combined logic)
            return chain.filter(exchange.mutate()
                    .request(r -> r.headers(h -> {
                        h.set("X-User-Id", String.valueOf(decoded.getClaim("id").asLong()));
                        h.set("X-User-Email", decoded.getSubject());
                        h.set("X-User-Roles", ""); // Add roles claim here if you add it to the token later
                    }))
                    .build());

        } catch (Exception e) {
            return reject(exchange.getResponse(), HttpStatus.UNAUTHORIZED, "Invalid or Expired Token!!");
        }
    }

    private boolean isPublicPath(String path) {
        return path.startsWith("/api/auth/")
                || path.equals("/actuator/health")
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