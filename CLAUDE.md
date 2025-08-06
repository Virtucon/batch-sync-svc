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

## Architecture

The application follows a layered Spring Boot architecture designed for transcription data processing:

**Controller Layer**: REST endpoints for receiving transcription JSON payloads via POST `/transcriptions`

**DTO Layer**: Data transfer objects for complex nested JSON structure including:
- Main transcription payload with call_id, run_config_id, generated_at
- AudioQualityMetric with audio analysis metrics (spectral, loudness, activity, conversation data)
- Word array with timing, confidence, and stereo channel metadata

**Service Layer**: Business logic for processing and validating transcription data

**Repository Layer**: JPA repositories for PostgreSQL persistence

**Entity Layer**: JPA entities with relationships modeling the transcription data structure

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

## Example Data Structure

The service processes JSON payloads with this structure (see `src/main/resources/db/changelog/transcription-example.json`):
- Call metadata (call_id, run_config_id)
- Audio quality metrics (duration, sample rate, spectral analysis, loudness metrics, activity detection)
- Word-level transcription array with timestamps, confidence scores, and stereo channel energy/ZCR metadata
- Generation timestamp

## Development Notes

- Package naming uses underscores: `com.virtucon.batch_sync_service` (note: original package with hyphens was invalid)
- PostgreSQL connection configuration should be in `application.properties`
- Spring Boot Actuator is enabled for monitoring endpoints
- The application is designed for production deployment with proper error handling and validation