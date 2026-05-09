package com.example.authservice.controller;

import com.example.authservice.dto.TokenIntrospectionResponse;
import com.example.authservice.service.TokenIntrospectionService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/internal/auth")
public class AuthIntrospectionController {

    private final TokenIntrospectionService tokenIntrospectionService;

    public AuthIntrospectionController(TokenIntrospectionService tokenIntrospectionService) {
        this.tokenIntrospectionService = tokenIntrospectionService;
    }

    @PostMapping("/introspect")
    public ResponseEntity<TokenIntrospectionResponse> introspect(
            @RequestHeader(value = HttpHeaders.AUTHORIZATION, required = false) String authorizationHeader) {
        return ResponseEntity.ok(tokenIntrospectionService.introspect(authorizationHeader));
    }
}
