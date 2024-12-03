package com.whisper.controller;

import com.whisper.dto.ExpireRequest;
import com.whisper.dto.TokenDTO;
import com.whisper.dto.UserAuthRequest;
import com.whisper.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@CrossOrigin(origins = "*")
@Slf4j
public class AuthController {

    @Autowired
    private final AuthService authService;
    private final AuthenticationManager authenticationManager;

    public AuthController(AuthService  authService, AuthenticationManager authenticationManager) {
        this.authService = authService;
        this.authenticationManager = authenticationManager;
    }

    @PostMapping("/generateToken")
    public TokenDTO generateToken(@RequestBody UserAuthRequest request) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.username(), request.password()));
        if(authentication.isAuthenticated()) {
            return authService.authenticate(request,authentication);
        }
        log.info("Error: ");
        throw new UsernameNotFoundException("invalid username {} " + request.username());
    }

    @PostMapping("/logout")
    public String logout(@RequestHeader(name="Authorization") String authorization) {
        return authService.logout(authorization);
    }

    @PostMapping("/isExpiredToken")
    public Boolean isExpiredToken(@RequestBody ExpireRequest expireRequest) {
        return authService.isExpiredToken(expireRequest.getAuthorization());
    }
}
