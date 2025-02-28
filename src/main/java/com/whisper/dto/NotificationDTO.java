package com.whisper.dto;

import com.whisper.persistence.entity.Notification;
import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class NotificationDTO {
    private List<Notification> notificationList;
    private Integer unReadNotify;

}
