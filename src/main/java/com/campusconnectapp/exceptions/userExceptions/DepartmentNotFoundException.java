package com.campusconnectapp.exceptions.userExceptions;

public class DepartmentNotFoundException
        extends RuntimeException {

    public DepartmentNotFoundException(Long departmentId) {
        super(
                "Department not found: "
                        + departmentId
        );
    }
}
