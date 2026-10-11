package com.campusconnectapp.controllers;

import com.campusconnectapp.dtos.postDtos.CreatePostRequestDto;
import com.campusconnectapp.dtos.postDtos.CreatePostResponse;
import com.campusconnectapp.security.CustomUserDetails;
import com.campusconnectapp.services.PostService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/posts")
public class PostController {

    private final PostService lostFoundPostService;

    public PostController(PostService lostFoundPostService) {
        this.lostFoundPostService = lostFoundPostService;
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
}