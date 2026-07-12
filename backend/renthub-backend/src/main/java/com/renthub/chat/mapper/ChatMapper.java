package com.renthub.chat.mapper;

import com.renthub.chat.dto.MessageResponse;
import com.renthub.chat.entity.ChatMessage;

public class ChatMapper {

    private ChatMapper() {
    }

    public static MessageResponse toResponse(
            ChatMessage message) {

        return MessageResponse.builder()
                .id(message.getId())
                .senderId(message.getSender().getId())
                .senderName(message.getSender().getFullName())
                .receiverId(message.getReceiver().getId())
                .receiverName(message.getReceiver().getFullName())
                .listingId(message.getListing().getId())
                .message(message.getMessage())
                .isRead(message.getIsRead())
                .createdAt(message.getCreatedAt())
                .build();
    }
}