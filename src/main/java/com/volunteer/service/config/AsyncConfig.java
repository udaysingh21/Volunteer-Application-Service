package com.volunteer.service.config;

import java.util.concurrent.Executor;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

/**
 * Asynchronous processing configuration.
 * Configures thread pool for async operations like event publishing.
 */
@Configuration
public class AsyncConfig implements AsyncConfigurer {

    /**
     * Custom task executor for async operations.
     * Optimized for event publishing and other background tasks.
     */
    @Bean(name = "taskExecutor")
    @Override
    public Executor getAsyncExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        
        // Core number of threads
        executor.setCorePoolSize(2);
        
        // Maximum number of threads
        executor.setMaxPoolSize(10);
        
        // Queue capacity
        executor.setQueueCapacity(25);
        
        // Thread name prefix
        executor.setThreadNamePrefix("VolunteerService-Async-");
        
        // Graceful shutdown
        executor.setWaitForTasksToCompleteOnShutdown(true);
        executor.setAwaitTerminationSeconds(60);
        
        executor.initialize();
        return executor;
    }
}