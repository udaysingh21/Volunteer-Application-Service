package com.volunteer.service.dto;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DTO for volunteer response data.
 */
public class VolunteerResponseDTO {

    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String location;
    private Double latitude;
    private Double longitude;
    private List<String> skills;
    private List<String> interests;
    private AvailabilityDTO availability;
    private List<String> drivesApplied;
    private List<String> drivesCompleted;
    private Boolean isActive;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

    // Constructors
    public VolunteerResponseDTO() {
        // Default constructor for serialization
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Double getLatitude() {
        return latitude;
    }

    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    public Double getLongitude() {
        return longitude;
    }

    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    public List<String> getSkills() {
        return skills;
    }

    public void setSkills(List<String> skills) {
        this.skills = skills;
    }

    public List<String> getInterests() {
        return interests;
    }

    public void setInterests(List<String> interests) {
        this.interests = interests;
    }

    public AvailabilityDTO getAvailability() {
        return availability;
    }

    public void setAvailability(AvailabilityDTO availability) {
        this.availability = availability;
    }

    public List<String> getDrivesApplied() {
        return drivesApplied;
    }

    public void setDrivesApplied(List<String> drivesApplied) {
        this.drivesApplied = drivesApplied;
    }

    public List<String> getDrivesCompleted() {
        return drivesCompleted;
    }

    public void setDrivesCompleted(List<String> drivesCompleted) {
        this.drivesCompleted = drivesCompleted;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    /**
     * Simple availability DTO for nested data.
     */
    public static class AvailabilityDTO {
        private List<String> weekdays; // e.g., ["MONDAY", "TUESDAY"]
        private boolean weekends; // true if available on weekends

        public AvailabilityDTO() {
            // Default constructor for serialization
        }

        public List<String> getWeekdays() {
            return weekdays;
        }

        public void setWeekdays(List<String> weekdays) {
            this.weekdays = weekdays;
        }

        public boolean isWeekends() {
            return weekends;
        }

        public void setWeekends(boolean weekends) {
            this.weekends = weekends;
        }
    }
}