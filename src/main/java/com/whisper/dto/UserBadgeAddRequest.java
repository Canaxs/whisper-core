package com.whisper.dto;

import lombok.Data;

@Data
public class UserBadgeAddRequest {
    private Long badgeId;
    private Long userId;
}
