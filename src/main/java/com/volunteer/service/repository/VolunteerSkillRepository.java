package com.volunteer.service.repository;

import com.volunteer.service.model.VolunteerSkill;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for VolunteerSkill entity.
 * Provides custom query methods for volunteer-skill relationship data access.
 */
@Repository
public interface VolunteerSkillRepository extends JpaRepository<VolunteerSkill, Long> {

    /**
     * Find volunteer-skill relationship by volunteer ID and skill ID.
     */
    Optional<VolunteerSkill> findByVolunteerIdAndSkillId(Long volunteerId, Long skillId);

    /**
     * Find all skills for a specific volunteer.
     */
    List<VolunteerSkill> findByVolunteerId(Long volunteerId);

    /**
     * Find all volunteers for a specific skill.
     */
    List<VolunteerSkill> findBySkillId(Long skillId);

    /**
     * Find volunteer skills by minimum proficiency level.
     */
    List<VolunteerSkill> findByVolunteerIdAndProficiencyLevelGreaterThanEqual(Long volunteerId, Integer minProficiency);

    /**
     * Find volunteers with specific skill and minimum proficiency.
     */
    List<VolunteerSkill> findBySkillIdAndProficiencyLevelGreaterThanEqual(Long skillId, Integer minProficiency);

    /**
     * Find certified volunteer skills.
     */
    List<VolunteerSkill> findByVolunteerIdAndCertifiedTrue(Long volunteerId);

    /**
     * Find volunteer skills by experience years.
     */
    List<VolunteerSkill> findByVolunteerIdAndExperienceYearsGreaterThanEqual(Long volunteerId, Integer minExperience);

    /**
     * Count skills for a volunteer.
     */
    long countByVolunteerId(Long volunteerId);

    /**
     * Count volunteers for a skill.
     */
    long countBySkillId(Long skillId);

    /**
     * Delete volunteer skill relationship.
     */
    void deleteByVolunteerIdAndSkillId(Long volunteerId, Long skillId);

    /**
     * Check if volunteer has specific skill.
     */
    boolean existsByVolunteerIdAndSkillId(Long volunteerId, Long skillId);

    /**
     * Find top skilled volunteers for a specific skill.
     */
    @Query("SELECT vs FROM VolunteerSkill vs " +
           "WHERE vs.skill.id = :skillId " +
           "ORDER BY vs.proficiencyLevel DESC, vs.experienceYears DESC")
    List<VolunteerSkill> findTopSkilledVolunteers(@Param("skillId") Long skillId);

    /**
     * Find volunteer skills with average proficiency by skill category.
     */
    @Query("SELECT vs FROM VolunteerSkill vs " +
           "JOIN vs.skill s " +
           "WHERE vs.volunteer.id = :volunteerId " +
           "AND LOWER(s.category) = LOWER(:category) " +
           "ORDER BY vs.proficiencyLevel DESC")
    List<VolunteerSkill> findVolunteerSkillsByCategory(@Param("volunteerId") Long volunteerId, 
                                                       @Param("category") String category);

    /**
     * Get average proficiency level for a volunteer across all skills.
     */
    @Query("SELECT AVG(vs.proficiencyLevel) FROM VolunteerSkill vs WHERE vs.volunteer.id = :volunteerId")
    Double getAverageProficiencyForVolunteer(@Param("volunteerId") Long volunteerId);

    /**
     * Get average proficiency level for a specific skill across all volunteers.
     */
    @Query("SELECT AVG(vs.proficiencyLevel) FROM VolunteerSkill vs WHERE vs.skill.id = :skillId")
    Double getAverageProficiencyForSkill(@Param("skillId") Long skillId);
}