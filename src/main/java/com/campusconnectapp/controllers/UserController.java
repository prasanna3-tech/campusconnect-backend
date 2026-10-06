package com.campusconnectapp.controllers;

import com.campusconnectapp.dtos.userDtos.*;
import com.campusconnectapp.mappers.userMappers.UserRequestMapper;
import com.campusconnectapp.models.User;
import com.campusconnectapp.security.CustomUserDetails;
import com.campusconnectapp.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import org.springframework.web.bind.annotation.GetMapping;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;
    private final UserRequestMapper userRequestMapper;

    public UserController(
            UserService userService,
            UserRequestMapper userRequestMapper) {

        this.userService = userService;
        this.userRequestMapper = userRequestMapper;
    }

    @PostMapping
    public ResponseEntity<String> createUser(
            @Valid @RequestBody CreateUserRequestDto requestDto) {

        User user = userRequestMapper.toUser(requestDto);

        userService.createUser(user, requestDto.getDepartmentId());

        return ResponseEntity
                .status(HttpStatus.CREATED)
                .body("User created successfully");
    }
    @GetMapping
    public ResponseEntity<List<GetUserResponseDto>> getAllUsers() {

        return ResponseEntity.ok(userService.getAllUsers());
    }
    @GetMapping("/search")
    public ResponseEntity<List<GetUserResponseDto>> getUsersByName(
            @RequestParam
            @NotBlank(message = "Name must not be blank")
            String name) {

        return ResponseEntity.ok(userService.getUsersByName(name));
    }
    @PatchMapping("/{userId}/deactivate")
    public ResponseEntity<String> deactivateUser(
            @PathVariable Long userId) {

        userService.deactivateUser(userId);

        return ResponseEntity.ok("User deactivated successfully");
    }
    @PatchMapping("/{userId}/activate")
    public ResponseEntity<String> activateUser(
            @PathVariable Long userId) {

        userService.activateUser(userId);

        return ResponseEntity.ok("User activated successfully");
    }
    @GetMapping("/email")
    public ResponseEntity<GetUserResponseDto> getUserByEmail(
            @RequestParam
            @NotBlank(message = "Email must not be blank")
            @Email(message = "Please provide a valid email address")
            String email) {

        return ResponseEntity.ok(userService.getUserByEmail(email));
    }
    @PutMapping("/me")
    public ResponseEntity<UpdateUserResponseDto> updateUser(
            @Valid @RequestBody UpdateUserRequestDto request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        UpdateUserResponseDto response =
                userService.updateUser(userDetails.getUserId(), request);

        return ResponseEntity.ok(response);
    }
    @PatchMapping("me/change-password")
    public ResponseEntity<String> changePassword(
            @Valid @RequestBody ChangePasswordRequestDto request,
            Authentication authentication) {

        CustomUserDetails userDetails =
                (CustomUserDetails) authentication.getPrincipal();

        userService.changePassword(
                userDetails.getUserId(),
                request
        );

        return ResponseEntity.ok("Password updated successfully");
    }
    @DeleteMapping("/{userId}")
    public ResponseEntity<String> deleteUser(@PathVariable Long userId) {

        userService.deleteUser(userId);

        return ResponseEntity.ok("User deleted successfully");
    }
}