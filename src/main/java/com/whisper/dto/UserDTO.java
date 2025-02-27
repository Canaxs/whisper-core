package com.whisper.dto;

import com.whisper.persistence.entity.BadgeEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Set;

@Builder
@Data
public class UserDTO {
    private String username;
    private Double userPoint;
    private String authorities;
    private Set<BadgeEntity> badges;
}
