package com.campusconnectapp.mappers.postMappers;

import com.campusconnectapp.dtos.postDtos.MatchingPostsResponseDto;
import com.campusconnectapp.models.LostFoundPost;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class MatchingPostsResponseMapper {

    public MatchingPostsResponseDto toDto(
            List<LostFoundPost> lostFoundPosts,
            List<Double> scores) {

        MatchingPostsResponseDto response = new MatchingPostsResponseDto();

        response.setLostFoundPosts(lostFoundPosts);
        response.setScores(scores);

        return response;
    }
}
