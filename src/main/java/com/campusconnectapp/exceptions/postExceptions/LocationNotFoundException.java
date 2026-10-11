package com.campusconnectapp.exceptions.postExceptions;
public class LocationNotFoundException extends RuntimeException {

    public LocationNotFoundException(Long locationId) {
        super("Location not found with id: " + locationId);
    }
}
