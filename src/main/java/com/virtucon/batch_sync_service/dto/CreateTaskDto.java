package com.virtucon.batch_sync_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virtucon.batch_sync_service.entity.TaskStatus;
import com.virtucon.batch_sync_service.entity.TaskType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;

public record CreateTaskDto(
        @JsonProperty("file_url")
        @NotBlank(message = "File URL cannot be blank")
        String fileUrl,

        @JsonProperty("task_type")
        @NotNull(message = "Task type cannot be null")
        TaskType taskType,

        @JsonProperty("task_status")
        @NotNull(message = "Task status cannot be null")
        TaskStatus taskStatus,

        @JsonProperty("processing_start")
        LocalDateTime processingStart,

        @JsonProperty("processing_end")
        LocalDateTime processingEnd,

        String owner
) {
}