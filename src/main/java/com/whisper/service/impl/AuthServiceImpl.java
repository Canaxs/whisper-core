package com.whisper.service.impl;

import com.whisper.dto.TokenDTO;
import com.whisper.dto.UserAuthRequest;
import com.whisper.persistence.entity.User;
import com.whisper.persistence.repository.UserRepository;
import com.whisper.service.AuthService;
import com.whisper.service.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class AuthServiceImpl implements AuthService {

    private final JwtService jwtService;

    private final UserRepository userRepository;

    public AuthServiceImpl(JwtService jwtService, UserRepository userRepository) {
        this.jwtService = jwtService;
        this.userRepository = userRepository;
    }

    @Override
    public TokenDTO authenticate(UserAuthRequest userAuthRequest, Authentication authentication) {
        User user = userRepository.findByUsername(userAuthRequest.username()).get();
        return TokenDTO.builder()
                .token(jwtService.generateToken(userAuthRequest.username()))
                .role(user.getAuthorities().toString())
                .userPoint(user.getUserPoint().toString())
                .username(user.getUsername())
                .isSubscribe(user.getSubscription() != null)
                .build();
    }

    @Override
    public String logout(String authorization) {
        String token = authorization.substring(7);
        return jwtService.validateExpiration(token);
    }

    @Override
    public Boolean isExpiredToken(String token) {
        Date expirationDate;
        try {
            expirationDate = jwtService.extractExpiration(token);
        }
        catch (Exception e) {
            return true;
        }
        return expirationDate.before(new Date());
    }
}
