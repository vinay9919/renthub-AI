package com.renthub.notification.dto;

import com.renthub.notification.model.NotificationType;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class NotificationRequest {

    private Long userId;

    private String title;

    private String message;

    private NotificationType type;

}