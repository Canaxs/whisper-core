package com.whisper.dto;

public record WhisperRequest (
        String authorName,
        String title,
        String description,
        String source,
        String category,
        String image
) {}
