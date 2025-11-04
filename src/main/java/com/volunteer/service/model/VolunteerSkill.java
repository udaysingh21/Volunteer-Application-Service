package com.volunteer.service.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

/**
 * VolunteerSkill entity representing the relationship between volunteers and skills.
 * Includes proficiency level and experience years.
 */
@Entity
@Table(name = "volunteer_skills", 
       uniqueConstraints = @UniqueConstraint(columnNames = {"volunteer_id", "skill_id"}),
       indexes = {
           @Index(name = "idx_volunteer_skill_volunteer", columnList = "volunteer_id"),
           @Index(name = "idx_volunteer_skill_skill", columnList = "skill_id"),
           @Index(name = "idx_volunteer_skill_proficiency", columnList = "proficiency_level")
       })
public class VolunteerSkill {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "volunteer_id", nullable = false)
    @NotNull(message = "Volunteer is required")
    private Volunteer volunteer;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "skill_id", nullable = false)
    @NotNull(message = "Skill is required")
    private Skill skill;

    @Min(value = 1, message = "Proficiency level must be between 1 and 5")
    @Max(value = 5, message = "Proficiency level must be between 1 and 5")
    @Column(name = "proficiency_level", nullable = false)
    private Integer proficiencyLevel;

    @Min(value = 0, message = "Experience years cannot be negative")
    @Column(name = "experience_years")
    private Integer experienceYears;

    @Column(name = "certified")
    private Boolean certified = false;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }

    // Constructors
    public VolunteerSkill() {}

    public VolunteerSkill(Volunteer volunteer, Skill skill, Integer proficiencyLevel) {
        this.volunteer = volunteer;
        this.skill = skill;
        this.proficiencyLevel = proficiencyLevel;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Volunteer getVolunteer() {
        return volunteer;
    }

    public void setVolunteer(Volunteer volunteer) {
        this.volunteer = volunteer;
    }

    public Skill getSkill() {
        return skill;
    }

    public void setSkill(Skill skill) {
        this.skill = skill;
    }

    public Integer getProficiencyLevel() {
        return proficiencyLevel;
    }

    public void setProficiencyLevel(Integer proficiencyLevel) {
        this.proficiencyLevel = proficiencyLevel;
    }

    public Integer getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(Integer experienceYears) {
        this.experienceYears = experienceYears;
    }

    public Boolean getCertified() {
        return certified;
    }

    public void setCertified(Boolean certified) {
        this.certified = certified;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }

    // Helper methods
    public String getProficiencyDescription() {
        return switch (proficiencyLevel) {
            case 1 -> "Beginner";
            case 2 -> "Novice";
            case 3 -> "Intermediate";
            case 4 -> "Advanced";
            case 5 -> "Expert";
            default -> "Unknown";
        };
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        VolunteerSkill that = (VolunteerSkill) obj;
        return id != null && id.equals(that.id);
    }

    @Override
    public int hashCode() {
        return getClass().hashCode();
    }

    @Override
    public String toString() {
        return "VolunteerSkill{" +
                "id=" + id +
                ", volunteerId=" + (volunteer != null ? volunteer.getId() : null) +
                ", skillId=" + (skill != null ? skill.getId() : null) +
                ", proficiencyLevel=" + proficiencyLevel +
                ", experienceYears=" + experienceYears +
                ", certified=" + certified +
                '}';
    }
}