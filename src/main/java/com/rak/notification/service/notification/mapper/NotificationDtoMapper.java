package com.rak.notification.service.notification.mapper;

import com.rak.notification.controller.notification.dto.NotificationDto;
import com.rak.notification.dao.model.notification.Notification;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class NotificationDtoMapper {

    public NotificationDto map(Notification entity) {
        return NotificationDto.builder()
                .id(entity.getId())
                .userId(entity.getUserId())
                .message(entity.getMessage())
                .read(entity.isRead())
                .build();
    }

}
