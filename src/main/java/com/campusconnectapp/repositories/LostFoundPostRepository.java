package com.campusconnectapp.repositories;

import com.campusconnectapp.models.LostFoundPost;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LostFoundPostRepository
        extends JpaRepository<LostFoundPost, Long> {

    List<LostFoundPost> findByUserId(Long userId);
    void deleteByUserId(Long userId);
}
