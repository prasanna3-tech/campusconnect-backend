package com.campusconnectapp.dtos.postDtos;
import java.time.LocalDate;

public class CandidateDto {

    private Long postId;
    private String title;
    private String description;
    private Long categoryId;
    private Long locationId;
    private String locationInDetail;
    private LocalDate dateOccurred;

    public CandidateDto() {

    }

    public CandidateDto(
            Long postId,
            String title,
            String description,
            Long categoryId,
            Long locationId,
            String locationInDetail,
            LocalDate dateOccurred) {

        this.postId = postId;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.locationId = locationId;
        this.locationInDetail = locationInDetail;
        this.dateOccurred = dateOccurred;
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
}
