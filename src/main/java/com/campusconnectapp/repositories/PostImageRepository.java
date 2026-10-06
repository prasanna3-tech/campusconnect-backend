package com.campusconnectapp.repositories;

import com.campusconnectapp.models.PostImage;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PostImageRepository extends JpaRepository<PostImage, Long> {
    void deleteByPostIdIn(List<Long> postIds);
}
