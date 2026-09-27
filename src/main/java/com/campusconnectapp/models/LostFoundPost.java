package com.campusconnectapp.models;

import jakarta.persistence.*;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "lost_found_posts")
public class LostFoundPost {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @Column(name = "post_type", nullable = false, length = 10)
    private String postType;

    @ManyToOne
    @JoinColumn(name = "category_id", nullable = false)
    private Category category;

    @Column(name = "title", nullable = false, length = 150)
    private String title;

    @Column(name = "description", nullable = false, columnDefinition = "TEXT")
    private String description;

    @ManyToOne
    @JoinColumn(name = "location_id", nullable = false)
    private Location location;

    @Column(name = "location_detail", length = 255)
    private String locationDetail;

    @Column(name = "date_occurred", nullable = false)
    private LocalDate dateOccurred;

    @Column(name = "status", nullable = false, length = 20)
    private String status;

    @OneToMany(mappedBy = "lostPost")
    private List<MatchCandidate> lostMatchCandidates;

    @OneToMany(mappedBy = "foundPost")
    private List<MatchCandidate> foundMatchCandidates;


    // No-argument constructor
    public LostFoundPost() {
    }


    // All-argument constructor
    public LostFoundPost(Long id, User user, String postType,
                         Category category, String title, String description,
                         Location location, String locationDetail,
                         LocalDate dateOccurred, String status) {

        this.id = id;
        this.user = user;
        this.postType = postType;
        this.category = category;
        this.title = title;
        this.description = description;
        this.location = location;
        this.locationDetail = locationDetail;
        this.dateOccurred = dateOccurred;
        this.status = status;
    }


    // Getters and Setters

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getPostType() {
        return postType;
    }

    public void setPostType(String postType) {
        this.postType = postType;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getLocationDetail() {
        return locationDetail;
    }

    public void setLocationDetail(String locationDetail) {
        this.locationDetail = locationDetail;
    }

    public LocalDate getDateOccurred() {
        return dateOccurred;
    }

    public void setDateOccurred(LocalDate dateOccurred) {
        this.dateOccurred = dateOccurred;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<MatchCandidate> getLostMatchCandidates() {
        return lostMatchCandidates;
    }

    public void setLostMatchCandidates(List<MatchCandidate> lostMatchCandidates) {
        this.lostMatchCandidates = lostMatchCandidates;
    }

    public List<MatchCandidate> getFoundMatchCandidates() {
        return foundMatchCandidates;
    }

    public void setFoundMatchCandidates(List<MatchCandidate> foundMatchCandidates) {
        this.foundMatchCandidates = foundMatchCandidates;
    }
}
