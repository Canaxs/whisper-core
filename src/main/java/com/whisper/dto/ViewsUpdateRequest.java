package com.whisper.dto;

public record ViewsUpdateRequest(
        Long whisperId,
        Long numberOfViews
) {}
