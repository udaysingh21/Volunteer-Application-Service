package com.volunteer.service.dto;

import java.util.List;

import jakarta.validation.constraints.Size;

/**
 * DTO for updating volunteer information.
 */
public class VolunteerUpdateDTO {

    @Size(max = 100)
    private String name;

    @Size(max = 20)
    private String phoneNumber;

    @Size(max = 255)
    private String location;

    private Double latitude;

    private Double longitude;

    private List<String> skills;

    private List<String> interests;

    private AvailabilityUpdateDTO availability;

    private Boolean isActive;

    // Constructors
    public VolunteerUpdateDTO() {
        // Default constructor for serialization
    }

    // Getters and Setters
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public AvailabilityUpdateDTO getAvailability() {
        return availability;
    }

    public void setAvailability(AvailabilityUpdateDTO availability) {
        this.availability = availability;
    }

    public Boolean getIsActive() {
        return isActive;
    }

    public void setIsActive(Boolean isActive) {
        this.isActive = isActive;
    }

    /**
     * Nested DTO for availability updates.
     */
    public static class AvailabilityUpdateDTO {
        private List<String> weekdays; // e.g., ["MONDAY", "TUESDAY"]
        private Boolean weekends; // true if available on weekends

        public AvailabilityUpdateDTO() {
            // Default constructor for serialization
        }

        public List<String> getWeekdays() {
            return weekdays;
        }

        public void setWeekdays(List<String> weekdays) {
            this.weekdays = weekdays;
        }

        public Boolean getWeekends() {
            return weekends;
        }

        public void setWeekends(Boolean weekends) {
            this.weekends = weekends;
        }
    }
}