package com.volunteer.service.repository;

import com.volunteer.service.model.Skill;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * Repository interface for Skill entity.
 * Provides custom query methods for skill data access.
 */
@Repository
public interface SkillRepository extends JpaRepository<Skill, Long> {

    /**
     * Find skill by name (case-insensitive).
     */
    Optional<Skill> findByNameIgnoreCase(String name);

    /**
     * Find all active skills.
     */
    List<Skill> findByIsActiveTrueOrderByName();

    /**
     * Find skills by category (case-insensitive).
     */
    List<Skill> findByCategoryIgnoreCaseAndIsActiveTrue(String category);

    /**
     * Find skills by category with pagination.
     */
    Page<Skill> findByCategoryIgnoreCaseAndIsActive(String category, Boolean isActive, Pageable pageable);

    /**
     * Find skills by name containing search term (case-insensitive).
     */
    @Query("SELECT s FROM Skill s WHERE " +
           "LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) " +
           "AND s.isActive = true " +
           "ORDER BY s.name")
    List<Skill> findByNameContaining(@Param("searchTerm") String searchTerm);

    /**
     * Find skills by name or description containing search term.
     */
    @Query("SELECT s FROM Skill s WHERE " +
           "(LOWER(s.name) LIKE LOWER(CONCAT('%', :searchTerm, '%')) OR " +
           "LOWER(s.description) LIKE LOWER(CONCAT('%', :searchTerm, '%'))) " +
           "AND s.isActive = true " +
           "ORDER BY s.name")
    List<Skill> findByNameOrDescriptionContaining(@Param("searchTerm") String searchTerm);

    /**
     * Find all distinct categories of active skills.
     */
    @Query("SELECT DISTINCT s.category FROM Skill s WHERE s.isActive = true AND s.category IS NOT NULL ORDER BY s.category")
    List<String> findDistinctCategories();

    /**
     * Count skills by category.
     */
    long countByCategoryIgnoreCaseAndIsActiveTrue(String category);

    /**
     * Count active skills.
     */
    long countByIsActiveTrue();

    /**
     * Check if skill name exists (excluding specific skill ID).
     */
    boolean existsByNameIgnoreCaseAndIdNot(String name, Long id);

    /**
     * Find most popular skills (skills with the most volunteers).
     */
    @Query("SELECT s FROM Skill s " +
           "JOIN s.volunteerSkills vs " +
           "WHERE s.isActive = true " +
           "GROUP BY s " +
           "ORDER BY COUNT(vs) DESC")
    List<Skill> findMostPopularSkills(Pageable pageable);

    /**
     * Find skills that have volunteers with minimum proficiency level.
     */
    @Query("SELECT DISTINCT s FROM Skill s " +
           "JOIN s.volunteerSkills vs " +
           "WHERE s.isActive = true " +
           "AND vs.proficiencyLevel >= :minProficiency " +
           "ORDER BY s.name")
    List<Skill> findSkillsWithMinProficiency(@Param("minProficiency") Integer minProficiency);
}