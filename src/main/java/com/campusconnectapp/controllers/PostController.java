package com.campusconnectapp.controllers;

import com.campusconnectapp.dtos.postDtos.CandidateDto;
import com.campusconnectapp.dtos.postDtos.CreatePostRequestDto;
import com.campusconnectapp.dtos.postDtos.CreatePostResponse;
import com.campusconnectapp.dtos.postDtos.UpdatePostRequestDto;
import com.campusconnectapp.mappers.postMappers.CreatePostResponseMapper;
import com.campusconnectapp.models.LostFoundPost;
import com.campusconnectapp.security.CustomUserDetails;
import com.campusconnectapp.services.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService lostFoundPostService;
    private final CreatePostResponseMapper createPostResponseMapper;


    public PostController(PostService lostFoundPostService,
                          CreatePostResponseMapper createPostResponseMapper
    ) {

        this.lostFoundPostService = lostFoundPostService;
        this.createPostResponseMapper=createPostResponseMapper;
    }

    @PostMapping
    public ResponseEntity< CreatePostResponse> createPost(
            @Valid @RequestBody CreatePostRequestDto dto,
            Authentication authentication
    ) {
        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        Long userId = userDetails.getUserId();

        CreatePostResponse response = lostFoundPostService.createPost(userId, dto);

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body(response);
    }

    @GetMapping("/all")
    public ResponseEntity<List<CandidateDto>> getAllPosts(
            @RequestParam String postType,
            @RequestParam String status) {

        List<LostFoundPost> posts =
                lostFoundPostService.getAllPosts(postType, status);

        List<CandidateDto> response =
                createPostResponseMapper.toCandidateDtos(posts);

        return ResponseEntity.ok(response);
    }
    @PutMapping("/update")
    public ResponseEntity<CandidateDto> updatePost(
            @Valid @RequestBody UpdatePostRequestDto dto) {

        CandidateDto response = lostFoundPostService.updatePost(dto);

        return ResponseEntity.ok(response);
    }
    @DeleteMapping("/delete-expired")
    public ResponseEntity<String> deletePosts() {
        lostFoundPostService.deletePosts();
        return ResponseEntity.ok("Post deletion process completed successfully.");
    }
    @DeleteMapping("/{postId}")
    public ResponseEntity<String> deletePost(@PathVariable Long postId) {
        lostFoundPostService.deletePost(postId);
        return ResponseEntity.ok("Post deletion process completed successfully.");
    }
}