package com.volunteer.service.service;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.volunteer.service.dto.VolunteerResponseDTO;
import com.volunteer.service.dto.VolunteerResponseDTO.AvailabilityDTO;
import com.volunteer.service.dto.VolunteerUpdateDTO;
import com.volunteer.service.exception.ResourceNotFoundException;
import com.volunteer.service.model.Volunteer;
import com.volunteer.service.repository.VolunteerRepository;

/**
 * Service class for managing volunteer operations.
 * Provides business logic for the 4 essential volunteer APIs.
 */
@Service
@Transactional
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
        this.objectMapper = new ObjectMapper();
    }

    /**
     * Update volunteer information including location, skills, and availability.
     */
    @CacheEvict(value = "volunteers", key = "#id")
    public VolunteerResponseDTO updateVolunteer(Long id, VolunteerUpdateDTO updateDTO) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.volunteer(id));

        updateVolunteerFields(volunteer, updateDTO);
        volunteer.setUpdatedAt(LocalDateTime.now());
        
        Volunteer savedVolunteer = volunteerRepository.save(volunteer);
        return convertToResponseDTO(savedVolunteer);
    }

    /**
     * Delete volunteer and all associated data from database.
     */
    @CacheEvict(value = "volunteers", key = "#id")
    public void deleteVolunteer(Long id) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.volunteer(id));
        
        volunteerRepository.delete(volunteer); // Hard delete
    }

    /**
     * Get list of drives/postings the volunteer has completed.
     */
    @Cacheable(value = "drives", key = "'completed:' + #id")
    @Transactional(readOnly = true)
    public List<String> getDrivesCompleted(Long id) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.volunteer(id));
        
        return parseJsonToStringList(volunteer.getDrivesCompleted());
    }

    /**
     * Get list of drives/postings the volunteer has applied for (scheduled).
     */
    @Cacheable(value = "drives", key = "'scheduled:' + #id")
    @Transactional(readOnly = true)
    public List<String> getDrivesScheduled(Long id) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.volunteer(id));
        
        return parseJsonToStringList(volunteer.getDrivesApplied());
    }

    /**
     * Update volunteer fields from DTO.
     */
    private void updateVolunteerFields(Volunteer volunteer, VolunteerUpdateDTO updateDTO) {
        if (updateDTO.getName() != null) {
            volunteer.setName(updateDTO.getName());
        }
        if (updateDTO.getPhoneNumber() != null) {
            volunteer.setPhoneNumber(updateDTO.getPhoneNumber());
        }
        if (updateDTO.getLocation() != null) {
            volunteer.setLocation(updateDTO.getLocation());
        }
        if (updateDTO.getLatitude() != null) {
            volunteer.setLatitude(updateDTO.getLatitude());
        }
        if (updateDTO.getLongitude() != null) {
            volunteer.setLongitude(updateDTO.getLongitude());
        }
        if (updateDTO.getSkills() != null) {
            volunteer.setSkills(convertListToJson(updateDTO.getSkills()));
        }
        if (updateDTO.getInterests() != null) {
            volunteer.setInterests(convertListToJson(updateDTO.getInterests()));
        }
        if (updateDTO.getAvailability() != null) {
            volunteer.setAvailability(convertAvailabilityToJson(updateDTO.getAvailability()));
        }
        if (updateDTO.getIsActive() != null) {
            volunteer.setIsActive(updateDTO.getIsActive());
        }
    }

    /**
     * Convert Volunteer entity to VolunteerResponseDTO.
     */
    private VolunteerResponseDTO convertToResponseDTO(Volunteer volunteer) {
        VolunteerResponseDTO dto = new VolunteerResponseDTO();
        dto.setId(volunteer.getId());
        dto.setName(volunteer.getName());
        dto.setEmail(volunteer.getEmail());
        dto.setPhoneNumber(volunteer.getPhoneNumber());
        dto.setLocation(volunteer.getLocation());
        dto.setLatitude(volunteer.getLatitude());
        dto.setLongitude(volunteer.getLongitude());
        dto.setSkills(parseJsonToStringList(volunteer.getSkills()));
        dto.setInterests(parseJsonToStringList(volunteer.getInterests()));
        dto.setAvailability(parseJsonToAvailability(volunteer.getAvailability()));
        dto.setDrivesApplied(parseJsonToStringList(volunteer.getDrivesApplied()));
        dto.setDrivesCompleted(parseJsonToStringList(volunteer.getDrivesCompleted()));
        dto.setIsActive(volunteer.getIsActive());
        dto.setCreatedAt(volunteer.getCreatedAt());
        dto.setUpdatedAt(volunteer.getUpdatedAt());
        return dto;
    }

    /**
     * Parse JSON string to List<String>.
     */
    private List<String> parseJsonToStringList(String json) {
        if (json == null || json.trim().isEmpty()) {
            return new ArrayList<>();
        }
        try {
            return objectMapper.readValue(json, new TypeReference<List<String>>() {});
        } catch (Exception e) {
            return new ArrayList<>();
        }
    }

    /**
     * Convert List<String> to JSON string.
     */
    private String convertListToJson(List<String> list) {
        if (list == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(list);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Parse JSON string to AvailabilityDTO.
     */
    private AvailabilityDTO parseJsonToAvailability(String json) {
        if (json == null || json.trim().isEmpty()) {
            return null;
        }
        try {
            return objectMapper.readValue(json, AvailabilityDTO.class);
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * Convert AvailabilityUpdateDTO to JSON string.
     */
    private String convertAvailabilityToJson(VolunteerUpdateDTO.AvailabilityUpdateDTO availability) {
        if (availability == null) {
            return null;
        }
        try {
            return objectMapper.writeValueAsString(availability);
        } catch (Exception e) {
            return null;
        }
    }
}