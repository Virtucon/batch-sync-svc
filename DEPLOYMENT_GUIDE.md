# Deployment Guide - Spring Boot Transcription Service

## Quick Start Options

The application supports multiple profiles for different deployment scenarios:

### 🚀 Option 1: Demo Mode (No Database Setup Required)
**Best for:** Quick testing, demonstrations, development without PostgreSQL

```bash
# Run with H2 in-memory database
java -jar target/batch-sync-service-0.0.1-SNAPSHOT.jar --spring.profiles.active=demo

# Access the application
curl http://localhost:8080/actuator/health

# Access H2 console (for debugging)
# http://localhost:8080/h2-console
# JDBC URL: jdbc:h2:mem:testdb
# Username: sa
# Password: (leave blank)
```

**Features:**
- ✅ H2 in-memory database (no setup required)
- ✅ Auto-creates tables on startup
- ✅ H2 web console enabled
- ✅ Debug logging enabled
- ❌ Data is lost when application stops

### 🏢 Option 2: Development Mode (PostgreSQL Required)
**Best for:** Local development with persistent data

```bash
# Set environment variables
export DB_USERNAME=dev_user
export DB_PASSWORD=dev_password

# Run with PostgreSQL
java -jar target/batch-sync-service-0.0.1-SNAPSHOT.jar --spring.profiles.active=dev

# Or with command line parameters
java -jar target/batch-sync-service-0.0.1-SNAPSHOT.jar \
  --spring.profiles.active=dev \
  --spring.datasource.username=your_user \
  --spring.datasource.password=your_password
```

**Prerequisites:**
```sql
-- Create database and user
CREATE DATABASE batch_sync_svc_dev_db;
CREATE USER dev_user WITH PASSWORD 'dev_password';
GRANT ALL PRIVILEGES ON DATABASE batch_sync_svc_dev_db TO dev_user;
```

### 🌐 Option 3: Production Mode (Full Configuration)
**Best for:** Production deployments

```bash
# Set required environment variables
export DB_URL=jdbc:postgresql://your-db-host:5432/your-database
export DB_USERNAME=your_production_user
export DB_PASSWORD=your_production_password
export SERVER_PORT=8080

# Optional tuning parameters
export DB_POOL_SIZE=20
export DB_MIN_IDLE=5

# Run in production mode
java -jar target/batch-sync-service-0.0.1-SNAPSHOT.jar --spring.profiles.active=prod
```

### 🔧 Option 4: Default Mode (Original Configuration)
**Best for:** Backward compatibility with existing setups

```bash
# Uses original database configuration
java -jar target/batch-sync-service-0.0.1-SNAPSHOT.jar

# Or explicitly
java -jar target/batch-sync-service-0.0.1-SNAPSHOT.jar --spring.profiles.active=default
```

**Database:** `jdbc:postgresql://localhost:5432/batch-sync-svc-db`
**Username:** `username`
**Password:** `password`

## Database Setup

### PostgreSQL Setup (Required for dev/prod/default profiles)

#### Using Docker
```bash
# Start PostgreSQL container
docker run --name batch-sync-postgres \
  -e POSTGRES_DB=batch_sync_svc_dev_db \
  -e POSTGRES_USER=dev_user \
  -e POSTGRES_PASSWORD=dev_password \
  -p 5432:5432 \
  -d postgres:latest

# Verify connection
docker exec -it batch-sync-postgres psql -U dev_user -d batch_sync_svc_dev_db -c "SELECT version();"
```

#### Manual Setup
```sql
-- Connect as superuser and create database
CREATE DATABASE batch_sync_svc_dev_db;
CREATE USER dev_user WITH PASSWORD 'dev_password';
GRANT ALL PRIVILEGES ON DATABASE batch_sync_svc_dev_db TO dev_user;
GRANT ALL ON SCHEMA public TO dev_user;
```

### Schema Migration
The application uses Liquibase for database migrations:
- Tables are created automatically on first startup
- Migration files located in: `src/main/resources/db/changelog/`
- Current version: 7 changesets

## Configuration Reference

### Environment Variables

| Variable | Description | Default | Required |
|----------|-------------|---------|----------|
| `SPRING_PROFILES_ACTIVE` | Application profile | `default` | No |
| `DB_URL` | Database JDBC URL | localhost:5432 | Yes (except demo) |
| `DB_USERNAME` | Database username | `username` | Yes (except demo) |
| `DB_PASSWORD` | Database password | `password` | Yes (except demo) |
| `SERVER_PORT` | Server port | `8080` | No |
| `DB_POOL_SIZE` | Connection pool size | `20` | No |
| `DB_MIN_IDLE` | Min idle connections | `5` | No |

