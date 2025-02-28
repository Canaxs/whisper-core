package com.whisper.controller;

import com.whisper.dto.NotificationDTO;
import com.whisper.persistence.entity.Notification;
import com.whisper.service.NotificationService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/notify")
@CrossOrigin(origins = "*")
public class NotifyController {

    private final NotificationService notificationService;

    public NotifyController(NotificationService notificationService) {
        this.notificationService = notificationService;
    }

    @GetMapping("/getUserNotifications")
    public ResponseEntity<NotificationDTO> getUserNotifications() {
        return ResponseEntity.ok(notificationService.getUserNotifications());
    }

    @GetMapping("/readUpdate/{notificationId}")
    public ResponseEntity<Boolean> readUpdate(@PathVariable("notificationId") Long notificationId) {
        return ResponseEntity.ok(notificationService.readUpdate(notificationId));
    }

    @GetMapping("/activeUpdate/{notificationId}")
    public ResponseEntity<Boolean> activeUpdate(@PathVariable("notificationId") Long notificationId) {
        return ResponseEntity.ok(notificationService.activeUpdate(notificationId));
    }

}
