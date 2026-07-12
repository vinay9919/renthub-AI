package com.renthub.chat.service;

import com.renthub.chat.dto.MessageResponse;
import com.renthub.chat.dto.SendMessageRequest;

import java.util.List;

public interface ChatService {

    MessageResponse sendMessage(SendMessageRequest request);

    List<MessageResponse> getConversation(Long userId);

    List<MessageResponse> getInbox();

    long getUnreadCount();

    void markAllAsRead(Long senderId);
}