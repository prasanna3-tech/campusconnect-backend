package com.campusconnectapp.dtos.postDtos;

import com.campusconnectapp.models.LostFoundPost;

import java.util.List;

public class MatchingPostsResponseDto {

    private List<LostFoundPost> lostFoundPosts;
    private List<Double> scores;

    public MatchingPostsResponseDto() {
    }

    public MatchingPostsResponseDto(
            List<LostFoundPost> lostFoundPosts,
            List<Double> scores) {
        this.lostFoundPosts = lostFoundPosts;
        this.scores = scores;
    }

    public List<LostFoundPost> getLostFoundPosts() {
        return lostFoundPosts;
    }

    public void setLostFoundPosts(List<LostFoundPost> lostFoundPosts) {
        this.lostFoundPosts = lostFoundPosts;
    }

    public List<Double> getScores() {
        return scores;
    }

    public void setScores(List<Double> scores) {
        this.scores = scores;
    }
}
