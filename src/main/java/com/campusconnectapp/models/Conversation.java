package com.campusconnectapp.models;

import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.List;

@Entity
@Table(
        name = "conversations",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uq_conversation_post_pair",
                        columnNames = {"lost_post_id", "found_post_id"}
                )
        }
)
public class Conversation {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Match candidate relationship
    @ManyToOne(optional = true)
    @JoinColumn(
            name = "match_candidate_id",
            nullable = true,
            foreignKey = @ForeignKey(
                    name = "fk_conversations_match_candidate"
            )
    )
    private MatchCandidate matchCandidate;

    // Lost and found post relationships
    @ManyToOne(optional = true)
    @JoinColumn(
            name = "lost_post_id",
            nullable = true,
            foreignKey = @ForeignKey(
                    name = "fk_conversations_lost_post"
            )
    )
    private LostFoundPost lostPost;

    @ManyToOne(fetch = FetchType.LAZY, optional = true)
    @JoinColumn(
            name = "found_post_id",
            nullable = true,
            foreignKey = @ForeignKey(
                    name = "fk_conversations_found_post"
            )
    )
    private LostFoundPost foundPost;

    // User relationships
    @ManyToOne(optional = true)
    @JoinColumn(
            name = "lost_user_id",
            nullable = true,
            foreignKey = @ForeignKey(
                    name = "fk_conversations_lost_user"
            )
    )
    private User lostUser;

    @ManyToOne(optional = true)
    @JoinColumn(
            name = "found_user_id",
            nullable = true,
            foreignKey = @ForeignKey(
                    name = "fk_conversations_found_user"
            )
    )
    private User foundUser;

    // Conversation metadata
    @Column(name = "source_type", nullable = false, length = 20)
    private String sourceType;

    @Column(name = "status", nullable = false, length = 20)
    private String status = "ACTIVE";

    @Column(name = "created_at", nullable = false, updatable = false)      //we do need to send time
    private LocalDateTime createdAt;

    @Column(name = "expires_at", nullable = false)
    private LocalDateTime expiresAt;

    @Column(name = "convo_title_1", length = 150)
    private String convoTitle1;

    @Column(name = "convo_title_2", length = 150)
    private String convoTitle2;

    // Resolution decisions
    @Column(name = "lost_user_decision", nullable = false, length = 20)
    private String lostUserDecision = "PENDING";

    @Column(name = "found_user_decision", nullable = false, length = 20)
    private String foundUserDecision = "PENDING";

    // Messages belonging to this conversation
    @OneToMany(mappedBy = "conversation")
    private List<Message> messages;

    // No-argument constructor
    public Conversation() {
    }

    public Conversation(
            Long id,
            MatchCandidate matchCandidate,
            LostFoundPost lostPost,
            LostFoundPost foundPost,
            User lostUser,
            User foundUser,
            String sourceType,
            String status,
            LocalDateTime createdAt,
            LocalDateTime expiresAt,
            String convoTitle1,
            String convoTitle2,
            String lostUserDecision,
            String foundUserDecision,
            List<Message> messages
    ) {
        this.id = id;
        this.matchCandidate = matchCandidate;
        this.lostPost = lostPost;
        this.foundPost = foundPost;
        this.lostUser = lostUser;
        this.foundUser = foundUser;
        this.sourceType = sourceType;
        this.status = status;
        this.createdAt = createdAt;
        this.expiresAt = expiresAt;
        this.convoTitle1 = convoTitle1;
        this.convoTitle2 = convoTitle2;
        this.lostUserDecision = lostUserDecision;
        this.foundUserDecision = foundUserDecision;
        this.messages = messages;
    }

    // Getters and setters

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

    public User getLostUser() {
        return lostUser;
    }

    public void setLostUser(User lostUser) {
        this.lostUser = lostUser;
    }

    public User getFoundUser() {
        return foundUser;
    }

    public void setFoundUser(User foundUser) {
        this.foundUser = foundUser;
    }

    public String getSourceType() {
        return sourceType;
    }

    public void setSourceType(String sourceType) {
        this.sourceType = sourceType;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getExpiresAt() {
        return expiresAt;
    }

    public void setExpiresAt(LocalDateTime expiresAt) {
        this.expiresAt = expiresAt;
    }

    public String getConvoTitle1() {
        return convoTitle1;
    }

    public void setConvoTitle1(String convoTitle1) {
        this.convoTitle1 = convoTitle1;
    }

    public String getConvoTitle2() {
        return convoTitle2;
    }

    public void setConvoTitle2(String convoTitle2) {
        this.convoTitle2 = convoTitle2;
    }

    public String getLostUserDecision() {
        return lostUserDecision;
    }

    public void setLostUserDecision(String lostUserDecision) {
        this.lostUserDecision = lostUserDecision;
    }

    public String getFoundUserDecision() {
        return foundUserDecision;
    }

    public void setFoundUserDecision(String foundUserDecision) {
        this.foundUserDecision = foundUserDecision;
    }

    public List<Message> getMessages() {
        return messages;
    }

    public void setMessages(List<Message> messages) {
        this.messages = messages;
    }
    public void setCreatedAt() {
        this.createdAt = LocalDateTime.now();
    }

}