package com.virtucon.batch_sync_service.mapper;

import org.springframework.stereotype.Component;

import com.virtucon.batch_sync_service.dto.CreateTaskDto;
import com.virtucon.batch_sync_service.dto.TaskDto;
import com.virtucon.batch_sync_service.entity.TaskEntity;

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
        entity.setProcessingStart(dto.processingStart());
        entity.setProcessingEnd(dto.processingEnd());
        return entity;
    }
}