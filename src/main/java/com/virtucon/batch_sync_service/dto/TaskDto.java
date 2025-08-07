package com.virtucon.batch_sync_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virtucon.batch_sync_service.entity.TaskStatus;
import com.virtucon.batch_sync_service.entity.TaskType;

import java.time.LocalDateTime;
import java.util.UUID;

public record TaskDto(
        UUID id,

        @JsonProperty("file_id")
        UUID fileId,

        @JsonProperty("file_url")
        String fileUrl,

        @JsonProperty("task_type")
        TaskType taskType,

        @JsonProperty("task_status")
        TaskStatus taskStatus,

        @JsonProperty("processing_start")
        LocalDateTime processingStart,

        @JsonProperty("processing_end")
        LocalDateTime processingEnd,

        String owner,

        @JsonProperty("created_at")
        LocalDateTime createdAt,

        @JsonProperty("updated_at")
        LocalDateTime updatedAt
) {
}