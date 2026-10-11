package com.campusconnectapp.dtos.aimlDtos;

public class TextPair {

    private String text1;
    private String text2;

    public TextPair(String text1, String text2) {
        this.text1 = text1;
        this.text2 = text2;
    }

    public String getText1() {
        return text1;
    }

    public String getText2() {
        return text2;
    }
}
