package com.campusconnectapp.exceptions.userExceptions;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(Long userId) {
        super(
                "User not found: "
                        + userId
        );
    }
    public UserNotFoundException(String email) {
        super("No user account found with email: " + email);
    }
}