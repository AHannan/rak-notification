package com.rak.notification.controller.notification;

import com.rak.notification.controller.notification.dto.NotificationCreateDto;
import com.rak.notification.controller.notification.dto.NotificationDto;
import com.rak.notification.dao.model.notification.Notification;
import com.rak.notification.service.notification.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService service;

    @GetMapping
    public ResponseEntity<Page<NotificationDto>> getAll(
            @RequestParam(required = false) String userId,
            Pageable pageable) {
        Page<NotificationDto> result = service.getAll(userId, pageable);
        return new ResponseEntity<>(result, HttpStatus.OK);
    }

    @GetMapping("/unread/{userId}")
    public ResponseEntity<List<Notification>> getUnreadNotifications(@PathVariable String userId) {
        List<Notification> notifications = service.getUnreadNotificationsByUserId(userId);
        return ResponseEntity.ok(notifications);
    }

    @PostMapping
    public ResponseEntity<NotificationDto> createNotification(@RequestBody NotificationCreateDto notification) {
        NotificationDto createdNotification = service.createNotification(notification);
        return ResponseEntity.ok(createdNotification);
    }

    @PutMapping("/{id}/read")
    public ResponseEntity<Notification> markAsRead(@PathVariable String id) {
        Notification notification = service.markAsRead(id);
        return ResponseEntity.ok(notification);
    }
}
