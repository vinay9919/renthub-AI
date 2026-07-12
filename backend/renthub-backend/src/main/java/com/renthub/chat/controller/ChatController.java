package com.renthub.chat.controller;

import com.renthub.chat.dto.MessageResponse;
import com.renthub.chat.dto.SendMessageRequest;
import com.renthub.chat.service.ChatService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/chat")
@RequiredArgsConstructor
public class ChatController {

    private final ChatService chatService;

    @PreAuthorize("isAuthenticated()")
    @PostMapping
    public MessageResponse sendMessage(
            @Valid
            @RequestBody SendMessageRequest request) {

        return chatService.sendMessage(request);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/conversation/{userId}")
    public List<MessageResponse> getConversation(
            @PathVariable Long userId) {

        return chatService.getConversation(userId);
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/inbox")
    public List<MessageResponse> getInbox() {

        return chatService.getInbox();
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/unread-count")
    public long getUnreadCount() {

        return chatService.getUnreadCount();
    }

    @PreAuthorize("isAuthenticated()")
    @PutMapping("/read/{senderId}")
    public void markAllAsRead(
            @PathVariable Long senderId) {

        chatService.markAllAsRead(senderId);
    }
}