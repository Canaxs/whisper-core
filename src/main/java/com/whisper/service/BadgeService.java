package com.whisper.service;

import com.whisper.dto.BadgeCreateRequest;
import com.whisper.dto.UserBadgeAddRequest;
import com.whisper.persistence.entity.BadgeEntity;
import com.whisper.persistence.entity.User;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

public interface BadgeService {

    BadgeEntity createBadge(MultipartFile imageFile, BadgeCreateRequest badgeCreateRequest);

    User addBadge(UserBadgeAddRequest userBadgeAddRequest);

    User deleteBadge(UserBadgeAddRequest userBadgeAddRequest);

    List<BadgeEntity> getAll();

    Set<BadgeEntity> getUserBadges(String username);


}
