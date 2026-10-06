package com.campusconnectapp.mappers.userMappers;

import com.campusconnectapp.dtos.userDtos.CreateUserRequestDto;
import com.campusconnectapp.models.User;
import org.springframework.stereotype.Component;

@Component
public class UserRequestMapper {

    public User toUser(CreateUserRequestDto requestDto) {

        User user = new User();

        user.setName(requestDto.getName());
        user.setEmail(requestDto.getEmail());
        user.setPassword(requestDto.getPassword());
        user.setPhone(requestDto.getPhone());

        return user;
    }
}
