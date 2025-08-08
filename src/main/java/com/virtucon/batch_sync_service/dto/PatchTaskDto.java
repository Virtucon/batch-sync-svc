package com.virtucon.batch_sync_service.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.virtucon.batch_sync_service.entity.TaskStatus;
import com.virtucon.batch_sync_service.entity.TaskType;
import com.virtucon.batch_sync_service.validation.ValidTaskTransition;

@ValidTaskTransition
public record PatchTaskDto(
        @JsonProperty("task_type")
        TaskType taskType,

        @JsonProperty("task_status")
        TaskStatus taskStatus
) {
}
