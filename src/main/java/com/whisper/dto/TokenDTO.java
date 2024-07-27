package com.whisper.dto;

import lombok.Builder;

@Builder
public record TokenDTO (
        String token,
        String username,
        String userPoint,
        String role
){}
