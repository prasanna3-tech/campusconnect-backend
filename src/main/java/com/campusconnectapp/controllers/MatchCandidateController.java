package com.campusconnectapp.controllers;

import com.campusconnectapp.dtos.postDtos.CreatePostResponse;
import com.campusconnectapp.dtos.postDtos.MatchingPostsResponseDto;
import com.campusconnectapp.services.MatchCandidateService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/match-candidates")
public class MatchCandidateController {

    private final MatchCandidateService matchCandidateService;

    public MatchCandidateController(MatchCandidateService matchCandidateService) {
        this.matchCandidateService = matchCandidateService;
    }

    @GetMapping("/{postId}")
    public ResponseEntity<CreatePostResponse> retrieveMatchCandidate(
            @PathVariable Long postId) {

        CreatePostResponse response =
                matchCandidateService.retrieveMatchCandidate(postId);

        return ResponseEntity.ok(response);
    }
}
