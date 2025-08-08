package com.virtucon.batch_sync_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtucon.batch_sync_service.dto.CreateTaskDto;
import com.virtucon.batch_sync_service.entity.FileEntity;
import com.virtucon.batch_sync_service.entity.TaskEntity;
import com.virtucon.batch_sync_service.entity.TaskStatus;
import com.virtucon.batch_sync_service.entity.TaskType;
import com.virtucon.batch_sync_service.mapper.TaskMapper;
import com.virtucon.batch_sync_service.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.context.annotation.Import;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@Import(TaskMapper.class)
class TaskControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldCreateTaskSuccessfully() throws Exception {
        CreateTaskDto createTaskDto = new CreateTaskDto(
            "https://example.com/test.wav",
            TaskType.TRANSCRIPTION,
            TaskStatus.READY,
            null,
            null,
            "test-owner"
        );

        TaskEntity mockTaskEntity = createMockTaskEntity();
        when(taskService.createTask(any(CreateTaskDto.class))).thenReturn(mockTaskEntity);

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(createTaskDto)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Task created successfully."))
                .andExpect(jsonPath("$.data.id").exists())
                .andExpect(jsonPath("$.data.file_url").value("https://example.com/test.wav"))
                .andExpect(jsonPath("$.data.task_type").value("TRANSCRIPTION"))
                .andExpect(jsonPath("$.data.task_status").value("READY"));
    }



    @Test
    void shouldReturn400ForInvalidCreateTaskRequest() throws Exception {
        CreateTaskDto invalidDto = new CreateTaskDto(
            null, // invalid - null fileUrl
            TaskType.TRANSCRIPTION,
            TaskStatus.READY,
            null,
            null,
            "test-owner"
        );

        mockMvc.perform(post("/api/tasks")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(invalidDto)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.success").value(false));
    }

    private TaskEntity createMockTaskEntity() {
        FileEntity mockFile = new FileEntity("https://example.com/test.wav");
        try {
            java.lang.reflect.Field fileIdField = FileEntity.class.getDeclaredField("id");
            fileIdField.setAccessible(true);
            fileIdField.set(mockFile, UUID.randomUUID());
        } catch (Exception e) {
            throw new RuntimeException("Failed to set mock file ID", e);
        }
        
        TaskEntity entity = new TaskEntity(
            mockFile,
            TaskType.TRANSCRIPTION,
            TaskStatus.READY,
            "test-owner"
        );
        // Simulate the entity having an ID as it would after being saved
        try {
            java.lang.reflect.Field idField = TaskEntity.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, UUID.randomUUID());
            
            java.lang.reflect.Field createdAtField = TaskEntity.class.getDeclaredField("createdAt");
            createdAtField.setAccessible(true);
            createdAtField.set(entity, LocalDateTime.now());
            
            java.lang.reflect.Field updatedAtField = TaskEntity.class.getDeclaredField("updatedAt");
            updatedAtField.setAccessible(true);
            updatedAtField.set(entity, LocalDateTime.now());
        } catch (Exception e) {
            throw new RuntimeException("Failed to set mock entity fields", e);
        }
        return entity;
    }
}