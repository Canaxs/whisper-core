package com.whisper.service.impl;

import com.whisper.dto.UserAuthRequest;
import com.whisper.service.AuthService;
import com.whisper.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;

    public AuthServiceImpl(JwtService jwtService) {
        this.jwtService = jwtService;
    }

    @Override
    public String authenticate(UserAuthRequest userAuthRequest, Authentication authentication) {
        return jwtService.generateToken(userAuthRequest.username());
    }
}
