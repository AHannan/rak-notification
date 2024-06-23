package com.rak.notification.controller.notification.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationCreateDto {

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String message;

}
