package com.volunteer.service.config;

import org.springframework.context.annotation.Configuration;

/**
 * OpenAPI configuration for Swagger documentation.
 * Uses default SpringDoc configuration with application properties.
 */
@Configuration
public class OpenApiConfig {
    
    // SpringDoc will automatically configure OpenAPI based on application.yml
    // and generate documentation from controllers and annotations
}