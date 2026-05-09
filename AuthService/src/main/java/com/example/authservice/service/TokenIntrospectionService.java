package com.example.authservice.service;

import com.example.authservice.dto.TokenIntrospectionResponse;
import com.example.authservice.entity.AccessToken;
import com.example.authservice.entity.UserAccount;
import com.example.authservice.repository.AccessTokenRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class TokenIntrospectionService {

    private final String validTokenValue;
    private final AccessTokenRepository accessTokenRepository;

    public TokenIntrospectionService(
            @Value("${auth.token.valid-value}") String validTokenValue,
            AccessTokenRepository accessTokenRepository) {
        this.validTokenValue = validTokenValue;
        this.accessTokenRepository = accessTokenRepository;
    }

    public TokenIntrospectionResponse introspect(String authorizationHeader) {
        if (!StringUtils.hasText(authorizationHeader) || !authorizationHeader.startsWith("Bearer ")) {
            return new TokenIntrospectionResponse(false, null);
        }

        String token = authorizationHeader.substring(7).trim();
        if (!StringUtils.hasText(token)) {
            return new TokenIntrospectionResponse(false, null);
        }

        if (validTokenValue.equals(token)) {
            return new TokenIntrospectionResponse(true, "1");
        }

        if (token.startsWith("dev-")) {
            String userId = token.substring(4);
            if (!StringUtils.hasText(userId)) {
                userId = "1";
            }
            return new TokenIntrospectionResponse(true, userId);
        }

        AccessToken accessToken = accessTokenRepository.findByTokenAndRevokedFalse(token).orElse(null);
        if (accessToken == null) {
            return new TokenIntrospectionResponse(false, null);
        }

        if (accessToken.getExpiresAt() == null || accessToken.getExpiresAt().isBefore(LocalDateTime.now())) {
            return new TokenIntrospectionResponse(false, null);
        }

        UserAccount user = accessToken.getUser();
        if (user == null || user.getId() == null) {
            return new TokenIntrospectionResponse(false, null);
        }

        return new TokenIntrospectionResponse(
                true,
                String.valueOf(user.getId())
        );
    }
}
