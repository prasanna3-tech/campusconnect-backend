package com.campusconnectapp.dtos.aimlDtos;
import java.util.List;

public class AiPredictionRequest {

    private List<TextPair> pairs;

    public AiPredictionRequest() {
    }

    public AiPredictionRequest(List<TextPair> pairs) {
        this.pairs = pairs;
    }

    public List<TextPair> getPairs() {
        return pairs;
    }

    public void setPairs(List<TextPair> pairs) {
        this.pairs = pairs;
    }
}
