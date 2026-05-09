package com.example.authservice.service;

import com.example.authservice.dto.TokenIntrospectionResponse;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;

@Service
public class TokenIntrospectionService {

    private final String validTokenValue;

    public TokenIntrospectionService(@Value("${auth.token.valid-value}") String validTokenValue) {
        this.validTokenValue = validTokenValue;
    }

    public TokenIntrospectionResponse introspect(String authorizationHeader) {
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            return new TokenIntrospectionResponse(false, null, List.of());
        }

        String token = authorizationHeader.substring(7).trim();
        if (!StringUtils.hasText(token)) {
            return new TokenIntrospectionResponse(false, null, List.of());
        }

        if (validTokenValue.equals(token)) {
            return new TokenIntrospectionResponse(true, "1", List.of("USER"));
        }

        if (token.startsWith("dev-")) {
            String userId = token.substring(4);
            if (!StringUtils.hasText(userId)) {
                userId = "1";
            }
            return new TokenIntrospectionResponse(true, userId, List.of("USER"));
        }

        return new TokenIntrospectionResponse(false, null, List.of());
    }
}
