package com.renthub.chat.service.impl;

import com.renthub.chat.dto.MessageResponse;
import com.renthub.chat.dto.SendMessageRequest;
import com.renthub.chat.entity.ChatMessage;
import com.renthub.chat.mapper.ChatMapper;
import com.renthub.chat.repository.ChatRepository;
import com.renthub.chat.service.ChatService;
import com.renthub.exception.ResourceNotFoundException;
import com.renthub.listing.entity.Listing;
import com.renthub.listing.repository.ListingRepository;
import com.renthub.security.service.AuthenticatedUserService;
import com.renthub.user.entity.User;
import com.renthub.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ChatServiceImpl implements ChatService {

    private final ChatRepository chatRepository;
    private final UserRepository userRepository;
    private final ListingRepository listingRepository;
    private final AuthenticatedUserService authenticatedUserService;

    @Override
    @Transactional
    public MessageResponse sendMessage(SendMessageRequest request) {

        User sender = authenticatedUserService.getCurrentUser();

        User receiver = userRepository.findById(request.getReceiverId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Receiver not found"));

        Listing listing = listingRepository.findById(request.getListingId())
                .orElseThrow(() ->
                        new ResourceNotFoundException("Listing not found"));

        ChatMessage message = new ChatMessage();

        message.setSender(sender);
        message.setReceiver(receiver);
        message.setListing(listing);
        message.setMessage(request.getMessage());

        ChatMessage saved = chatRepository.save(message);

        return ChatMapper.toResponse(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageResponse> getConversation(Long userId) {

        Long currentUserId = authenticatedUserService.getCurrentUserId();

        return chatRepository
                .findConversation(
                        currentUserId,
                        userId)
                .stream()
                .map(ChatMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public List<MessageResponse> getInbox() {

        Long currentUserId = authenticatedUserService.getCurrentUserId();

        return chatRepository
                .findByReceiverIdOrderByCreatedAtDesc(currentUserId)
                .stream()
                .map(ChatMapper::toResponse)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public long getUnreadCount() {

        return chatRepository.countByReceiverIdAndIsReadFalse(
                authenticatedUserService.getCurrentUserId());
    }

    @Override
    @Transactional
    public void markAllAsRead(Long senderId) {

        Long currentUserId = authenticatedUserService.getCurrentUserId();

        List<ChatMessage> messages =
                chatRepository.findConversation(
                        senderId,
                        currentUserId);

        messages.forEach(message -> message.setIsRead(true));

        chatRepository.saveAll(messages);
    }
}