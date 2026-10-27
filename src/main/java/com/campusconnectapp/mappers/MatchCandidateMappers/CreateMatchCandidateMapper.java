package com.campusconnectapp.mappers.MatchCandidateMappers;

import com.campusconnectapp.models.LostFoundPost;
import com.campusconnectapp.models.MatchCandidate;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;

@Component
public class CreateMatchCandidateMapper {
    public MatchCandidate createMatchCandidate(
            LostFoundPost lostPost,
            LostFoundPost foundPost,
            double matchScore) {

        MatchCandidate matchCandidate = new MatchCandidate();

        matchCandidate.setLostPost(lostPost);
        matchCandidate.setFoundPost(foundPost);
        matchCandidate.setMatchScore(BigDecimal.valueOf(matchScore));
        matchCandidate.setStatus("INITIAL");

        return matchCandidate;
    }
}
