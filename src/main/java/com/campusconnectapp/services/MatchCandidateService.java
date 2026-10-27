package com.campusconnectapp.services;

import com.campusconnectapp.mappers.MatchCandidateMappers.CreateMatchCandidateMapper;
import com.campusconnectapp.models.LostFoundPost;
import com.campusconnectapp.models.MatchCandidate;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
public class MatchCandidateService {

    private final CreateMatchCandidateMapper createMatchCandidateMapper;


    public MatchCandidateService(CreateMatchCandidateMapper createMatchCandidateMapper){
        this.createMatchCandidateMapper=createMatchCandidateMapper;
    }
    public MatchCandidate createMatchCandidate(
            LostFoundPost lostPost,
            LostFoundPost foundPost,
            double matchScore) {

        MatchCandidate matchCandidate = createMatchCandidateMapper.createMatchCandidate(lostPost,foundPost,matchScore);

        return matchCandidate;
    }
}
