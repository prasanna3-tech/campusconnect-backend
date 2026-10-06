package com.campusconnectapp.utils;

import com.campusconnectapp.exceptions.userExceptions.InvalidUserEmailException;

public class UserRoleUtil {

    public static String determineRole(String email) {

        String localPart = email.substring(0, email.indexOf('@'));

        if (localPart.matches("\\d{2}[a-zA-Z]+\\d+")) {
            return "STUDENT";
        }

        if (localPart.matches("[a-zA-Z]+\\.[a-zA-Z]+")) {
            return "STAFF";
        }

        throw new InvalidUserEmailException("Invalid email format for role determination");
    }
}
