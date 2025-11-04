package com.volunteer.service.exception;

/**
 * Exception thrown when a requested resource is not found.
 */
public class ResourceNotFoundException extends RuntimeException {

    public ResourceNotFoundException(String message) {
        super(message);
    }

    public ResourceNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }

    public static ResourceNotFoundException volunteer(Long id) {
        return new ResourceNotFoundException("Volunteer not found with id: " + id);
    }

    public static ResourceNotFoundException skill(Long id) {
        return new ResourceNotFoundException("Skill not found with id: " + id);
    }

    public static ResourceNotFoundException volunteerByEmail(String email) {
        return new ResourceNotFoundException("Volunteer not found with email: " + email);
    }
}