package com.campusconnectapp.repositories;

import com.campusconnectapp.models.MatchCandidate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MatchCandidateRepository
        extends JpaRepository<MatchCandidate, Long> {

    void deleteByLostPostIdInOrFoundPostIdIn(
            List<Long> lostPostIds,
            List<Long> foundPostIds
    );
    boolean existsByLostPostIdOrFoundPostId(
            Long lostPostId,
            Long foundPostId
    );
    List<MatchCandidate> findByLostPostIdOrFoundPostId(
            Long lostPostId,
            Long foundPostId
    );
}
