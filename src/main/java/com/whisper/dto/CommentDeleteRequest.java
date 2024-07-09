package com.whisper.dto;

public record CommentDeleteRequest(
        Long commentId,
        Long whisperId
) {}
