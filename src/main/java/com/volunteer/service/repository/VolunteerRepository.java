package com.volunteer.service.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.volunteer.service.model.Volunteer;

/**
 * Repository for Volunteer entities.
 */
@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    /**
     * Find volunteer by email.
     */
    Optional<Volunteer> findByEmail(String email);

    /**
     * Check if volunteer exists by email.
     */
    boolean existsByEmail(String email);
}