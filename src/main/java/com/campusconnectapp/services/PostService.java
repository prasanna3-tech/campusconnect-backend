package com.campusconnectapp.services;
import com.campusconnectapp.dtos.postDtos.*;
import com.campusconnectapp.exceptions.postExceptions.CategoryNotFoundException;
import com.campusconnectapp.exceptions.postExceptions.InvalidPostTypeException;
import com.campusconnectapp.exceptions.postExceptions.LocationNotFoundException;
import com.campusconnectapp.exceptions.postExceptions.PostNotFoundException;
import com.campusconnectapp.mappers.postMappers.CreatePostResponseMapper;
import com.campusconnectapp.models.MatchCandidate;
import com.campusconnectapp.models.User;
import com.campusconnectapp.models.LostFoundPost;
import com.campusconnectapp.mappers.postMappers.PostRequestMapper;
import com.campusconnectapp.repositories.*;
import com.campusconnectapp.exceptions.userExceptions.UserNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
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
    private final MatchCandidateRepository matchCandidateRepository;
    private final ConversationRepository conversationRepository;

    public PostService(
            LostFoundPostRepository lostFoundPostRepository,
            UserRepository userRepository,
            PostRequestMapper postRequestMapper,
            AimlService aimlService,
            CreatePostResponseMapper createPostResponseMapper,

            CategoryRepository categoryRepository,
            LocationRepository locationRepositor,
            MatchCandidateRepository matchCandidateRepository,
            ConversationRepository conversationRepository
    ) {
        this.lostFoundPostRepository = lostFoundPostRepository;
        this.userRepository = userRepository;
        this.postRequestMapper = postRequestMapper;
        this.aimlService = aimlService;
        this.createPostResponseMapper = createPostResponseMapper;

        this.locationRepository=locationRepositor;
        this.categoryRepository=categoryRepository;
        this.matchCandidateRepository = matchCandidateRepository;
        this.conversationRepository = conversationRepository;
    }

    public CreatePostResponse createPost(Long userId, CreatePostRequestDto dto) {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UserNotFoundException(userId));

        LostFoundPost post = postRequestMapper.toEntity(dto);

        post.setUser(user);
        post.setStatus("OPEN");
        LostFoundPost savedPost = lostFoundPostRepository.save(post);

        MatchingPostsResponseDto matchingPosts =
                aimlService.findMatchingPosts(savedPost);

        return createPostResponseMapper.toDto(
                savedPost.getId(),
                matchingPosts
        );
    }
    public List<LostFoundPost> getAllPosts(String postType,String status) {

        String type;

        if (!status.equals("OPEN") && !status.equals("CLOSED")) {
            throw new IllegalArgumentException(
                    "Status must be either OPEN or CLOSED");
        }

        if (postType.equals("LOST")) {
            type = "LOST";
        }else if(postType.equals("FOUND")){
            type = "FOUND";
        }else {
            throw new InvalidPostTypeException(
                    "Invalid post type. Use 'LOST' for LOST POSTS or 'FOUND' for FOUND POSTS."
            );
        }

        return lostFoundPostRepository
                .findAllByPostTypeAndStatusOrderByCreatedAtDesc(type, status);
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

    @Transactional
    public void deletePosts() {
        LocalDateTime sixMonthsAgo = LocalDateTime.now().minusMonths(6);
        LocalDateTime oneMonthAgo = LocalDateTime.now().minusMonths(1);

        List<LostFoundPost> posts = lostFoundPostRepository.findAll();

        for (LostFoundPost post : posts) {

            // Rule 1: Delete CLOSED posts immediately
            if ("CLOSED".equals(post.getStatus())) {
                lostFoundPostRepository.deletePostDirectly(post.getId());
                continue;
            }

            boolean hasActiveConversation =
                    conversationRepository
                            .existsByLostPostIdAndStatusOrFoundPostIdAndStatus(
                                    post.getId(), "ACTIVE",
                                    post.getId(), "ACTIVE"
                            );

            // Rule 4: Keep posts involved in active conversations
            if (hasActiveConversation) {
                continue;
            }

            boolean hasCandidateMatch =
                    matchCandidateRepository.existsByLostPostIdOrFoundPostId(
                            post.getId(), post.getId());

            // Rule 2: No conversation or candidate match; use post creation time
            if (!hasCandidateMatch) {
                if (post.getCreatedAt().isBefore(sixMonthsAgo)) {
                    lostFoundPostRepository.deletePostDirectly(post.getId());
                }
                continue;
            }

            // Rule 3: Check INITIAL candidate matches
            List<MatchCandidate> candidates =
                    matchCandidateRepository
                            .findByLostPostIdOrFoundPostId(
                                    post.getId(), post.getId());

            //candidate matches with any other state cannot come here , it is just useless .

            // if any candidate match was created within a month then we should not delete the post .
            boolean flag = false;
            for (MatchCandidate candidate : candidates) {
                if ("INITIAL".equals(candidate.getStatus())
                        && candidate.getCreatedAt().isAfter(oneMonthAgo)) {
                    flag = true;
                    break;
                }
            }
            if(flag){
                continue;
            }
            //we can delete if no candidate match was created a month ago .
            lostFoundPostRepository.deletePostDirectly(post.getId());
            // if a post is involved in a convo ,which is inactive or the candidate_match which is (accepted or rejected ), doesn't
            // need to be handled because in that cases the post's status is closed .
        }
    }
    @Transactional
    public void deletePost(Long postId) {
        LostFoundPost post = lostFoundPostRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " +postId));

        lostFoundPostRepository.delete(post);
    }

    @Transactional
    public void closePost(Long postId) {
        LostFoundPost post = lostFoundPostRepository.findById(postId)
                .orElseThrow(() -> new PostNotFoundException("Post not found with ID: " +postId));

        post.setStatus("CLOSED");

        lostFoundPostRepository.save(post);
    }

}