package com.renthub.notification.service;

import com.renthub.notification.dto.NotificationRequest;
import com.renthub.notification.dto.NotificationResponse;

import java.util.List;

public interface NotificationService {

    NotificationResponse create(NotificationRequest request);

    List<NotificationResponse> getUserNotifications(Long userId);

    List<NotificationResponse> getUnreadNotifications(Long userId);

    void markAsRead(Long notificationId);

    void markAllAsRead(Long userId);

    void delete(Long notificationId);

    long getUnreadCount(Long userId);

}