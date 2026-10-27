package com.campusconnectapp.dtos.aimlDtos;


public class AiPrediction {

    private String text1;
    private String text2;
    private double score;
    //add postId so that we can know which post's AiPrediction this is .

    public AiPrediction() {
    }

    public String getText1() {
        return text1;
    }

    public void setText1(String text1) {
        this.text1 = text1;
    }

    public String getText2() {
        return text2;
    }

    public void setText2(String text2) {
        this.text2 = text2;
    }

    public double getScore() {
        return score;
    }

    public void setScore(double score) {
        this.score = score;
    }
}

//add postId so that we can know which post's AiPrediction this is .
