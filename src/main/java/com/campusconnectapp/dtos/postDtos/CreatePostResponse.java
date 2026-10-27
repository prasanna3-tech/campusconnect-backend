package com.campusconnectapp.dtos.postDtos;
import com.campusconnectapp.models.LostFoundPost;
import java.util.List;
public class CreatePostResponse {

    private Long postId;
    private List<CandidateDto> matchingPosts;
    private List<Double> scores;

    public CreatePostResponse() {
    }

    public CreatePostResponse(
            Long postId,
            List<CandidateDto> matchingPosts,
            List<Double> scores ) {

        this.postId = postId;
        this.matchingPosts = matchingPosts;
        this.scores = scores;
    }

    public Long getPostId() {
        return postId;
    }

    public List<CandidateDto> getMatchingPosts() {
        return matchingPosts;
    }

    public List<Double> getScores() {
        return scores;
    }
}