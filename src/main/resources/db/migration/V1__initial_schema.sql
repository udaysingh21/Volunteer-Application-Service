-- Simplified schema for Volunteer Service
-- Single table containing all volunteer data

-- Create volunteers table with all necessary fields
CREATE TABLE IF NOT EXISTS volunteers (
    id BIGSERIAL PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    phone_number VARCHAR(20),
    location VARCHAR(255),
    latitude DECIMAL(10, 8),
    longitude DECIMAL(11, 8),
    skills TEXT, -- JSON array of skills
    interests TEXT, -- JSON array of interests
    availability TEXT, -- JSON object for availability (days of week, weekends)
    drives_applied TEXT, -- JSON array of drive IDs applied for
    drives_completed TEXT, -- JSON array of drive IDs completed
    is_active BOOLEAN NOT NULL DEFAULT true,
    created_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP
);

-- Create indexes for better performance
CREATE INDEX IF NOT EXISTS idx_volunteer_email ON volunteers(email);
CREATE INDEX IF NOT EXISTS idx_volunteer_location ON volunteers(latitude, longitude);
CREATE INDEX IF NOT EXISTS idx_volunteer_active ON volunteers(is_active);
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