package com.virtucon.batch_sync_service.mapper;

import com.virtucon.batch_sync_service.dto.CreateTaskDto;
import com.virtucon.batch_sync_service.dto.TaskDto;
import com.virtucon.batch_sync_service.dto.UpdateTaskDto;
import com.virtucon.batch_sync_service.entity.TaskEntity;
import org.springframework.stereotype.Component;

@Component
public class TaskMapper {

    public TaskDto toDto(TaskEntity entity) {
        if (entity == null) {
            return null;
        }
        
        return new TaskDto(
            entity.getId(),
            entity.getFile() != null ? entity.getFile().getId() : null,
            entity.getFile() != null ? entity.getFile().getUrl() : null,
            entity.getTaskType(),
            entity.getTaskStatus(),
            entity.getProcessingStart(),
            entity.getProcessingEnd(),
            entity.getOwner(),
            entity.getCreatedAt(),
            entity.getUpdatedAt()
        );
    }

    public TaskEntity toEntity(CreateTaskDto dto) {
        if (dto == null) {
            return null;
        }
        
        TaskEntity entity = new TaskEntity(null, dto.taskType(), dto.taskStatus(), dto.owner());
        return entity;
    }

    public void updateEntityFromDto(UpdateTaskDto dto, TaskEntity entity) {
        if (dto == null || entity == null) {
            return;
        }
        
        if (dto.taskType() != null) {
            entity.setTaskType(dto.taskType());
        }
        if (dto.taskStatus() != null) {
            entity.setTaskStatus(dto.taskStatus());
        }
        if (dto.processingStart() != null) {
            entity.setProcessingStart(dto.processingStart());
        }
        if (dto.processingEnd() != null) {
            entity.setProcessingEnd(dto.processingEnd());
        }
        if (dto.owner() != null) {
            entity.setOwner(dto.owner());
        }
    }
}