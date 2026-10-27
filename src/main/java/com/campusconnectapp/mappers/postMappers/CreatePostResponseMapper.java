package com.campusconnectapp.mappers.postMappers;
import com.campusconnectapp.dtos.postDtos.CandidateDto;
import com.campusconnectapp.dtos.postDtos.CreatePostResponse;
import com.campusconnectapp.dtos.postDtos.MatchingPostsResponseDto;
import com.campusconnectapp.models.LostFoundPost;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreatePostResponseMapper {

    public CreatePostResponse toDto(
            Long postId,
            MatchingPostsResponseDto matchingPosts) {

        List<CandidateDto> candidateDtos = matchingPosts.getLostFoundPosts().stream()
                .map(post -> new CandidateDto(
                        post.getId(),
                        post.getTitle(),
                        post.getDescription(),
                        post.getCategory().getId(),
                        post.getLocation().getId(),
                        post.getLocationDetail(),
                        post.getDateOccurred(),
                        post.getCreatedAt()
                ))
                .toList();

        return new CreatePostResponse(postId, candidateDtos,matchingPosts.getScores());
    }

    public List<CandidateDto> toCandidateDtos(
            List<LostFoundPost> posts) {

        return posts.stream()
                .map(post -> new CandidateDto(
                        post.getId(),
                        post.getTitle(),
                        post.getDescription(),
                        post.getCategory().getId(),
                        post.getLocation().getId(),
                        post.getLocationDetail(),
                        post.getDateOccurred(),
                        post.getCreatedAt()
                ))
                .toList();
    }
    public CandidateDto toCandidateDto(LostFoundPost post) {
        return new CandidateDto(
                post.getId(),
                post.getTitle(),
                post.getDescription(),
                post.getCategory().getId(),
                post.getLocation().getId(),
                post.getLocationDetail(),
                post.getDateOccurred(),
                post.getCreatedAt()
        );
    }
}
