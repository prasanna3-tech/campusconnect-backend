package com.campusconnectapp.controllers;

import com.campusconnectapp.models.LostFoundPost;
import com.campusconnectapp.repositories.LostFoundPostRepository;
import com.campusconnectapp.services.AimlService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/temp/aiml")
public class TempAimlController {

    private final AimlService aimlService;
    private final LostFoundPostRepository lostFoundPostRepository;

    public TempAimlController(
            AimlService aimlService,
            LostFoundPostRepository lostFoundPostRepository
    ) {
        this.aimlService = aimlService;
        this.lostFoundPostRepository = lostFoundPostRepository;
    }

    @GetMapping("/candidates/{postId}")
    public String testCandidateRetrieval(@PathVariable Long postId) {

        LostFoundPost post = lostFoundPostRepository.findById(postId)
                .orElseThrow(() ->
                        new RuntimeException("Post not found: " + postId)
                );

        aimlService.retrieveCandidates(post);

        return "Candidate retrieval executed. Check the IntelliJ console.";
    }
}