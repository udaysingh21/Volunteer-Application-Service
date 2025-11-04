package com.volunteer.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.volunteer.service.dto.ApiResponse;
import com.volunteer.service.dto.VolunteerRequestDTO;
import com.volunteer.service.dto.VolunteerResponseDTO;
import com.volunteer.service.dto.VolunteerUpdateDTO;
import com.volunteer.service.service.VolunteerService;

import jakarta.validation.Valid;

/**
 * REST Controller for volunteer management.
 * Provides endpoints for CRUD operations on volunteers.
 */
@RestController
@RequestMapping("/api/v1/volunteers")
@CrossOrigin(origins = {"http://localhost:3000", "http://localhost:4200"})
public class VolunteerController {

    private final VolunteerService volunteerService;

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    /**
     * Create a new volunteer.
     */
    @PostMapping
    public ResponseEntity<ApiResponse<VolunteerResponseDTO>> createVolunteer(
            @Valid @RequestBody VolunteerRequestDTO requestDTO) {
        VolunteerResponseDTO volunteer = volunteerService.createVolunteer(requestDTO);
        ApiResponse<VolunteerResponseDTO> response = ApiResponse.success("Volunteer created successfully", volunteer);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    /**
     * Get volunteer by ID.
     */
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<VolunteerResponseDTO>> getVolunteerById(@PathVariable Long id) {
        VolunteerResponseDTO volunteer = volunteerService.getVolunteerById(id);
        ApiResponse<VolunteerResponseDTO> response = ApiResponse.success(volunteer);
        return ResponseEntity.ok(response);
    }

    /**
     * Get volunteer by email.
     */
    @GetMapping("/email/{email}")
    public ResponseEntity<ApiResponse<VolunteerResponseDTO>> getVolunteerByEmail(@PathVariable String email) {
        VolunteerResponseDTO volunteer = volunteerService.getVolunteerByEmail(email);
        ApiResponse<VolunteerResponseDTO> response = ApiResponse.success(volunteer);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all volunteers with pagination.
     */
    @GetMapping
    public ResponseEntity<ApiResponse<Page<VolunteerResponseDTO>>> getAllVolunteers(
            @RequestParam(required = false) Boolean active,
            Pageable pageable) {
        Page<VolunteerResponseDTO> volunteers = volunteerService.getVolunteers(active, pageable);
        ApiResponse<Page<VolunteerResponseDTO>> response = ApiResponse.success(volunteers);
        return ResponseEntity.ok(response);
    }

    /**
     * Get all active volunteers.
     */
    @GetMapping("/active")
    public ResponseEntity<ApiResponse<List<VolunteerResponseDTO>>> getActiveVolunteers() {
        List<VolunteerResponseDTO> volunteers = volunteerService.getAllActiveVolunteers();
        ApiResponse<List<VolunteerResponseDTO>> response = ApiResponse.success(volunteers);
        return ResponseEntity.ok(response);
    }

    /**
     * Search volunteers by name.
     */
    @GetMapping("/search")
    public ResponseEntity<ApiResponse<List<VolunteerResponseDTO>>> searchVolunteers(
            @RequestParam String q) {
        List<VolunteerResponseDTO> volunteers = volunteerService.searchVolunteersByName(q);
        ApiResponse<List<VolunteerResponseDTO>> response = ApiResponse.success(volunteers);
        return ResponseEntity.ok(response);
    }

    /**
     * Find volunteers nearby.
     */
    @GetMapping("/nearby")
    public ResponseEntity<ApiResponse<List<VolunteerResponseDTO>>> findVolunteersNearby(
            @RequestParam Double latitude,
            @RequestParam Double longitude,
            @RequestParam(defaultValue = "50.0") Double radius) {
        List<VolunteerResponseDTO> volunteers = volunteerService.findVolunteersNearby(latitude, longitude, radius);
        ApiResponse<List<VolunteerResponseDTO>> response = ApiResponse.success(volunteers);
        return ResponseEntity.ok(response);
    }

    /**
     * Update volunteer information.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VolunteerResponseDTO>> updateVolunteer(
            @PathVariable Long id,
            @Valid @RequestBody VolunteerUpdateDTO updateDTO) {
        VolunteerResponseDTO volunteer = volunteerService.updateVolunteer(id, updateDTO);
        ApiResponse<VolunteerResponseDTO> response = ApiResponse.success("Volunteer updated successfully", volunteer);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete volunteer (soft delete).
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteVolunteer(@PathVariable Long id) {
        volunteerService.deleteVolunteer(id);
        ApiResponse<Object> response = ApiResponse.success("Volunteer deleted successfully");
        return ResponseEntity.ok(response);
    }
}