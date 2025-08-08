# PATCH Task Endpoint Implementation

## Summary

A new PATCH endpoint has been implemented for partially updating task type and status.

## Changes Made

### 1. New DTO - `PatchTaskDto.java`
- Created a dedicated DTO for PATCH operations
- Contains only `taskType` and `taskStatus` fields
- Includes `@ValidTaskTransition` validation annotation

### 2. Service Layer Updates
- Added `patchTask(UUID id, PatchTaskDto patchTaskDto)` method to `TaskService` interface
- Implemented the method in `TaskServiceImpl` to update only non-null fields

### 3. Controller Updates 
- Added new `@PatchMapping("/{id}")` endpoint in `TaskController`
- Returns the same `ApiResponse<TaskDto>` format as other endpoints
- Includes validation using `@Valid` annotation

### 4. Validation Updates
- Created `PatchTaskTransitionValidator` for validating PATCH operations
- Updated `@ValidTaskTransition` annotation to support both `UpdateTaskDto` and `PatchTaskDto`

### 5. Tests
- Created comprehensive test suite `TaskControllerPatchTest` with 3 test scenarios:
  - Patch both type and status
  - Patch only type (status = null)
  - Patch only status (type = null)

## API Usage

```http
PATCH /api/tasks/{id}
Content-Type: application/json

{
  "task_type": "ENRICHMENT",
  "task_status": "IN_PROGRESS"
}
```

### Example Response
```json
{
  "success": true,
  "message": "Task patched successfully.",
  "data": {
    "id": "123e4567-e89b-12d3-a456-426614174000",
    "task_type": "ENRICHMENT", 
    "task_status": "IN_PROGRESS",
    // ... other task fields
  }
}
```

### Differences from PUT endpoint
- PATCH allows partial updates (only specified fields are updated)
- PUT requires all fields and replaces the entire resource
- Both endpoints return the same response format
