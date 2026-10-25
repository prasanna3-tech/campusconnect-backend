package com.campusconnectapp.utils;

import com.campusconnectapp.models.LostFoundPost;
import com.campusconnectapp.models.MatchCandidate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class ObjectCreationUtil {
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
