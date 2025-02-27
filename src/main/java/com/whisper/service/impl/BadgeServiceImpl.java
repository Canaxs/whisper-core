package com.whisper.service.impl;

import com.whisper.dto.BadgeCreateRequest;
import com.whisper.dto.UserBadgeAddRequest;
import com.whisper.enums.BadgeType;
import com.whisper.persistence.entity.BadgeEntity;
import com.whisper.persistence.entity.User;
import com.whisper.persistence.repository.BadgeRepository;
import com.whisper.persistence.repository.UserRepository;
import com.whisper.service.BadgeService;
import com.whisper.service.WhisperService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Service
public class BadgeServiceImpl implements BadgeService {

    private final BadgeRepository badgeRepository;

    private final UserRepository userRepository;

    private final WhisperService whisperService;

    public BadgeServiceImpl(BadgeRepository badgeRepository, UserRepository userRepository, WhisperService whisperService) {
        this.badgeRepository = badgeRepository;
        this.userRepository = userRepository;
        this.whisperService = whisperService;
    }

    @Override
    public BadgeEntity createBadge(MultipartFile imageFile, BadgeCreateRequest badgeCreateRequest) {
        String imageURL = null;
        if (!imageFile.isEmpty()) {
            imageURL = whisperService.uploadImgBB(imageFile);
        }

        BadgeEntity badgeEntity = BadgeEntity.builder()
                .badge(badgeCreateRequest.getBadge()).
                badgeURL(imageURL).
                badgeType(BadgeType.ADDED).
                build();
        return badgeRepository.save(badgeEntity);
    }

    @Override
    public User addBadge(UserBadgeAddRequest userBadgeAddRequest) {
        User user = userRepository.findById(userBadgeAddRequest.getUserId()).get();
        try {
            BadgeEntity badgeEntity = badgeRepository.findById(userBadgeAddRequest.getBadgeId()).get();
            user.getBadges().add(badgeEntity);
            return userRepository.save(user);
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public User deleteBadge(UserBadgeAddRequest userBadgeAddRequest) {
        User user = userRepository.findById(userBadgeAddRequest.getUserId()).get();
        try {
            BadgeEntity badgeEntity = badgeRepository.findById(userBadgeAddRequest.getBadgeId()).get();
            user.getBadges().remove(badgeEntity);
            return userRepository.save(user);
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public List<BadgeEntity> getAll() {
        return badgeRepository.findAll();
    }

    @Override
    public Set<BadgeEntity> getUserBadges(String username) {
        User user = userRepository.findByUsername(username).get();
        return user.getBadges();
    }
}
