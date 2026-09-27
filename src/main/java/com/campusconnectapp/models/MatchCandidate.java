package com.campusconnectapp.models;

import jakarta.persistence.*;

import java.math.BigDecimal;

@Entity
@Table(name = "match_candidates")
public class MatchCandidate {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "lost_post_id", nullable = false)
    private LostFoundPost lostPost;

    @ManyToOne
    @JoinColumn(name = "found_post_id", nullable = false)
    private LostFoundPost foundPost;

    @Column(name = "match_score", nullable = false, precision = 5, scale = 4)
    private BigDecimal matchScore;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @OneToOne(mappedBy = "matchCandidate")
    private Conversation conversation;


    // No-argument constructor
    public MatchCandidate() {
    }


    // All-argument constructor
    public MatchCandidate(Long id, LostFoundPost lostPost,
                          LostFoundPost foundPost, BigDecimal matchScore,
                          String status) {

        this.id = id;
        this.lostPost = lostPost;
        this.foundPost = foundPost;
        this.matchScore = matchScore;
        this.status = status;
    }


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LostFoundPost getLostPost() {
        return lostPost;
    }

    public void setLostPost(LostFoundPost lostPost) {
        this.lostPost = lostPost;
    }

    public LostFoundPost getFoundPost() {
        return foundPost;
    }

    public void setFoundPost(LostFoundPost foundPost) {
        this.foundPost = foundPost;
    }

    public BigDecimal getMatchScore() {
        return matchScore;
    }

    public void setMatchScore(BigDecimal matchScore) {
        this.matchScore = matchScore;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
    public Conversation getConversation() {
        return conversation;
    }
    public void setConversation(Conversation conversation) {
        this.conversation = conversation;
    }
}
