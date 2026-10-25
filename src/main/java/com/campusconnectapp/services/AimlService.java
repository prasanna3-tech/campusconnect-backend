package com.campusconnectapp.services;
import com.campusconnectapp.dtos.aimlDtos.*;
import com.campusconnectapp.models.LostFoundPost;
import com.campusconnectapp.models.MatchCandidate;
import com.campusconnectapp.repositories.LostFoundPostRepository;
import com.campusconnectapp.repositories.MatchCandidateRepository;
import com.campusconnectapp.utils.ObjectCreationUtil;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;
import java.util.List;
import java.time.LocalDate;
import java.util.ArrayList;

@Service
public class AimlService {

    private final LostFoundPostRepository lostFoundPostRepository;
    private final ObjectCreationUtil objectCreationUtil;
    private final MatchCandidateRepository matchCandidateRepository;

    private final RestClient aiRestClient;

    public AimlService(
            LostFoundPostRepository lostFoundPostRepository,
            ObjectCreationUtil objectCreationUtil,
            MatchCandidateRepository matchCandidateRepository,
            RestClient aiRestClient) {

        this.lostFoundPostRepository = lostFoundPostRepository;
        this.aiRestClient = aiRestClient;
        this.objectCreationUtil = objectCreationUtil;
        this.matchCandidateRepository = matchCandidateRepository;
    }

    public List<LostFoundPost> findMatchingPosts(LostFoundPost post) {

        // 1. Retrieve eligible candidate posts
        List<LostFoundPost> candidates = retrieveCandidates(post);

        if (candidates.isEmpty()) {
            return candidates;
        }

        // 2. Create one text pair per candidate
        List<TextPair> pairs = createTextPairs(post, candidates);

        // 3. Get scores from FastAPI
        AiPredictionResponse response = getPredictions(pairs);

        List<AiPrediction> predictions = response.getPredictions();

        // 4. Keep candidates that pass the threshold
        List<LostFoundPost> matchingPosts = new ArrayList<>();

        double threshold = 0.2; // Example only; replace with your chosen threshold

        for (int i = 0; i < candidates.size(); i++) {
            if (predictions.get(i).getScore() >= threshold) {
                matchingPosts.add(candidates.get(i));

                LostFoundPost lostPost;
                LostFoundPost foundPost;

                if ("LOST".equals(post.getPostType())) {
                    lostPost = post;
                    foundPost = candidates.get(i);
                } else {
                    lostPost = candidates.get(i);
                    foundPost = post;
                }

                MatchCandidate matchCandidate =
                        objectCreationUtil.createMatchCandidate(
                                lostPost,
                                foundPost,
                                predictions.get(i).getScore()
                        );

                matchCandidateRepository.save(matchCandidate);
            }
        }

        return matchingPosts;
    }

    public AiPredictionResponse getPredictions(List<TextPair> pairs) {

        AiPredictionRequest request = new AiPredictionRequest(pairs);

        return aiRestClient.post()
                .uri("/predict")
                .body(request)
                .retrieve()
                .body(AiPredictionResponse.class);
    }

    public List<TextPair> createTextPairs(LostFoundPost post,
                                          List<LostFoundPost> candidates) {

        List<TextPair> pairs = new ArrayList<>();

        String postText =
                "Title: " + post.getTitle()
                        + "\nDescription: " + post.getDescription();

        for (LostFoundPost candidate : candidates) {

            String candidateText =
                    "Title: " + candidate.getTitle()
                            + "\nDescription: " + candidate.getDescription();

            TextPair pair = new TextPair(postText, candidateText);

            pairs.add(pair);
        }

        return pairs;
    }

    public List<LostFoundPost> retrieveCandidates(LostFoundPost post) {

        String oppositePostType;

        if ("LOST".equals(post.getPostType())) {
            oppositePostType = "FOUND";
        } else if ("FOUND".equals(post.getPostType())) {
            oppositePostType = "LOST";
        } else {
            throw new IllegalArgumentException("Invalid post type");
        }

        LocalDate startDate = post.getDateOccurred().minusDays(30);
        LocalDate endDate = post.getDateOccurred().plusDays(30);

        return lostFoundPostRepository
                .findByPostTypeAndStatusAndCategoryIdAndDateOccurredBetween(
                        oppositePostType,
                        "OPEN",
                        post.getCategory().getId(),
                        startDate,
                        endDate
                );
        }
}
