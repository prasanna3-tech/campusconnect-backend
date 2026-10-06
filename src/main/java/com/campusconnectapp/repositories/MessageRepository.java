package com.campusconnectapp.repositories;

import com.campusconnectapp.models.Message;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MessageRepository extends JpaRepository<Message, Long> {

        @Modifying(flushAutomatically = true, clearAutomatically = true)
        @Query("DELETE FROM Message m WHERE m.conversation.id IN :conversationIds")
        void deleteByConversationIdIn(@Param("conversationIds") List<Long> conversationIds);
}