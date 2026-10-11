package com.campusconnectapp.services;
import com.campusconnectapp.dtos.postDtos.CreatePostResponse;
import com.campusconnectapp.mappers.postMappers.CreatePostResponseMapper;
import com.campusconnectapp.models.User;
import com.campusconnectapp.models.LostFoundPost;
import com.campusconnectapp.dtos.postDtos.CreatePostRequestDto;
import com.campusconnectapp.mappers.postMappers.PostRequestMapper;
import com.campusconnectapp.repositories.UserRepository;
import com.campusconnectapp.repositories.LostFoundPostRepository;
import com.campusconnectapp.exceptions.userExceptions.UserNotFoundException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class PostService
{
    private final LostFoundPostRepository lostFoundPostRepository;
    private final UserRepository userRepository;
    private final PostRequestMapper postRequestMapper;
    private final AimlService aimlService;
    private final CreatePostResponseMapper createPostResponseMapper;



    public PostService(
            LostFoundPostRepository lostFoundPostRepository,
            UserRepository userRepository,
            PostRequestMapper postRequestMapper,
            AimlService aimlService,
            CreatePostResponseMapper createPostResponseMapper
    ) {
        this.lostFoundPostRepository = lostFoundPostRepository;
        this.userRepository = userRepository;
        this.postRequestMapper = postRequestMapper;
        this.aimlService = aimlService;
        this.createPostResponseMapper = createPostResponseMapper;
    }

    public CreatePostResponse createPost(Long userId, CreatePostRequestDto dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        LostFoundPost post = postRequestMapper.toEntity(dto);

        post.setUser(user);
        post.setStatus("OPEN");
        LostFoundPost savedPost = lostFoundPostRepository.save(post);

        List<LostFoundPost> matchingPosts =
                aimlService.findMatchingPosts(savedPost);

        return createPostResponseMapper.toDto(
                savedPost.getId(),
                matchingPosts
        );
    }
}