package com.example.authservice.service;

import com.example.authservice.dto.AuthResponse;
import com.example.authservice.dto.LoginRequest;
import com.example.authservice.dto.RegisterRequest;
import com.example.authservice.entity.AccessToken;
import com.example.authservice.entity.UserAccount;
import com.example.authservice.repository.AccessTokenRepository;
import com.example.authservice.repository.UserAccountRepository;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
public class AuthenticationService {

    private final UserAccountRepository userAccountRepository;
    private final AccessTokenRepository accessTokenRepository;
    private final PasswordEncoder passwordEncoder;
    private final long tokenTtlMinutes;

    public AuthenticationService(
        UserAccountRepository userAccountRepository,
        AccessTokenRepository accessTokenRepository,
        @Value("${auth.token.ttl-minutes:120}") long tokenTtlMinutes) 
    {
        this.userAccountRepository = userAccountRepository;
        this.accessTokenRepository = accessTokenRepository;
        this.tokenTtlMinutes = tokenTtlMinutes;
        this.passwordEncoder = new BCryptPasswordEncoder();
    }

    @Transactional
    public AuthResponse register(RegisterRequest request) {
        String normalizedUsername = request.username().trim().toLowerCase();
        String normalizedEmail = request.email().trim().toLowerCase();

        if (userAccountRepository.existsByUsername(normalizedUsername)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Username already exists");
        }
        if (userAccountRepository.existsByEmail(normalizedEmail)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        UserAccount user = new UserAccount();
        user.setUsername(normalizedUsername);
        user.setEmail(normalizedEmail);
        user.setPasswordHash(passwordEncoder.encode(request.password()));

        UserAccount savedUser = userAccountRepository.save(user);
        return issueAccessToken(savedUser);
    }

    @Transactional
    public AuthResponse login(LoginRequest request) {
        String normalizedEmail = request.email().trim().toLowerCase();
        UserAccount user = userAccountRepository.findByEmail(normalizedEmail)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid email or password"));

        if (!passwordEncoder.matches(request.password(), user.getPasswordHash())) {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid username or password");
        }

        return issueAccessToken(user);
    }

    private AuthResponse issueAccessToken(UserAccount user) {
        AccessToken token = new AccessToken();
        token.setToken(UUID.randomUUID().toString() + "." + UUID.randomUUID());
        token.setRevoked(false);
        token.setUser(user);
        token.setExpiresAt(LocalDateTime.now().plusMinutes(tokenTtlMinutes));

        AccessToken savedToken = accessTokenRepository.save(token);

        return new AuthResponse(
                savedToken.getToken(),
                "Bearer",
                tokenTtlMinutes * 60,
                String.valueOf(user.getId()),
                user.getEmail()
        );
    }
}
