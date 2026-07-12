package com.renthub.chat.dto;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@Builder
public class MessageResponse {

    private Long id;

    private Long senderId;

    private String senderName;

    private Long receiverId;

    private String receiverName;

    private Long listingId;

    private String message;

    private Boolean isRead;

    private LocalDateTime createdAt;
}