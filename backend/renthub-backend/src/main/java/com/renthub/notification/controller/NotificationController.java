package com.renthub.notification.controller;

import com.renthub.notification.dto.NotificationRequest;
import com.renthub.notification.dto.NotificationResponse;
import com.renthub.notification.service.NotificationService;
import com.renthub.security.service.AuthenticatedUserService;

import lombok.RequiredArgsConstructor;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/notifications")
@RequiredArgsConstructor
public class NotificationController {

    private final NotificationService notificationService;
    private final AuthenticatedUserService authenticatedUserService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public NotificationResponse create(
            @RequestBody NotificationRequest request) {

        return notificationService.create(request);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userId}")
    public List<NotificationResponse> getUserNotifications(
            @PathVariable Long userId) {

        return notificationService.getUserNotifications(userId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userId}/unread")
    public List<NotificationResponse> getUnreadNotifications(
            @PathVariable Long userId) {

        return notificationService.getUnreadNotifications(userId);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/{notificationId}/read")
    public void markAsRead(
            @PathVariable Long notificationId) {

        notificationService.markAsRead(notificationId);
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/user/{userId}/read-all")
    public void markAllAsRead(
            @PathVariable Long userId) {

        notificationService.markAllAsRead(userId);
    }

    @PreAuthorize("isAuthenticated()")
    @DeleteMapping("/{notificationId}")
    public void delete(
            @PathVariable Long notificationId) {

        notificationService.delete(notificationId);
    }
    
    @PreAuthorize("isAuthenticated()")
    @GetMapping("/user/{userId}/count")
public long getUnreadCount(
        @PathVariable Long userId) {

    return notificationService.getUnreadCount(userId);

}
}