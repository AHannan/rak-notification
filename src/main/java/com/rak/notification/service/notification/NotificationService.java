package com.rak.notification.service.notification;

import com.rak.notification.controller.notification.dto.NotificationCreateDto;
import com.rak.notification.controller.notification.dto.NotificationDto;
import com.rak.notification.dao.model.notification.Notification;
import com.rak.notification.dao.repository.notification.NotificationRepository;
import com.rak.notification.service.notification.mapper.NotificationDtoMapper;
import com.rak.notification.service.notification.specification.NotificationSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class NotificationService {

    private final NotificationRepository repository;
    private final NotificationDtoMapper mapper;

    public Page<NotificationDto> getAll(String userId, Pageable pageable) {
        return repository.findAll(NotificationSpecification.buildSpecification(userId), pageable)
                .map(mapper::map);
    }

    public Optional<NotificationDto> getById(String id) {
        return null;
    }

    public List<Notification> getUnreadNotificationsByUserId(String userId) {
        return repository.findByUserIdAndRead(userId, false);
    }

    public Notification createNotification(NotificationCreateDto dto) {
        Notification notification = new Notification();
        notification.setUserId(dto.getUserId());
        notification.setMessage(dto.getMessage());
        return repository.save(notification);
    }

    public Notification markAsRead(String id) {
        Notification notification = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Notification not found"));
        notification.setRead(true);
        return repository.save(notification);
    }

}
