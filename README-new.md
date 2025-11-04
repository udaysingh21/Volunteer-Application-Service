# Volunteer Service

A comprehensive Spring Boot microservice for managing volunteers with Redis caching, event publishing, and geographical utilities.

## Features

- **Volunteer Management**: Complete CRUD operations for volunteer profiles
- **Skill Management**: Track and manage volunteer skills
- **Availability Tracking**: Monitor volunteer availability schedules
- **Redis Caching**: High-performance caching for frequently accessed data
- **Event Publishing**: Asynchronous event publishing for domain events
- **Geographical Utilities**: Location-based operations and calculations
- **API Documentation**: Interactive OpenAPI/Swagger documentation
- **Database Migrations**: Flyway-based database versioning
- **Health Monitoring**: Spring Boot Actuator endpoints
- **Docker Support**: Containerized deployment with Docker Compose

## Technology Stack

- **Java 17**
- **Spring Boot 3.2.10**
- **Spring Data JPA**
- **PostgreSQL 15**
- **Redis 7**
- **Flyway**
- **MapStruct**
- **OpenAPI 3 (Swagger)**
- **Testcontainers**
- **Docker & Docker Compose**

## Project Structure

```
volunteer-service/
├── pom.xml
├── Dockerfile
├── docker-compose.yml
├── .env
├── .gitignore
├── README.md
└── src/
    ├── main/
    │   ├── java/com/volunteer/service/
    │   │   ├── VolunteerServiceApplication.java
    │   │   ├── config/
    │   │   │   ├── RedisConfig.java
    │   │   │   ├── OpenApiConfig.java
    │   │   │   ├── JacksonConfig.java
    │   │   │   └── AsyncConfig.java
    │   │   ├── controller/
    │   │   │   └── VolunteerController.java
    │   │   ├── service/
    │   │   │   ├── VolunteerService.java
    │   │   │   ├── SkillService.java
    │   │   │   └── EventPublisher.java
    │   │   ├── repository/
    │   │   │   ├── VolunteerRepository.java
    │   │   │   ├── SkillRepository.java
    │   │   │   └── VolunteerSkillRepository.java
    │   │   ├── model/
    │   │   │   ├── Volunteer.java
    │   │   │   ├── Skill.java
    │   │   │   ├── VolunteerSkill.java
    │   │   │   └── Availability.java
    │   │   ├── dto/
    │   │   │   ├── VolunteerRequestDTO.java
    │   │   │   ├── VolunteerResponseDTO.java
    │   │   │   ├── VolunteerUpdateDTO.java
    │   │   │   ├── SkillRequestDTO.java
    │   │   │   ├── SkillResponseDTO.java
    │   │   │   ├── AvailabilityDTO.java
    │   │   │   └── ApiResponse.java
    │   │   ├── exception/
    │   │   │   ├── GlobalExceptionHandler.java
    │   │   │   ├── ResourceNotFoundException.java
    │   │   │   ├── DuplicateResourceException.java
    │   │   │   └── InvalidDataException.java
    │   │   ├── event/
    │   │   │   ├── VolunteerEvent.java
    │   │   │   └── EventType.java
    │   │   └── util/
    │   │       └── GeoUtils.java
    │   └── resources/
    │       ├── application.yml
    │       ├── application-dev.yml
    │       ├── application-prod.yml
    │       └── db/migration/
    │           ├── V1__initial_schema.sql
    │           └── V2__add_indexes.sql
    └── test/
        └── java/com/volunteer/service/
            ├── VolunteerServiceApplicationTests.java
            ├── controller/
            │   └── VolunteerControllerTest.java
            └── service/
                └── VolunteerServiceTest.java
```

## Getting Started

### Prerequisites

- Java 17 or higher
- Maven 3.8+
- Docker and Docker Compose (for containerized setup)

### Local Development

1. **Clone the repository**
   ```bash
   git clone <repository-url>
   cd volunteer-service
   ```

2. **Set up environment variables**
   ```bash
   cp .env.example .env
   # Edit .env with your configuration
   ```

3. **Start the infrastructure services**
   ```bash
   docker-compose up -d postgres redis
   ```

4. **Run the application**
   ```bash
   ./mvnw spring-boot:run
   ```

### Docker Deployment

1. **Start all services**
   ```bash
   docker-compose up -d
   ```

2. **View logs**
   ```bash
   docker-compose logs -f volunteer-service
   ```

3. **Stop services**
   ```bash
   docker-compose down
   ```

## API Documentation

Once the application is running, access the API documentation at:
- **Swagger UI**: http://localhost:8080/swagger-ui.html
- **OpenAPI JSON**: http://localhost:8080/v3/api-docs

## Health Checks

- **Application Health**: http://localhost:8080/actuator/health
- **Application Info**: http://localhost:8080/actuator/info
- **Metrics**: http://localhost:8080/actuator/metrics

## Database Management

- **Redis Commander**: http://localhost:8081 (when using Docker Compose)
- **Database Migrations**: Automatically applied on startup via Flyway

## Testing

```bash
# Run unit tests
./mvnw test

# Run integration tests
./mvnw verify

# Run tests with coverage
./mvnw test jacoco:report
```

## Configuration

### Environment Variables

| Variable | Description | Default |
|----------|-------------|---------|
| `SERVER_PORT` | Application port | `8080` |
| `SPRING_PROFILES_ACTIVE` | Active Spring profile | `dev` |
| `DB_HOST` | PostgreSQL host | `localhost` |
| `DB_PORT` | PostgreSQL port | `5432` |
| `REDIS_HOST` | Redis host | `localhost` |
| `REDIS_PORT` | Redis port | `6379` |

### Profiles

- **dev**: Development profile with detailed logging
- **prod**: Production profile with optimized settings

## Contributing

1. Fork the repository
2. Create a feature branch (`git checkout -b feature/amazing-feature`)
3. Commit your changes (`git commit -m 'Add some amazing feature'`)
4. Push to the branch (`git push origin feature/amazing-feature`)
5. Open a Pull Request

## License

This project is licensed under the MIT License - see the [LICENSE](LICENSE) file for details.