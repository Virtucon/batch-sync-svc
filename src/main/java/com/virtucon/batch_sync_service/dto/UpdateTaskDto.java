package com.virtucon.batch_sync_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virtucon.batch_sync_service.entity.TaskStatus;
import com.virtucon.batch_sync_service.entity.TaskType;

import java.time.LocalDateTime;

public record UpdateTaskDto(
        @JsonProperty("task_type")
        TaskType taskType,

        @JsonProperty("task_status")
        TaskStatus taskStatus,

        @JsonProperty("processing_start")
        LocalDateTime processingStart,

        @JsonProperty("processing_end")
        LocalDateTime processingEnd,

        String owner
) {
}