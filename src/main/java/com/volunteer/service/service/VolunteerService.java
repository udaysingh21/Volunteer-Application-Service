package com.volunteer.service.service;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.volunteer.service.dto.VolunteerRequestDTO;
import com.volunteer.service.dto.VolunteerResponseDTO;
import com.volunteer.service.dto.VolunteerUpdateDTO;
import com.volunteer.service.exception.ResourceNotFoundException;
import com.volunteer.service.model.Volunteer;
import com.volunteer.service.repository.VolunteerRepository;

/**
 * Service class for managing volunteers.
 * Provides business logic for volunteer operations with caching support.
 */
@Service
@Transactional
public class VolunteerService {

    private final VolunteerRepository volunteerRepository;

    @Autowired
    public VolunteerService(VolunteerRepository volunteerRepository) {
        this.volunteerRepository = volunteerRepository;
    }

    /**
     * Create a new volunteer.
     */
    @CacheEvict(value = "volunteers", allEntries = true)
    public VolunteerResponseDTO createVolunteer(VolunteerRequestDTO requestDTO) {
        // Check if email already exists
        if (volunteerRepository.findByEmail(requestDTO.getEmail()).isPresent()) {
            throw new IllegalArgumentException("Email already exists: " + requestDTO.getEmail());
        }

        Volunteer volunteer = convertToEntity(requestDTO);
        Volunteer savedVolunteer = volunteerRepository.save(volunteer);
        return convertToResponseDTO(savedVolunteer);
    }

    /**
     * Get volunteer by ID.
     */
    @Cacheable(value = "volunteers", key = "#id")
    @Transactional(readOnly = true)
    public VolunteerResponseDTO getVolunteerById(Long id) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.volunteer(id));
        return convertToResponseDTO(volunteer);
    }

    /**
     * Get volunteer by email.
     */
    @Cacheable(value = "volunteers", key = "#email")
    @Transactional(readOnly = true)
    public VolunteerResponseDTO getVolunteerByEmail(String email) {
        Volunteer volunteer = volunteerRepository.findByEmail(email)
                .orElseThrow(() -> ResourceNotFoundException.volunteerByEmail(email));
        return convertToResponseDTO(volunteer);
    }

    /**
     * Get all active volunteers.
     */
    @Cacheable(value = "volunteers", key = "'active'")
    @Transactional(readOnly = true)
    public List<VolunteerResponseDTO> getAllActiveVolunteers() {
        return volunteerRepository.findByIsActiveTrue()
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Get volunteers with pagination.
     */
    @Transactional(readOnly = true)
    public Page<VolunteerResponseDTO> getVolunteers(Boolean isActive, Pageable pageable) {
        if (isActive != null) {
            return volunteerRepository.findByIsActive(isActive, pageable)
                    .map(this::convertToResponseDTO);
        }
        return volunteerRepository.findAll(pageable)
                .map(this::convertToResponseDTO);
    }

    /**
     * Update volunteer information.
     */
    @CacheEvict(value = "volunteers", key = "#id")
    public VolunteerResponseDTO updateVolunteer(Long id, VolunteerUpdateDTO updateDTO) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.volunteer(id));

        updateVolunteerFromDTO(volunteer, updateDTO);
        Volunteer updatedVolunteer = volunteerRepository.save(volunteer);
        return convertToResponseDTO(updatedVolunteer);
    }

    /**
     * Delete volunteer (soft delete).
     */
    @CacheEvict(value = "volunteers", key = "#id")
    public void deleteVolunteer(Long id) {
        Volunteer volunteer = volunteerRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.volunteer(id));
        volunteer.setIsActive(false);
        volunteerRepository.save(volunteer);
    }

    /**
     * Search volunteers by name.
     */
    @Transactional(readOnly = true)
    public List<VolunteerResponseDTO> searchVolunteersByName(String searchTerm) {
        return volunteerRepository.findByNameContaining(searchTerm)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    /**
     * Find volunteers within geographical radius.
     */
    @Transactional(readOnly = true)
    public List<VolunteerResponseDTO> findVolunteersNearby(Double latitude, Double longitude, Double radiusKm) {
        return volunteerRepository.findVolunteersWithinRadius(latitude, longitude, radiusKm)
                .stream()
                .map(this::convertToResponseDTO)
                .collect(Collectors.toList());
    }

    // Helper methods for conversion

    private Volunteer convertToEntity(VolunteerRequestDTO dto) {
        Volunteer volunteer = new Volunteer();
        volunteer.setFirstName(dto.getFirstName());
        volunteer.setLastName(dto.getLastName());
        volunteer.setEmail(dto.getEmail());
        volunteer.setPhoneNumber(dto.getPhoneNumber());
        volunteer.setAddress(dto.getAddress());
        volunteer.setLatitude(dto.getLatitude());
        volunteer.setLongitude(dto.getLongitude());
        volunteer.setBio(dto.getBio());
        volunteer.setIsActive(true);
        return volunteer;
    }

    private VolunteerResponseDTO convertToResponseDTO(Volunteer volunteer) {
        VolunteerResponseDTO dto = new VolunteerResponseDTO();
        dto.setId(volunteer.getId());
        dto.setFirstName(volunteer.getFirstName());
        dto.setLastName(volunteer.getLastName());
        dto.setEmail(volunteer.getEmail());
        dto.setPhoneNumber(volunteer.getPhoneNumber());
        dto.setAddress(volunteer.getAddress());
        dto.setLatitude(volunteer.getLatitude());
        dto.setLongitude(volunteer.getLongitude());
        dto.setBio(volunteer.getBio());
        dto.setIsActive(volunteer.getIsActive());
        dto.setCreatedAt(volunteer.getCreatedAt());
        dto.setUpdatedAt(volunteer.getUpdatedAt());
        return dto;
    }

    private void updateVolunteerFromDTO(Volunteer volunteer, VolunteerUpdateDTO dto) {
        if (dto.getFirstName() != null) {
            volunteer.setFirstName(dto.getFirstName());
        }
        if (dto.getLastName() != null) {
            volunteer.setLastName(dto.getLastName());
        }
        if (dto.getPhoneNumber() != null) {
            volunteer.setPhoneNumber(dto.getPhoneNumber());
        }
        if (dto.getAddress() != null) {
            volunteer.setAddress(dto.getAddress());
        }
        if (dto.getLatitude() != null) {
            volunteer.setLatitude(dto.getLatitude());
        }
        if (dto.getLongitude() != null) {
            volunteer.setLongitude(dto.getLongitude());
        }
        if (dto.getBio() != null) {
            volunteer.setBio(dto.getBio());
        }
        if (dto.getIsActive() != null) {
            volunteer.setIsActive(dto.getIsActive());
        }
    }
}