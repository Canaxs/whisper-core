package com.whisper.service;

import com.whisper.dto.NotificationDTO;
import com.whisper.persistence.entity.Notification;
import com.whisper.persistence.entity.User;

import java.util.List;

public interface NotificationService {

    Notification create(String text,String actionInfo , User user);

    Boolean readUpdate(Long notificationId);

    NotificationDTO getUserNotifications();

    Boolean activeUpdate(Long notificationId);

    Boolean delete(Long notificationId);
}
