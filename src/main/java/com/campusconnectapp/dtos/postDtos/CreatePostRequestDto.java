package com.campusconnectapp.dtos.postDtos;
import jakarta.validation.constraints.*;

import java.time.LocalDate;

public class CreatePostRequestDto {

    @NotBlank
    @Size(max = 150)
    private String title;

    @NotBlank
    @Size(max = 1000)
    private String description;

    @NotNull
    @Min(1)
    @Max(2)
    private Long postType;

    @NotNull
    private Long categoryId;

    @NotNull
    private Long locationId;

    @Size(max = 255)
    private String locationDetail;

    @NotNull
    private LocalDate dateOccurred;

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

    public Long getPostType() {
        return postType;
    }

    public void setPostType(Long postType) {
        this.postType = postType;
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
}