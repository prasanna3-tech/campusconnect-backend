package com.campusconnectapp.exceptions.userExceptions;

public class IncorrectOldPasswordException extends RuntimeException {

    public IncorrectOldPasswordException() {
        super("Old password is incorrect");
    }
}