# Volunteer Application Service

A simplified Spring Boot microservice for managing volunteer applications to NGO postings.

## � Purpose

This service handles:
1. **Apply for Posting** - Volunteers apply for NGO postings (POST)
2. **Get Applications** - List all applications for a volunteer (GET)  
3. **Deregister** - Remove application from a posting (DELETE)

The volunteer data is mapped to the User Service database where user registration and preferences are stored.

## �️ Database Schema

### Table: `volunteer_applications`

| Column | Type | Description |
|--------|------|-------------|
| `id` | BIGINT (PK) | Application ID |
| `volunteer_name` | VARCHAR | Volunteer's name |
| `user_id` | BIGINT | Maps to User Service |
| `posting_id` | BIGINT | NGO posting ID |
| `posting_title` | VARCHAR | Posting title |
| `applied_at` | TIMESTAMP | Application timestamp |

## � Quick Setup

### 1. Setup PostgreSQL Database with Docker

```bash
# Start PostgreSQL container
docker-compose -f docker-compose-db.yml up -d

# Check if database is running
docker ps
```

This creates:
- **Database**: `volunteer_db`
- **Username**: `volunteer_user` 
- **Password**: `volunteer_pass`
- **Port**: `5432`

### 2. Connect to Database with DBeaver

1. Open DBeaver
2. Create New Connection → PostgreSQL
3. Configure:
   - **Host**: `localhost`
   - **Port**: `5432`
   - **Database**: `volunteer_db`
   - **Username**: `volunteer_user`
   - **Password**: `volunteer_pass`
4. Test Connection and Save

### 3. Run the Application

```bash
# Run with dev profile (uses PostgreSQL)
./mvnw spring-boot:run -Dspring-boot.run.profiles=dev
```

The service will:
- Start on port **8083**
- Auto-create the `volunteer_applications` table
- Connect to your PostgreSQL database

## 📋 API Endpoints

### 1. Apply for Posting
```http
POST /api/volunteer-applications/apply
Content-Type: application/json

{
  "volunteerName": "John Doe",
  "userId": 123,
  "postingId": 456,
  "postingTitle": "Community Clean-up Drive"
}
```

### 2. Get Volunteer Applications
```http
GET /api/volunteer-applications/volunteer/{userId}
```

### 3. Deregister from Posting
```http
DELETE /api/volunteer-applications/deregister/{userId}/{postingId}
```

## 🧪 Testing the API

### Apply for a posting:
```bash
curl -X POST http://localhost:8083/api/volunteer-applications/apply \
  -H "Content-Type: application/json" \
  -d '{
    "volunteerName": "John Doe",
    "userId": 123,
    "postingId": 456,
    "postingTitle": "Community Clean-up Drive"
  }'
```

### Get applications for user:
```bash
curl http://localhost:8083/api/volunteer-applications/volunteer/123
```

### Deregister from posting:
```bash
curl -X DELETE http://localhost:8083/api/volunteer-applications/deregister/123/456
```

## � Configuration

The service connects to PostgreSQL using these settings (in `application-dev.properties`):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/volunteer_db
spring.datasource.username=volunteer_user
spring.datasource.password=volunteer_pass
spring.jpa.hibernate.ddl-auto=update
```

## 🔗 Integration Points

- **User Service**: `userId` field maps to user registration data
- **Matching Service**: Gets matched postings based on user preferences
- **NGO Postings Service**: `postingId` references available opportunities

## 📊 Database View in DBeaver

Once running, you can view the `volunteer_applications` table in DBeaver to see:
- All volunteer applications
- Volunteer names and their applied postings
- Application timestamps
- User ID mappings to User Service