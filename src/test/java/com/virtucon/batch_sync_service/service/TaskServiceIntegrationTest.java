package com.virtucon.batch_sync_service.service;

import static org.assertj.core.api.Assertions.assertThat;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.transaction.annotation.Transactional;

import com.virtucon.batch_sync_service.TestcontainersConfiguration;
import com.virtucon.batch_sync_service.dto.CreateTaskDto;
import com.virtucon.batch_sync_service.entity.TaskEntity;
import com.virtucon.batch_sync_service.entity.TaskStatus;
import com.virtucon.batch_sync_service.entity.TaskType;
import com.virtucon.batch_sync_service.repository.TaskRepository;

@SpringBootTest
@Import(TestcontainersConfiguration.class)
@ActiveProfiles("test")
@Transactional
class TaskServiceIntegrationTest {

    @Autowired
    private TaskService taskService;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    void shouldCreateTaskWithUniqueFile() {
        String fileUrl = "https://example.com/file1.wav";
        CreateTaskDto createTaskDto = new CreateTaskDto(
            fileUrl,
            TaskType.TRANSCRIPTION,
            TaskStatus.READY,
            null,
            null,
            "test-owner"
        );

        TaskEntity createdTask = taskService.createTask(createTaskDto);

        assertThat(createdTask).isNotNull();
        assertThat(createdTask.getId()).isNotNull();
        assertThat(createdTask.getFile().getUrl()).isEqualTo(fileUrl);
        assertThat(createdTask.getTaskType()).isEqualTo(TaskType.TRANSCRIPTION);
        assertThat(createdTask.getTaskStatus()).isEqualTo(TaskStatus.READY);
        assertThat(createdTask.getOwner()).isEqualTo("test-owner");
        assertThat(createdTask.getCreatedAt()).isNotNull();
        assertThat(createdTask.getUpdatedAt()).isNotNull();
    }

    @Test
    void shouldReuseExistingFileForSameUrl() {
        String fileUrl = "https://example.com/shared-file.wav";
        
        CreateTaskDto firstTaskDto = new CreateTaskDto(
            fileUrl,
            TaskType.TRANSCRIPTION,
            TaskStatus.READY,
            null,
            null,
            "owner1"
        );
        
        CreateTaskDto secondTaskDto = new CreateTaskDto(
            fileUrl,
            TaskType.ENRICHMENT,
            TaskStatus.READY,
            null,
            null,
            "owner2"
        );

        TaskEntity firstTask = taskService.createTask(firstTaskDto);
        TaskEntity secondTask = taskService.createTask(secondTaskDto);

        assertThat(firstTask.getFile().getId()).isEqualTo(secondTask.getFile().getId());
        assertThat(firstTask.getFile().getUrl()).isEqualTo(secondTask.getFile().getUrl());
        assertThat(firstTask.getId()).isNotEqualTo(secondTask.getId());
    }
}