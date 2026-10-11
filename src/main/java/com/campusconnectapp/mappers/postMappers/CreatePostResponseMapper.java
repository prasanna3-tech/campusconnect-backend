package com.campusconnectapp.mappers.postMappers;
import com.campusconnectapp.dtos.postDtos.CandidateDto;
import com.campusconnectapp.dtos.postDtos.CreatePostResponse;
import com.campusconnectapp.models.LostFoundPost;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class CreatePostResponseMapper {

    public CreatePostResponse toDto(
            Long postId,
            List<LostFoundPost> matchingPosts) {

        List<CandidateDto> candidateDtos = matchingPosts.stream()
                .map(post -> new CandidateDto(
                        post.getId(),
                        post.getTitle(),
                        post.getDescription(),
                        post.getCategory().getId(),
                        post.getLocation().getId(),
                        post.getLocationDetail(),
                        post.getDateOccurred()
                ))
                .toList();

        return new CreatePostResponse(postId, candidateDtos);
    }
}
