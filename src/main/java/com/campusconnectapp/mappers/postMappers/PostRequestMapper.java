package com.campusconnectapp.mappers.postMappers;
import com.campusconnectapp.dtos.postDtos.CreatePostRequestDto;
import com.campusconnectapp.exceptions.postExceptions.CategoryNotFoundException;
import com.campusconnectapp.exceptions.postExceptions.LocationNotFoundException;
import com.campusconnectapp.models.Category;
import com.campusconnectapp.models.Location;
import com.campusconnectapp.models.LostFoundPost;
import com.campusconnectapp.repositories.CategoryRepository;
import com.campusconnectapp.repositories.LocationRepository;
import org.springframework.stereotype.Component;

@Component
public class PostRequestMapper {

    private final CategoryRepository categoryRepository;
    private final LocationRepository locationRepository;

    public PostRequestMapper(
            CategoryRepository categoryRepository,
            LocationRepository locationRepository
    ) {
        this.categoryRepository = categoryRepository;
        this.locationRepository = locationRepository;
    }

    public LostFoundPost toEntity(CreatePostRequestDto dto) {

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElseThrow(() ->
                        new CategoryNotFoundException(dto.getCategoryId())
                );

        Location location = locationRepository.findById(dto.getLocationId())
                .orElseThrow(() ->
                        new LocationNotFoundException(dto.getLocationId())
                );

        LostFoundPost post = new LostFoundPost();

        post.setTitle(dto.getTitle());
        post.setDescription(dto.getDescription());

        if (dto.getPostType() == 1L) {
            post.setPostType("LOST");
        } else if (dto.getPostType() == 2L) {
            post.setPostType("FOUND");
        }

        post.setCategory(category);
        post.setLocation(location);
        post.setLocationDetail(dto.getLocationDetail());
        post.setDateOccurred(dto.getDateOccurred());

        return post;
    }
}
