package com.campusconnectapp.services;

import com.campusconnectapp.dtos.postDtos.CreatePostResponse;
import com.campusconnectapp.dtos.postDtos.MatchingPostsResponseDto;
import com.campusconnectapp.exceptions.postExceptions.PostNotFoundException;
import com.campusconnectapp.mappers.MatchCandidateMappers.CreateMatchCandidateMapper;
import com.campusconnectapp.mappers.postMappers.CreatePostResponseMapper;
import com.campusconnectapp.models.LostFoundPost;
import com.campusconnectapp.models.MatchCandidate;
import com.campusconnectapp.repositories.LostFoundPostRepository;
import com.campusconnectapp.repositories.MatchCandidateRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Service
public class MatchCandidateService {

    private final CreateMatchCandidateMapper createMatchCandidateMapper;
    private final LostFoundPostRepository lostFoundPostRepository;
    private final MatchCandidateRepository matchCandidateRepository;
    private final CreatePostResponseMapper createPostResponseMapper;


    public MatchCandidateService(CreateMatchCandidateMapper createMatchCandidateMapper,
                                 LostFoundPostRepository lostFoundPostRepository,
                                 CreatePostResponseMapper createPostResponseMapper,
                                 MatchCandidateRepository matchCandidateRepository){
        this.createMatchCandidateMapper=createMatchCandidateMapper;
        this.matchCandidateRepository=matchCandidateRepository;
        this.lostFoundPostRepository=lostFoundPostRepository;
        this.createPostResponseMapper = createPostResponseMapper;
    }
    public MatchCandidate createMatchCandidate(
            LostFoundPost lostPost,
            LostFoundPost foundPost,
            double matchScore) {

        MatchCandidate matchCandidate = createMatchCandidateMapper.createMatchCandidate(lostPost,foundPost,matchScore);

        return matchCandidate;
    }
    public CreatePostResponse retrieveMatchCandidate(Long postId) {

        LostFoundPost post = lostFoundPostRepository.findById(postId)
                .orElseThrow(() ->  new PostNotFoundException("Post not found with ID: " +postId));

        List<MatchCandidate> candidates =
                matchCandidateRepository
                        .findByLostPostIdAndStatusOrFoundPostIdAndStatus(
                                postId, "INITIAL",
                                postId, "INITIAL"
                        );

        List<LostFoundPost> matchingPosts = new ArrayList<>();
        List<Double> scores = new ArrayList<>();

        for (MatchCandidate candidate : candidates) {

            LostFoundPost otherPost;

            if (candidate.getLostPost().getId().equals(postId)) {
                otherPost = candidate.getFoundPost();
            } else {
                otherPost = candidate.getLostPost();
            }

            matchingPosts.add(otherPost);
            scores.add(candidate.getMatchScore().doubleValue());
        }

        MatchingPostsResponseDto dto =  new MatchingPostsResponseDto(matchingPosts, scores);

        return createPostResponseMapper.toDto(
                postId,
                dto
        );
    }
}
