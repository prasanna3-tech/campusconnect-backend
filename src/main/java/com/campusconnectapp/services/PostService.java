package com.campusconnectapp.services;
import com.campusconnectapp.dtos.postDtos.CandidateDto;
import com.campusconnectapp.dtos.postDtos.CreatePostResponse;
import com.campusconnectapp.dtos.postDtos.UpdatePostRequestDto;
import com.campusconnectapp.exceptions.postExceptions.CategoryNotFoundException;
import com.campusconnectapp.exceptions.postExceptions.InvalidPostTypeException;
import com.campusconnectapp.exceptions.postExceptions.LocationNotFoundException;
import com.campusconnectapp.exceptions.postExceptions.PostNotFoundException;
import com.campusconnectapp.mappers.postMappers.CreatePostResponseMapper;
import com.campusconnectapp.models.User;
import com.campusconnectapp.models.LostFoundPost;
import com.campusconnectapp.dtos.postDtos.CreatePostRequestDto;
import com.campusconnectapp.mappers.postMappers.PostRequestMapper;
import com.campusconnectapp.repositories.CategoryRepository;
import com.campusconnectapp.repositories.LocationRepository;
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

    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;




    public PostService(
            LostFoundPostRepository lostFoundPostRepository,
            UserRepository userRepository,
            PostRequestMapper postRequestMapper,
            AimlService aimlService,
            CreatePostResponseMapper createPostResponseMapper,

            CategoryRepository categoryRepository,
            LocationRepository locationRepositor
    ) {
        this.lostFoundPostRepository = lostFoundPostRepository;
        this.userRepository = userRepository;
        this.postRequestMapper = postRequestMapper;
        this.aimlService = aimlService;
        this.createPostResponseMapper = createPostResponseMapper;

        this.locationRepository=locationRepositor;
        this.categoryRepository=categoryRepository;
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
    public List<LostFoundPost> getAllPosts(int postType) {

        String type;

        if (postType == 1) {
            type = "LOST";
        }else if(postType == 2){
            type = "FOUND";
        }else {
            throw new InvalidPostTypeException(
                    "Invalid post type. Use 1 for LOST or 2 for FOUND."
            );
        }

        return lostFoundPostRepository.findAllByPostType(type);
    }

    public CandidateDto updatePost(
            UpdatePostRequestDto dto) {

        LostFoundPost post = lostFoundPostRepository.findById(dto.getPostId())
                .orElseThrow(() -> new PostNotFoundException(
                        "Post not found with ID: " + dto.getPostId()
                ));

        String postType;

        if (dto.getPostType() == 1) {
            postType = "LOST";
        } else if (dto.getPostType() == 2) {
            postType = "FOUND";
        } else {
            throw new InvalidPostTypeException(
                    "Invalid post type. Use 1 for LOST or 2 for FOUND."
            );
        }

        post.setTitle(dto.getTitle());
        post.setPostType(postType);
        post.setDescription(dto.getDescription());
        post.setCategory(categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() -> new CategoryNotFoundException(dto.getCategoryId()
                )));
        post.setLocation(locationRepository.findById(dto.getLocationId())
                .orElseThrow(() -> new LocationNotFoundException(dto.getLocationId()
                )));
        post.setLocationDetail(dto.getLocationInDetail());
        post.setDateOccurred(dto.getDateOccurred());

        LostFoundPost updatedPost = lostFoundPostRepository.save(post);

        return createPostResponseMapper.toCandidateDto(updatedPost);
    }
}