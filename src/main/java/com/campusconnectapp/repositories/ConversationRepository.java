package com.campusconnectapp.repositories;

import com.campusconnectapp.models.Conversation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ConversationRepository extends JpaRepository<Conversation, Long> {



    List<Conversation> findByMatchCandidateLostPostIdInOrMatchCandidateFoundPostIdIn(
            List<Long> lostPostIds,
            List<Long> foundPostIds
    );

    boolean existsByLostPostIdAndStatusOrFoundPostIdAndStatus(
            Long lostPostId,
            String lostPostStatus,
            Long foundPostId,
            String foundPostStatus
    );


}