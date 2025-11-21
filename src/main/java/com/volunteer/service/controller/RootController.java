package com.volunteer.service.controller;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import com.volunteer.service.dto.ApiResponse;

/**
 * Root controller for basic application information and health check.
 */
@RestController
public class RootController {

    /**
     * Root endpoint providing basic application information.
     */
    @GetMapping("/")
    public ResponseEntity<ApiResponse<Map<String, Object>>> root() {
        Map<String, Object> info = new HashMap<>();
        info.put("application", "Volunteer Management Service");
        info.put("version", "0.0.1-SNAPSHOT");
        info.put("timestamp", LocalDateTime.now());
        info.put("status", "running");
        info.put("apiDocumentation", "/api/v1/swagger-ui.html");
        info.put("baseApiUrl", "/api/v1");
        
        ApiResponse<Map<String, Object>> response = ApiResponse.success(
            "Welcome to Volunteer Management Service API", 
            info
        );
        return ResponseEntity.ok(response);
    }

    /**
     * Simple health check endpoint.
     */
    @GetMapping("/health")
    public ResponseEntity<ApiResponse<Map<String, String>>> health() {
        Map<String, String> health = new HashMap<>();
        health.put("status", "UP");
        health.put("timestamp", LocalDateTime.now().toString());
        
        ApiResponse<Map<String, String>> response = ApiResponse.success(health);
        return ResponseEntity.ok(response);
    }
}