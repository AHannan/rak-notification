package com.rak.notification.service.notification;

import com.rak.notification.controller.notification.dto.NotificationCreateDto;
import com.rak.notification.controller.notification.dto.NotificationDto;
import com.rak.notification.dao.model.notification.Notification;
import com.rak.notification.dao.repository.notification.NotificationRepository;
import com.rak.notification.service.notification.mapper.NotificationDtoMapper;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

public class NotificationServiceTest {

    @Mock
    private NotificationRepository repository;

    @Mock
    private NotificationDtoMapper mapper;

    @InjectMocks
    private NotificationService service;

    private Notification notification;
    private NotificationCreateDto createDto;
    private NotificationDto notificationDto;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        notification = new Notification();
        notification.setId("1");
        notification.setUserId("user1");
        notification.setMessage("Test message");
        notification.setRead(false);

        createDto = NotificationCreateDto.builder()
                .userId("user1")
                .message("Test message")
                .build();

        notificationDto = NotificationDto.builder()
                .id("1")
                .userId("user1")
                .message("Test message")
                .read(false)
                .build();
    }

    @Test
    void testCreateNotification() {
        when(repository.save(any(Notification.class))).thenReturn(notification);
        when(mapper.map(any(Notification.class))).thenReturn(notificationDto);

        NotificationDto result = service.createNotification(createDto);

        assertNotNull(result);
        assertEquals("user1", result.getUserId());
        assertEquals("Test message", result.getMessage());
        assertFalse(result.getRead());

        verify(repository, times(1)).save(any(Notification.class));
        verify(mapper, times(1)).map(any(Notification.class));
    }

    @Test
    void testGetAllNotifications() {
        Pageable pageable = PageRequest.of(0, 10);
        Page<Notification> page = new PageImpl<>(Collections.singletonList(notification), pageable, 1);

        when(repository.findAll(any(Specification.class), eq(pageable))).thenReturn(page);
        when(mapper.map(any(Notification.class))).thenReturn(notificationDto);

        Page<NotificationDto> result = service.getAll("user1", pageable);

        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Test message", result.getContent().get(0).getMessage());

        verify(repository, times(1)).findAll(any(Specification.class), eq(pageable));
        verify(mapper, times(1)).map(any(Notification.class));
    }

    @Test
    void testMarkNotificationAsRead() {
        when(repository.findById("1")).thenReturn(Optional.of(notification));
        notification.setRead(true);
        when(repository.save(any(Notification.class))).thenReturn(notification);

        Notification result = service.markAsRead("1");

        assertNotNull(result);
        assertTrue(result.isRead());

        verify(repository, times(1)).findById("1");
        verify(repository, times(1)).save(any(Notification.class));
    }

    @Test
    void testMarkNotificationAsRead_NotFound() {
        when(repository.findById("1")).thenReturn(Optional.empty());

        Exception exception = assertThrows(IllegalArgumentException.class, () -> {
            service.markAsRead("1");
        });

        String expectedMessage = "Notification not found";
        String actualMessage = exception.getMessage();

        assertTrue(actualMessage.contains(expectedMessage));

        verify(repository, times(1)).findById("1");
        verify(repository, never()).save(any(Notification.class));
    }

    @Test
    void testGetUnreadNotificationsByUserId() {
        Notification notification1 = toNotification("user1", "Message 1", false);
        List<Notification> notifications = List.of(notification1);

        when(repository.findByUserIdAndRead("user1", false)).thenReturn(notifications);

        List<Notification> result = service.getUnreadNotificationsByUserId("user1");

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Message 1", result.get(0).getMessage());

        verify(repository, times(1)).findByUserIdAndRead("user1", false);
    }

    private static Notification toNotification(String userId, String message, boolean read) {
        Notification notification = new Notification();
        notification.setUserId(userId);
        notification.setMessage(message);
        notification.setRead(read);
        return notification;
    }
}
