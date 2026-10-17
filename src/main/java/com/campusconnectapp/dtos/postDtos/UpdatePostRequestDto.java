package com.campusconnectapp.dtos.postDtos;
import java.time.LocalDate;

import jakarta.validation.constraints.*;

public class UpdatePostRequestDto {

    @NotNull(message = "Post ID is required")
    private Long postId;

    @NotBlank(message = "Title is required")
    @Size(max = 150, message = "Title cannot exceed 150 characters")
    private String title;

    @NotNull(message = "Post type is required")
    @Min(value = 1, message = "Post type must be 1 (LOST) or 2 (FOUND)")
    @Max(value = 2, message = "Post type must be 1 (LOST) or 2 (FOUND)")
    private Integer postType;

    @NotBlank(message = "Description is required")
    @Size(max = 1000, message = "Description cannot exceed 1000 characters")
    private String description;

    @NotNull(message = "Category ID is required")
    private Long categoryId;

    @NotNull(message = "Location ID is required")
    private Long locationId;

    @NotBlank(message = "Location details are required")
    private String locationInDetail;

    @NotNull(message = "Date occurred is required")
    private LocalDate dateOccurred;

    public UpdatePostRequestDto() {
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

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getLocationId() {
        return locationId;
    }

    public void setLocationId(Long locationId) {
        this.locationId = locationId;
    }

    public String getLocationInDetail() {
        return locationInDetail;
    }

    public void setLocationInDetail(String locationInDetail) {
        this.locationInDetail = locationInDetail;
    }

    public LocalDate getDateOccurred() {
        return dateOccurred;
    }

    public void setDateOccurred(LocalDate dateOccurred) {
        this.dateOccurred = dateOccurred;
    }
    public Integer getPostType() {
        return postType;
    }

    public void setPostType(Integer postType) {
        this.postType = postType;
    }

    public Long getPostId() {
        return postId;
    }

    public void setPostId(Long postId) {
        this.postId = postId;
    }
}

