package com.whisper.service.impl;

import com.whisper.dto.NotificationDTO;
import com.whisper.persistence.entity.Notification;
import com.whisper.persistence.entity.User;
import com.whisper.persistence.repository.NotificationRepository;
import com.whisper.persistence.repository.UserRepository;
import com.whisper.service.NotificationService;
import org.aspectj.weaver.ast.Not;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
public class NotificationServiceImpl implements NotificationService {

    private final NotificationRepository notificationRepository;

    private final UserRepository userRepository;

    public NotificationServiceImpl(NotificationRepository notificationRepository, UserRepository userRepository) {
        this.notificationRepository = notificationRepository;
        this.userRepository = userRepository;
    }

    @Override
    public Notification create(String text,String actionInfo , User user) {
        return notificationRepository.save(Notification.builder()
                    .text(text)
                        .actionInfo(actionInfo)
                            .user(user)
                                .isRead(false)
                                    .isActive(true)
                .build());
    }

    @Override
    public Boolean readUpdate(Long notificationId) {
        try {
            Notification notification = notificationRepository.findById(notificationId).get();
            String securityName = SecurityContextHolder.getContext().getAuthentication().getName();
            if(Objects.equals(notification.getUser().getUsername(), securityName)) {
                notification.setIsRead(true);
                notificationRepository.save(notification);
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public NotificationDTO getUserNotifications() {
        User user = userRepository.findByUsername(SecurityContextHolder.getContext().getAuthentication().getName()).get();
        return NotificationDTO.builder()
                .notificationList(notificationRepository.getAllByUserOrderByIdDesc(user))
                .unReadNotify(notificationRepository.getIsReads(user))
                .build();
    }

    @Override
    public Boolean activeUpdate(Long notificationId) {
        try {
            Notification notification = notificationRepository.findById(notificationId).get();
            String securityName = SecurityContextHolder.getContext().getAuthentication().getName();
            if(Objects.equals(notification.getUser().getUsername(), securityName)) {
                notification.setIsActive(true);
                notificationRepository.save(notification);
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }

    @Override
    public Boolean delete(Long notificationId) {
        try {
            Notification notification = notificationRepository.findById(notificationId).get();
            String securityName = SecurityContextHolder.getContext().getAuthentication().getName();
            if(Objects.equals(notification.getUser().getUsername(), securityName)) {
                notificationRepository.delete(notification);
                return true;
            }
            else {
                return false;
            }
        }
        catch (Exception e) {
            throw new RuntimeException();
        }
    }
}
