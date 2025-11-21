# Volunteer Profile Management Service

A comprehensive Spring Boot microservice for managing volunteer profiles, activities, and engagement tracking within the **Volunteer Resource Management System**.

## System Architecture Overview

This service is part of a **5-microservice ecosystem** for volunteer resource management:

| Service | Purpose | Port |
|---------|---------|------|
| **User Service** | User registration, authentication, and login | 8081 |
| **NGO Posting Service** | NGO profile management, posting creation, application tracking | 8082 |
| **Volunteer Service** *(This Service)* | Volunteer profile management and activity tracking | 8080 |
| **Matching Service** | Smart matching between volunteers and NGO opportunities | 8084 |
| **Analytics Service** | Admin dashboard, monitoring, and system analytics | 8085 |

## Service Purpose

The **Volunteer Service** serves as the central hub for volunteer-related operations:

1. **Profile Management** - Comprehensive volunteer profile updates
2. **Account Management** - Complete volunteer data deletion
3. **Activity Tracking** - Track completed volunteer drives/activities
4. **Engagement History** - Monitor scheduled and applied activities

## Database Schema

### Table: `volunteers`

| Column | Type | Description |
|--------|------|-------------|
| `id` | BIGINT (PK) | Unique volunteer identifier |
| `name` | VARCHAR(100) | Volunteer full name |
| `email` | VARCHAR(100) UNIQUE | Email address (links to User Service) |
| `phone_number` | VARCHAR(20) | Contact phone number |
| `location` | VARCHAR(255) | Geographic location/address |
| `latitude` | DOUBLE | GPS latitude for location-based matching |
| `longitude` | DOUBLE | GPS longitude for proximity calculations |
| `skills` | TEXT (JSON) | Array of volunteer skills and capabilities |
| `interests` | TEXT (JSON) | Areas of interest for volunteer work |
| `availability` | TEXT (JSON) | Time availability (weekdays, weekends) |
| `drives_applied` | TEXT (JSON) | Array of applied posting IDs |
| `drives_completed` | TEXT (JSON) | Array of completed activity IDs |
| `is_active` | BOOLEAN | Account active status |
| `created_at` | TIMESTAMP | Profile creation timestamp |
| `updated_at` | TIMESTAMP | Last profile update timestamp |

### JSON Field Structures

**Skills Example:**
```json
["Teaching", "Event Planning", "Medical Assistance", "IT Support"]
```

**Interests Example:**
```json
["Education", "Healthcare", "Environment", "Animal Welfare"]
```

**Availability Example:**
```json
{
  "weekdays": ["MONDAY", "WEDNESDAY", "FRIDAY"],
  "weekends": true
}
```

## Quick Setup

### Prerequisites
- Java 21+ (LTS)
- Maven 3.8+
- H2 Database (embedded) or PostgreSQL (production)

### 1. Clone and Build
```bash
git clone <repository-url>
cd Volunteer-Application-Service
./mvnw clean install
```

### 2. Run the Application

**Development Mode (H2 Database):**
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

**Production Mode (PostgreSQL):**
```bash
./mvnw spring-boot:run -Dspring-boot.run.profiles=prod
```

### 3. Verify Service is Running
```bash
curl http://localhost:8080/health
```

Expected Response:
```json
{
  "success": true,
  "message": "Success",
  "data": {
    "status": "UP",
    "timestamp": "2025-11-21T10:30:00.123456"
  },
  "timestamp": "2025-11-21T10:30:00.123456"
}
```

## API Documentation

For comprehensive API documentation with request/response examples, interactive testing, and schema definitions, visit the **Swagger UI** when the service is running:

