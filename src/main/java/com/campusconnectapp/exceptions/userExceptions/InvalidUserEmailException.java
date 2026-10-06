package com.campusconnectapp.exceptions.userExceptions;

public class InvalidUserEmailException
        extends RuntimeException {

    public InvalidUserEmailException(String email) {
        super(
                "Invalid user email: "
                        + email
        );
    }
}