package com.virtucon.batch_sync_service.mapper;

import java.time.LocalDateTime;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import com.virtucon.batch_sync_service.dto.CreateTaskDto;
import com.virtucon.batch_sync_service.dto.TaskDto;
import com.virtucon.batch_sync_service.entity.FileEntity;
import com.virtucon.batch_sync_service.entity.TaskEntity;
import com.virtucon.batch_sync_service.entity.TaskStatus;
import com.virtucon.batch_sync_service.entity.TaskType;

class TaskMapperTest {

    private TaskMapper taskMapper;

    @BeforeEach
    void setUp() {
        taskMapper = new TaskMapper();
    }

    @Test
    void shouldMapTaskEntityToDto() {
        FileEntity file = new FileEntity("https://example.com/test.wav");
        
        TaskEntity entity = new TaskEntity(file, TaskType.TRANSCRIPTION, TaskStatus.READY, "test-owner");
        entity.setProcessingStart(LocalDateTime.now());

        TaskDto dto = taskMapper.toDto(entity);

        assertThat(dto).isNotNull();
        assertThat(dto.fileUrl()).isEqualTo(file.getUrl());
        assertThat(dto.taskType()).isEqualTo(TaskType.TRANSCRIPTION);
        assertThat(dto.taskStatus()).isEqualTo(TaskStatus.READY);
        assertThat(dto.owner()).isEqualTo("test-owner");
        assertThat(dto.processingStart()).isEqualTo(entity.getProcessingStart());
    }

    @Test
    void shouldMapCreateTaskDtoToEntity() {
        CreateTaskDto dto = new CreateTaskDto(
            "https://example.com/test.wav",
            TaskType.ENRICHMENT,
            TaskStatus.IN_PROGRESS,
            LocalDateTime.now(),
            LocalDateTime.now().plusHours(1),
            "mapper-test-owner"
        );

        TaskEntity entity = taskMapper.toEntity(dto);

        assertThat(entity).isNotNull();
        assertThat(entity.getTaskType()).isEqualTo(TaskType.ENRICHMENT);
        assertThat(entity.getTaskStatus()).isEqualTo(TaskStatus.IN_PROGRESS);
        assertThat(entity.getOwner()).isEqualTo("mapper-test-owner");
        assertThat(entity.getProcessingStart()).isEqualTo(dto.processingStart());
        assertThat(entity.getProcessingEnd()).isEqualTo(dto.processingEnd());
        assertThat(entity.getId()).isNull();
        assertThat(entity.getFile()).isNull();
    }
}