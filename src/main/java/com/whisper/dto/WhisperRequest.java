package com.whisper.dto;

import lombok.Getter;

public record WhisperRequest (
        String authorName,
        String title,
        String description,
        String source,
        String category,
        String image
) { }
