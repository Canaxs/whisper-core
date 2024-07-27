package com.whisper.dto;

import lombok.Getter;

public record WhisperRequest (
        String title,
        String description,
        String source,
        String category,
        String image
) { }
