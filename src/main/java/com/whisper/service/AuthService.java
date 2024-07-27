package com.whisper.service;

import com.whisper.dto.TokenDTO;
import com.whisper.dto.UserAuthRequest;
import org.springframework.security.core.Authentication;

public interface AuthService {

    TokenDTO authenticate(UserAuthRequest userAuthRequest, Authentication authentication);

    String logout(String authorization);

    Boolean isExpiredToken(String token);
}
