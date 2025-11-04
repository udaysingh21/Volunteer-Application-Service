package com.volunteer.service.repository;

import com.volunteer.service.model.Volunteer;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Volunteer entity.
 * Provides custom query methods for volunteer data access.
 */
@Repository
public interface VolunteerRepository extends JpaRepository<Volunteer, Long> {

    /**
     * Find volunteer by email address.
     */
    Optional<Volunteer> findByEmail(String email);

    /**
     * Find all active volunteers.
     */
    List<Volunteer> findByIsActiveTrue();

    /**
     * Find volunteers by active status with pagination.
     */
    Page<Volunteer> findByIsActive(Boolean isActive, Pageable pageable);

    /**
     * Find volunteers by first name or last name containing search term (case-insensitive).
     */
    @Query("SELECT v FROM Volunteer v WHERE " +
           "LOWER(v.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(v.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    List<Volunteer> findByNameContaining(@Param("searchTerm") String searchTerm);

    /**
     * Find volunteers by first name or last name containing search term with pagination.
     */
    @Query("SELECT v FROM Volunteer v WHERE " +
           "LOWER(v.firstName) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(v.lastName) LIKE LOWER(CONCAT('%', :searchTerm, '%'))")
    Page<Volunteer> findByNameContaining(@Param("searchTerm") String searchTerm, Pageable pageable);

    /**
     * Find volunteers within a geographical radius.
     * Uses the Haversine formula to calculate distance.
     */
    @Query(value = "SELECT * FROM volunteers v WHERE " +
                   "(6371 * acos(cos(radians(:latitude)) * cos(radians(v.latitude)) * " +
                   "cos(radians(v.longitude) - radians(:longitude)) + " +
                   "sin(radians(:latitude)) * sin(radians(v.latitude)))) <= :radiusKm " +
                   "AND v.is_active = true " +
                   "AND v.latitude IS NOT NULL " +
                   "AND v.longitude IS NOT NULL",
           nativeQuery = true)
    List<Volunteer> findVolunteersWithinRadius(@Param("latitude") Double latitude,
                                               @Param("longitude") Double longitude,
                                               @Param("radiusKm") Double radiusKm);

    /**
     * Find volunteers by skill name.
     */
    @Query("SELECT DISTINCT v FROM Volunteer v " +
           "JOIN v.volunteerSkills vs " +
           "JOIN vs.skill s " +
           "WHERE LOWER(s.name) = LOWER(:skillName) " +
           "AND v.isActive = true")
    List<Volunteer> findBySkillName(@Param("skillName") String skillName);

    /**
     * Find volunteers by skill category.
     */
    @Query("SELECT DISTINCT v FROM Volunteer v " +
           "JOIN v.volunteerSkills vs " +
           "JOIN vs.skill s " +
           "WHERE LOWER(s.category) = LOWER(:category) " +
           "AND v.isActive = true")
    List<Volunteer> findBySkillCategory(@Param("category") String category);

    /**
     * Find volunteers by minimum proficiency level in a skill.
     */
    @Query("SELECT DISTINCT v FROM Volunteer v " +
           "JOIN v.volunteerSkills vs " +
           "JOIN vs.skill s " +
           "WHERE LOWER(s.name) = LOWER(:skillName) " +
           "AND vs.proficiencyLevel >= :minProficiency " +
           "AND v.isActive = true")
    List<Volunteer> findBySkillAndMinProficiency(@Param("skillName") String skillName,
                                                 @Param("minProficiency") Integer minProficiency);

    /**
     * Count active volunteers.
     */
    long countByIsActiveTrue();

    /**
     * Check if email exists (excluding specific volunteer ID).
     */
    boolean existsByEmailAndIdNot(String email, Long id);

    /**
     * Find volunteers who are currently available (for recurring schedules).
     */
    @Query("SELECT DISTINCT v FROM Volunteer v " +
           "JOIN v.availabilities a " +
           "WHERE a.isRecurring = true " +
           "AND a.dayOfWeek = :dayOfWeek " +
           "AND a.startTime <= :currentTime " +
           "AND a.endTime >= :currentTime " +
           "AND a.isActive = true " +
           "AND v.isActive = true")
    List<Volunteer> findCurrentlyAvailableVolunteers(@Param("dayOfWeek") java.time.DayOfWeek dayOfWeek,
                                                     @Param("currentTime") java.time.LocalTime currentTime);
}