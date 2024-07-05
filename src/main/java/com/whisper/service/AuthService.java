package com.whisper.service;

import com.whisper.dto.UserAuthRequest;
import org.springframework.security.core.Authentication;

public interface AuthService {

    String authenticate(UserAuthRequest userAuthRequest, Authentication authentication);

    String logout(String authorization);
}
