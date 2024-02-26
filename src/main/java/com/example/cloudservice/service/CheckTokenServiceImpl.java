package com.example.cloudservice.service;

import com.example.cloudservice.exceptions.InvalidTokenException;
import com.example.cloudservice.repository.TokenRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CheckTokenServiceImpl implements CheckTokenService {

    private final TokenRepository tokenRepository;
    public final static String TOKEN_INVALID = "Invalid token!";

    @Override
    public void testToken(String authToken) {
        tokenRepository.getTokenEntityByToken(authToken)
                .orElseThrow(() -> new InvalidTokenException(TOKEN_INVALID));
    }
}
