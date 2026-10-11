package com.campusconnectapp.dtos.aimlDtos;

import java.util.List;

public class AiPredictionResponse {

    private List<AiPrediction> predictions;

    public AiPredictionResponse() {
    }

    public List<AiPrediction> getPredictions() {
        return predictions;
    }

    public void setPredictions(List<AiPrediction> predictions) {
        this.predictions = predictions;
    }
}
