package com.renthub.notification.controller;

import com.renthub.notification.dto.NotificationRequest;
import com.renthub.notification.dto.NotificationResponse;
import com.renthub.notification.service.NotificationService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;

    @PostMapping
    public NotificationResponse create(
            @RequestBody NotificationRequest request) {

        return notificationService.create(request);
    }

    @GetMapping("/user/{userId}")
    public List<NotificationResponse> getUserNotifications(
            @PathVariable Long userId) {

        return notificationService.getUserNotifications(userId);
    }

    @GetMapping("/user/{userId}/unread")
    public List<NotificationResponse> getUnreadNotifications(
            @PathVariable Long userId) {

        return notificationService.getUnreadNotifications(userId);
    }

    @PutMapping("/{notificationId}/read")
    public void markAsRead(
            @PathVariable Long notificationId) {

        notificationService.markAsRead(notificationId);
    }

    @PutMapping("/user/{userId}/read-all")
    public void markAllAsRead(
            @PathVariable Long userId) {

        notificationService.markAllAsRead(userId);
    }

    @DeleteMapping("/{notificationId}")
    public void delete(
            @PathVariable Long notificationId) {

        notificationService.delete(notificationId);
    }
    @GetMapping("/user/{userId}/count")
public long getUnreadCount(
        @PathVariable Long userId) {

    return notificationService.getUnreadCount(userId);

}
}