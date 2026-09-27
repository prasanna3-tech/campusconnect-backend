package com.campusconnectapp.models;

import jakarta.persistence.*;

import java.util.List;

@Entity
@Table(name = "conversations")
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    @JoinColumn(name = "match_candidate_id", nullable = false, unique = true)
    private MatchCandidate matchCandidate;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @OneToMany(mappedBy = "conversation")
    private List<Message> messages;

    // No-argument constructor
    public Conversation() {
    }


    // All-argument constructor
    public Conversation(Long id, MatchCandidate matchCandidate, String status) {
        this.id = id;
        this.matchCandidate = matchCandidate;
        this.status = status;
    }


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public MatchCandidate getMatchCandidate() {
        return matchCandidate;
    }

    public void setMatchCandidate(MatchCandidate matchCandidate) {
        this.matchCandidate = matchCandidate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }


}