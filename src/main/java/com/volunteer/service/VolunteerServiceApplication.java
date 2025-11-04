package com.volunteer.service;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.scheduling.annotation.EnableAsync;

/**
 * Main application class for the Volunteer Service.
 * 
 * This Spring Boot application provides:
 * - Volunteer management with CRUD operations
 * - Skill management and volunteer-skill associations
 * - Redis caching for improved performance
 * - Asynchronous event publishing
 * - Geographical utilities for location-based operations
 * - RESTful API with OpenAPI documentation
 * 
 * @author Volunteer Service Team
 * @version 1.0
 */
@SpringBootApplication
@EnableCaching
@EnableAsync
public class VolunteerServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(VolunteerServiceApplication.class, args);
    }
}