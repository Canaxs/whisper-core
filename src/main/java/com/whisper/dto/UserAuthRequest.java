package com.whisper.dto;

import lombok.Builder;

@Builder
public record UserAuthRequest (
        String username,
        String password
) {}
