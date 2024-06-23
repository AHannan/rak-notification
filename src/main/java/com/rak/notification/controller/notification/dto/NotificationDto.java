package com.rak.notification.controller.notification.dto;

import jakarta.persistence.Column;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class NotificationDto {

    private String id;

    @Column(nullable = false)
    private String userId;

    @Column(nullable = false)
    private String message;

    private Boolean read;

}
