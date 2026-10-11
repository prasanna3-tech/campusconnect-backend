package com.campusconnectapp.dtos.postDtos;
import com.campusconnectapp.models.LostFoundPost;
import java.util.List;
public class CreatePostResponse {

    private Long postId;
    private List<CandidateDto> matchingPosts;

    public CreatePostResponse() {
    }

    public CreatePostResponse(
            Long postId,
            List<CandidateDto> matchingPosts) {

        this.postId = postId;
        this.matchingPosts = matchingPosts;
    }

    public Long getPostId() {
        return postId;
    }

    public List<CandidateDto> getMatchingPosts() {
        return matchingPosts;
    }
}