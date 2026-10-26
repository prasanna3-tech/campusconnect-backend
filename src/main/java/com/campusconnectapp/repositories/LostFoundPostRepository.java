package com.campusconnectapp.repositories;

import com.campusconnectapp.models.LostFoundPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface LostFoundPostRepository
        extends JpaRepository<LostFoundPost, Long> {

    List<LostFoundPost> findByUserId(Long userId);
    void deleteByUserId(Long userId);

    List<LostFoundPost> findByPostTypeAndStatusAndCategoryIdAndDateOccurredBetween(
            String postType,
            String status,
            Long categoryId,
            LocalDate startDate,
            LocalDate endDate
    );
    List<LostFoundPost> findAllByPostType(String postType);

    List<LostFoundPost> findAllByPostTypeOrderByCreatedAtDesc(String postType);

    List<LostFoundPost> findAllByPostTypeAndStatusOrderByCreatedAtDesc(
            String postType,
            String status
    );

    @Modifying
    @Query(value = "DELETE FROM lost_found_posts WHERE id = :postId",
            nativeQuery = true)
    void deletePostDirectly(@Param("postId") Long postId);
}
