-- Initial schema for Volunteer Service
-- Creates tables for volunteers, skills, volunteer_skills, and availability

-- Create volunteers table
CREATE TABLE IF NOT EXISTS volunteers (
    id BIGSERIAL PRIMARY KEY,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    address VARCHAR(255),
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    bio VARCHAR(500),
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create skills table
CREATE TABLE IF NOT EXISTS skills (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL UNIQUE,
    description VARCHAR(500),
    category VARCHAR(50),
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create volunteer_skills table (many-to-many relationship)
CREATE TABLE IF NOT EXISTS volunteer_skills (
    id BIGSERIAL PRIMARY KEY,
    volunteer_id BIGINT NOT NULL,
    skill_id BIGINT NOT NULL,
    proficiency_level INTEGER NOT NULL CHECK (proficiency_level >= 1 AND proficiency_level <= 5),
    experience_years INTEGER CHECK (experience_years >= 0),
    certified BOOLEAN DEFAULT false,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    UNIQUE(volunteer_id, skill_id),
    FOREIGN KEY (volunteer_id) REFERENCES volunteers(id) ON DELETE CASCADE,
    FOREIGN KEY (skill_id) REFERENCES skills(id) ON DELETE CASCADE
);

-- Create availability table
CREATE TABLE IF NOT EXISTS availability (
    id BIGSERIAL PRIMARY KEY,
    volunteer_id BIGINT NOT NULL,
    day_of_week VARCHAR(10),
    start_time TIME,
    end_time TIME,
    start_date TIMESTAMP,
    end_date TIMESTAMP,
    is_recurring BOOLEAN NOT NULL DEFAULT false,
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (volunteer_id) REFERENCES volunteers(id) ON DELETE CASCADE
);

-- Create indexes for better query performance
CREATE INDEX IF NOT EXISTS idx_volunteer_email ON volunteers(email);
CREATE INDEX IF NOT EXISTS idx_volunteer_location ON volunteers(latitude, longitude);
CREATE INDEX IF NOT EXISTS idx_volunteer_active ON volunteers(is_active);

CREATE INDEX IF NOT EXISTS idx_skill_name ON skills(name);
CREATE INDEX IF NOT EXISTS idx_skill_category ON skills(category);
CREATE INDEX IF NOT EXISTS idx_skill_active ON skills(is_active);

CREATE INDEX IF NOT EXISTS idx_volunteer_skill_volunteer ON volunteer_skills(volunteer_id);
CREATE INDEX IF NOT EXISTS idx_volunteer_skill_skill ON volunteer_skills(skill_id);
CREATE INDEX IF NOT EXISTS idx_volunteer_skill_proficiency ON volunteer_skills(proficiency_level);

CREATE INDEX IF NOT EXISTS idx_availability_volunteer ON availability(volunteer_id);
CREATE INDEX IF NOT EXISTS idx_availability_day ON availability(day_of_week);
CREATE INDEX IF NOT EXISTS idx_availability_date_range ON availability(start_date, end_date);
CREATE INDEX IF NOT EXISTS idx_availability_active ON availability(is_active);