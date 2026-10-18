package com.campusconnectapp.dtos.postDtos;
import java.time.LocalDate;
import java.time.LocalDateTime;

public class CandidateDto {

    private Long postId;
    private String title;
    private String description;
    private Long categoryId;
    private Long locationId;
    private String locationInDetail;
    private LocalDate dateOccurred;
    private LocalDateTime created_at;

    public CandidateDto() {

    }

    public CandidateDto(
            Long postId,
            String title,
            String description,
            Long categoryId,
            Long locationId,
            String locationInDetail,
            LocalDate dateOccurred,
            LocalDateTime created_at) {

        this.postId = postId;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.locationInDetail = locationInDetail;
        this.dateOccurred = dateOccurred;
        this.created_at = created_at;
    }

    public Long getPostId() {
        return postId;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public String getLocationInDetail() {
        return locationInDetail;
    }

    public LocalDate getDateOccurred() {
        return dateOccurred;
    }

    public LocalDateTime getCreated_at() {
        return created_at;
    }
}
