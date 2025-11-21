package com.volunteer.service.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.volunteer.service.dto.ApiResponse;
import com.volunteer.service.dto.VolunteerResponseDTO;
import com.volunteer.service.dto.VolunteerUpdateDTO;
import com.volunteer.service.service.VolunteerService;

import jakarta.validation.Valid;

/**
 * REST Controller for volunteer management.
 * Provides the 4 essential APIs for volunteer operations.
 */
@RestController
@RequestMapping("/api/v1/volunteers")
@CrossOrigin(origins = {"http://localhost:5174"})
public class VolunteerController {

    private final VolunteerService volunteerService;

    @Autowired
    public VolunteerController(VolunteerService volunteerService) {
        this.volunteerService = volunteerService;
    }

    /**
     * Update volunteer information including location, skills, and availability.
     */
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<VolunteerResponseDTO>> updateVolunteer(
            @PathVariable Long id,
            @Valid @RequestBody VolunteerUpdateDTO updateDTO) {
        VolunteerResponseDTO volunteer = volunteerService.updateVolunteer(id, updateDTO);
        ApiResponse<VolunteerResponseDTO> response = ApiResponse.success("Volunteer profile updated successfully", volunteer);
        return ResponseEntity.ok(response);
    }

    /**
     * Delete volunteer and all associated data from database.
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteVolunteer(@PathVariable Long id) {
        volunteerService.deleteVolunteer(id);
        ApiResponse<Object> response = ApiResponse.success("Volunteer deleted successfully");
        return ResponseEntity.ok(response);
    }

    /**
     * Get list of drives/postings the volunteer has completed.
     */
    @GetMapping("/{id}/drives/completed")
    public ResponseEntity<ApiResponse<List<String>>> getDrivesCompleted(@PathVariable Long id) {
        List<String> completedDrives = volunteerService.getDrivesCompleted(id);
        ApiResponse<List<String>> response = ApiResponse.success("Completed drives retrieved successfully", completedDrives);
        return ResponseEntity.ok(response);
    }

    /**
     * Get list of drives/postings the volunteer has applied for (scheduled).
     */
    @GetMapping("/{id}/drives/scheduled")
    public ResponseEntity<ApiResponse<List<String>>> getDrivesScheduled(@PathVariable Long id) {
        List<String> scheduledDrives = volunteerService.getDrivesScheduled(id);
        ApiResponse<List<String>> response = ApiResponse.success("Scheduled drives retrieved successfully", scheduledDrives);
        return ResponseEntity.ok(response);
    }
}