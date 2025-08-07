# CLAUDE.md

This file provides guidance to Claude Code (claude.ai/code) when working with code in this repository.

## Project Overview

This is a Spring Boot 3.5.4 application designed as a transcription ingestion system for audio processing data. The service handles complex JSON payloads containing call transcription data, audio quality metrics, and word-level metadata, persisting them into a PostgreSQL database.

## Technology Stack

- **Framework**: Spring Boot 3.5.4 with Java 21
- **Database**: PostgreSQL with Liquibase migrations
- **Testing**: JUnit 5 with Testcontainers for integration testing
- **Documentation**: Spring REST Docs with AsciiDoc generation
- **Build Tool**: Maven
- **Package**: `com.virtucon.batch_sync_service`

## Architecture

The application follows a layered Spring Boot architecture with dual-entity processing:

### Core Entities
- **Transcriptions**: Word-level transcription data with timing and confidence scores
- **Enrichments**: Sentence-level analysis and metadata
- **Files**: File URLs with unique constraint for task management
- **Tasks**: Processing tasks with file associations and status tracking
- **Shared Base**: Transcriptions and Enrichments use `BaseCallService` and inherit from common patterns

### API Endpoints
- `POST /api/transcriptions` - Ingest transcription data
- `GET /api/transcriptions/call/{callId}` - Retrieve by call ID  
- `POST /api/enrichments` - Ingest enrichment data
- `GET /api/enrichments/call/{callId}` - Retrieve by call ID
- `POST /api/tasks` - Create new task
- `GET /api/tasks/{id}` - Get task by ID
- `PUT /api/tasks/{id}` - Update task
- `DELETE /api/tasks/{id}` - Delete task
- `GET /api/tasks` - List tasks with pagination
- `GET /api/tasks/type/{taskType}` - Get tasks by type
- `GET /api/tasks/status/{taskStatus}` - Get tasks by status
- `GET /api/tasks/owner/{owner}` - Get tasks by owner
- `GET /api/tasks/file?url={url}` - Get tasks by file URL

### Data Layer
- **JPA Entities**: Complex relationships between transcriptions, words, enrichments, sentences, audio metrics, files, and tasks
- **Validation**: Custom validators for confidence scores, time ranges, and task data
- **Mappers**: MapStruct-style mapping between DTOs and entities
- **Task Management**: One-to-one relationship between files and tasks with strict constraints

### Configuration
- **Profiles**: `default`, `dev`, `demo`, `prod` with specific database configurations
- **Jackson**: Configured for UTC timestamps and null exclusion
- **Liquibase**: Schema versioning with `V1__create_schema.sql` and `V2__create_files_and_tasks.sql`
- **Scheduling**: Automatic task seeding every 10 seconds for development/demo purposes

## Common Commands

### Build and Run
```bash
./mvnw clean install                    # Build the project
./mvnw spring-boot:run                  # Run the application
java -jar target/batch-sync-service-0.0.1-SNAPSHOT.jar  # Run the JAR directly
```

### Testing
```bash
./mvnw test                             # Run all tests
./mvnw test -Dtest=BatchSyncServiceApplicationTests  # Run specific test class
./mvnw test -Dtest=BatchSyncServiceApplicationTests#contextLoads  # Run specific test method
```

### Development with Testcontainers
```bash
./mvnw spring-boot:test-run             # Run with test configuration (uses TestcontainersConfiguration)
```

### Database Operations
```bash
./mvnw liquibase:update                 # Apply database migrations
./mvnw liquibase:status                 # Check migration status
./mvnw liquibase:rollback -Dliquibase.rollbackCount=1  # Rollback last migration
```

### Documentation Generation
```bash
./mvnw clean package                    # Generates REST API documentation via AsciiDoctor
```

### Profile-Specific Testing
```bash
./mvnw test -Dspring.profiles.active=dev   # Run tests with dev profile
./mvnw test -Dspring.profiles.active=demo  # Run tests with demo profile
```

### Environment-Specific Runs
```bash
# Run with specific profile
./mvnw spring-boot:run -Dspring.profiles.active=dev

# Run with environment variables
DB_URL=jdbc:postgresql://localhost:5432/mydb ./mvnw spring-boot:run
```

## Database Schema

The application uses Liquibase for database migrations located in `src/main/resources/db/changelog/`. The schema supports complex transcription data including:
- Main transcription records
- Audio quality metrics (40+ audio analysis fields)
- Word-level transcription with timing and confidence data
- Stereo channel metadata for each word

## Test Configuration

The project uses Testcontainers for integration testing with a PostgreSQL container. The `TestcontainersConfiguration` class provides a PostgreSQL container bean that automatically starts during tests.

**Test Application**: `TestBatchSyncServiceApplication` runs the main application with test configuration.

**Test Database**: Uses `postgres:latest` Docker image via Testcontainers.

## Data Processing

### Transcription Data Flow
1. **JSON Ingestion**: Complex nested payloads via `TranscriptionController`
2. **Validation**: Custom validators for confidence scores (0.0-1.0) and time ranges
3. **Mapping**: DTO to entity transformation with relationship handling
4. **Persistence**: Atomic saves with JPA cascading for words and audio metrics

### Enrichment Data Flow
1. **Sentence Processing**: Higher-level analysis data via `EnrichmentController` 
2. **Metadata Handling**: Complex sentence-level metadata and timing
3. **Relationship Mapping**: Links to transcriptions via call_id

### Task Management Data Flow
1. **File Creation**: Unique files are created or reused based on URL
2. **Task Assignment**: Each file gets exactly one associated task
3. **Status Tracking**: Tasks move through READY → IN_PROGRESS → COMPLETED/FAILED/BLOCKED states
4. **Type Classification**: Tasks can be TRANSCRIPTION, ENRICHMENT, RELEVANCY, or NOT_ASSIGNED
5. **Automated Seeding**: Background scheduler creates random tasks every 10 seconds

### Task Enums
- **TaskType**: TRANSCRIPTION, ENRICHMENT, RELEVANCY, NOT_ASSIGNED
- **TaskStatus**: READY, IN_PROGRESS, COMPLETED, FAILED, BLOCKED

### Example Data
- Transcription example: `src/main/resources/transcription-example.json`
- Enrichment example: `src/main/resources/enrichment-example.json`
- Test data: `test_transcription.json`
- Task creation: Automatic via `TaskSeederScheduler` or manual via API

## Development Notes

- Package naming uses underscores: `com.virtucon.batch_sync_service` (note: original package with hyphens was invalid)
- PostgreSQL connection configuration should be in `application.properties`
- Spring Boot Actuator is enabled for monitoring endpoints
- The application is designed for production deployment with proper error handling and validation