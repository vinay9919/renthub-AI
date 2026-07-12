package com.renthub.chat.repository;

import com.renthub.chat.entity.ChatMessage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ChatRepository
        extends JpaRepository<ChatMessage, Long> {

    @Query("""
SELECT m
FROM ChatMessage m
WHERE
      (m.sender.id = :user1 AND m.receiver.id = :user2)
   OR (m.sender.id = :user2 AND m.receiver.id = :user1)
ORDER BY m.createdAt ASC
""")
List<ChatMessage> findConversation(
        @Param("user1") Long user1,
        @Param("user2") Long user2);

    List<ChatMessage> findByReceiverIdOrderByCreatedAtDesc(
            Long receiverId);

    long countByReceiverIdAndIsReadFalse(
            Long receiverId);
}