**Swagger UI:** [http://localhost:8080/swagger-ui.html](http://localhost:8080/swagger-ui.html)

**OpenAPI Specification:** [http://localhost:8080/api/v1/api-docs](http://localhost:8080/api/v1/api-docs)

### Available Endpoints
- **PUT** `/api/v1/volunteers/{id}` - Update volunteer profile
- **DELETE** `/api/v1/volunteers/{id}` - Delete volunteer profile  
- **GET** `/api/v1/volunteers/{id}/drives/completed` - Get completed activities
- **GET** `/api/v1/volunteers/{id}/drives/scheduled` - Get scheduled activities

## Configuration

### Development Configuration (`application-dev.properties`)
```properties
# Server Configuration
server.port=8080

# H2 In-Memory Database (Development)
spring.datasource.url=jdbc:h2:mem:testdb
spring.datasource.username=sa
spring.datasource.password=password
spring.h2.console.enabled=true

# JPA Configuration
spring.jpa.hibernate.ddl-auto=create-drop
spring.jpa.show-sql=true

# CORS Configuration (Frontend Integration)
cors.allowed-origins=http://localhost:5174

# Redis Caching (Optional)
spring.data.redis.host=localhost
spring.data.redis.port=6379
```

### Production Configuration (`application-prod.properties`)
```properties
# Server Configuration
server.port=8080

# PostgreSQL Database (Production)
spring.datasource.url=jdbc:postgresql://localhost:5432/volunteer_db
spring.datasource.username=${DB_USERNAME:volunteer_user}
spring.datasource.password=${DB_PASSWORD:volunteer_pass}

# JPA Configuration
spring.jpa.hibernate.ddl-auto=validate
spring.jpa.show-sql=false
spring.flyway.enabled=true
```

## Microservice Integration

### Integration with Other Services

#### User Service Integration
- **Purpose:** Profile creation triggered after user registration
- **Data Flow:** User ID from User Service maps to volunteer email
- **Endpoint Dependencies:** User authentication validates volunteer access

#### NGO Posting Service Integration  
- **Purpose:** Track volunteer applications and completions
- **Data Flow:** `drives_applied` and `drives_completed` reference NGO posting IDs
- **Events:** Publishes volunteer engagement metrics

#### Matching Service Integration
- **Purpose:** Provides volunteer skills, interests, and availability data
- **Data Flow:** Location coordinates enable proximity-based matching
- **Events:** Receives optimized posting recommendations

#### Analytics Service Integration
- **Purpose:** Provides volunteer engagement and activity data
- **Data Flow:** Completion rates, skill utilization, geographic distribution
- **Events:** Real-time volunteer activity metrics

## Monitoring & Health

### Health Check Endpoint
```bash
curl http://localhost:8080/health
```

### Application Metrics
- **Actuator Endpoints:** `/actuator/health`, `/actuator/metrics`
- **Database Monitoring:** H2 Console (dev) at `http://localhost:8080/h2-console`
- **API Documentation:** Swagger UI at `http://localhost:8080/swagger-ui.html`

## Deployment

### Docker Deployment
```bash
# Build Docker image
docker build -t volunteer-service:latest .

# Run container
docker run -p 8080:8080 -e SPRING_PROFILES_ACTIVE=prod volunteer-service:latest
```

### Environment Variables
```bash
export DB_USERNAME=volunteer_user
export DB_PASSWORD=volunteer_pass
export REDIS_HOST=localhost
export REDIS_PORT=6379
```

## Development

### Technology Stack
- **Java 21** (LTS)
- **Spring Boot 3.2.10**
- **Spring Data JPA** (Database Operations)
- **H2/PostgreSQL** (Database)
- **Redis** (Caching - Optional)
- **Maven** (Dependency Management)
- **OpenAPI 3** (API Documentation)

### Key Features
- ✅ JSON-based flexible data storage
- ✅ Geographic coordinate support for location matching  
- ✅ Comprehensive validation and error handling
- ✅ Redis caching for improved performance
- ✅ CORS support for frontend integration
- ✅ Extensive API documentation
- ✅ Health monitoring and metrics

---

**Part of the Volunteer Resource Management System** | **Service Port: 8080** | **Version: 0.0.1-SNAPSHOT**