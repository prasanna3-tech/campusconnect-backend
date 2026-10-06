package com.campusconnectapp.mappers.userMappers;

import com.campusconnectapp.dtos.userDtos.GetUserResponseDto;
import com.campusconnectapp.dtos.userDtos.UpdateUserResponseDto;
import com.campusconnectapp.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMapper {

    public GetUserResponseDto toGetUserResponseDto(User user) {

        return new GetUserResponseDto(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhone(),
                user.getRole().getName(),
                user.getDepartment().getName(),
                user.getIsActive()
        );
    }
    public UpdateUserResponseDto toUpdateUserResponseDto(User user) {

        UpdateUserResponseDto dto = new UpdateUserResponseDto();

        dto.setName(user.getName());
        dto.setPhone(user.getPhone());
        dto.setDepartment(user.getDepartment().getName());

        return dto;
    }
}