package com.rak.notification.dao.repository.notification;

import com.rak.notification.dao.model.notification.Notification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotificationRepository extends JpaRepository<Notification, String>, JpaSpecificationExecutor<Notification> {
    List<Notification> findByUserIdAndRead(String userId, boolean read);
}