### Profile Comparison

| Feature | demo | dev | prod | default |
|---------|------|-----|------|---------|
| Database | H2 (memory) | PostgreSQL | PostgreSQL | PostgreSQL |
| Liquibase | Disabled | Enabled | Enabled | Enabled |
| SQL Logging | Yes | Yes | No | Yes |
| H2 Console | Enabled | Disabled | Disabled | Disabled |
| Actuator | Full | Extended | Limited | Basic |
| Log Level | DEBUG | DEBUG | INFO | INFO |

## Health Check Endpoints

All profiles expose basic health check endpoints:

```bash
# Basic health check
curl http://localhost:8080/actuator/health

# Application info
curl http://localhost:8080/actuator/info

# Metrics (if enabled by profile)
curl http://localhost:8080/actuator/metrics
```

## API Endpoints

### Create Transcription
```bash
POST /api/transcriptions
Content-Type: application/json

{
  "call_id": "550e8400-e29b-41d4-a716-446655440000",
  "audio_quality_metric": { ... },
  "run_config_id": "123e4567-e89b-12d3-a456-426614174000",
  "words": [ ... ],
  "generated_at": "2024-01-15T10:30:00Z"
}
```

### Get Transcription
```bash
GET /api/transcriptions/call/{callId}
```

### Create Enrichment
```bash
POST /api/enrichments
Content-Type: application/json

{
  "call_id": "550e8400-e29b-41d4-a716-446655440000",
  "audio_quality_metric": { ... },
  "run_config_id": "123e4567-e89b-12d3-a456-426614174000",
  "sentences": [ ... ],
  "generated_at": "2024-01-15T10:30:00Z"
}
```

## Troubleshooting

### Common Issues

#### 1. Database Connection Failed
```
ERROR: password authentication failed for user "dev_user"
```
**Solution:** 
- Verify database credentials
- Ensure PostgreSQL is running
- Check network connectivity
- Use demo profile for testing without PostgreSQL

#### 2. Port Already in Use
```
ERROR: Port 8080 was already in use
```
**Solution:**
```bash
# Use different port
java -jar target/batch-sync-service-0.0.1-SNAPSHOT.jar --server.port=8081

# Or use random available port
java -jar target/batch-sync-service-0.0.1-SNAPSHOT.jar --server.port=0
```

#### 3. Liquibase Migration Failed
```
ERROR: Liquibase migration failed
```
**Solution:**
- Check database permissions
- Verify schema exists
- Use demo profile to bypass Liquibase

### Debug Mode
Enable debug logging for troubleshooting:
```bash
java -jar target/batch-sync-service-0.0.1-SNAPSHOT.jar \
  --spring.profiles.active=demo \
  --logging.level.com.virtucon.batch_sync_service=DEBUG \
  --logging.level.org.springframework.web=DEBUG
```

## Production Checklist

- [ ] PostgreSQL database configured and accessible
- [ ] Environment variables set (DB_URL, DB_USERNAME, DB_PASSWORD)
- [ ] Network security configured (firewall rules)
- [ ] Monitoring endpoints secured
- [ ] Log aggregation configured
- [ ] Backup strategy in place
- [ ] Health checks configured in load balancer
- [ ] SSL/TLS certificates configured (if needed)
- [ ] Resource limits configured (memory, CPU)
- [ ] Error alerting configured

## Docker Deployment

### Option 1: Simple Docker Run
```dockerfile
FROM openjdk:21-jre-slim
COPY target/batch-sync-service-0.0.1-SNAPSHOT.jar app.jar
EXPOSE 8080
ENTRYPOINT ["java","-jar","/app.jar"]
```

```bash
# Build and run
docker build -t batch-sync-service .
docker run -d -p 8080:8080 \
  -e SPRING_PROFILES_ACTIVE=demo \
  batch-sync-service
```

### Option 2: Docker Compose with PostgreSQL
```yaml
version: '3.8'
services:
  app:
    image: batch-sync-service
    ports:
      - "8080:8080"
    environment:
      - SPRING_PROFILES_ACTIVE=dev
      - DB_URL=jdbc:postgresql://postgres:5432/batch_sync_db
      - DB_USERNAME=postgres
      - DB_PASSWORD=password
    depends_on:
      - postgres
      
  postgres:
    image: postgres:latest
    environment:
      - POSTGRES_DB=batch_sync_db
      - POSTGRES_USER=postgres
      - POSTGRES_PASSWORD=password
    volumes:
      - postgres_data:/var/lib/postgresql/data

volumes:
  postgres_data:
```

## Support

For issues and questions:
1. Check the application logs
2. Verify configuration settings
3. Test with demo profile first
4. Review the troubleshooting section above