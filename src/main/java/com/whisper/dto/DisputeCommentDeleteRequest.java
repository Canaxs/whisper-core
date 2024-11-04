package com.whisper.dto;

public record DisputeCommentDeleteRequest (
        Long commentId,
        Long disputeId
){}
