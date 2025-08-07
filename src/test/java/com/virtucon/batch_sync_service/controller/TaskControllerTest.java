package com.virtucon.batch_sync_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtucon.batch_sync_service.dto.CreateTaskDto;
import com.virtucon.batch_sync_service.dto.TaskDto;
import com.virtucon.batch_sync_service.entity.TaskEntity;
import com.virtucon.batch_sync_service.entity.TaskStatus;
import com.virtucon.batch_sync_service.entity.TaskType;
import com.virtucon.batch_sync_service.service.TaskService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDateTime;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
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
                .andExpect(jsonPath("$.data.fileUrl").value("https://example.com/test.wav"))
                .andExpect(jsonPath("$.data.taskType").value("TRANSCRIPTION"))
                .andExpect(jsonPath("$.data.taskStatus").value("READY"));
    }

    @Test
    void shouldGetTaskByIdSuccessfully() throws Exception {
        UUID taskId = UUID.randomUUID();
        TaskDto mockTaskDto = createMockTaskDto(taskId);
        
        when(taskService.getTaskDto(eq(taskId))).thenReturn(mockTaskDto);

        mockMvc.perform(get("/api/tasks/{id}", taskId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Task found"))
                .andExpect(jsonPath("$.data.id").value(taskId.toString()));
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
        return new TaskEntity(
            null, // file will be mocked separately
            TaskType.TRANSCRIPTION,
            TaskStatus.READY,
            "test-owner"
        );
    }

    private TaskDto createMockTaskDto(UUID id) {
        return new TaskDto(
            id,
            UUID.randomUUID(),
            "https://example.com/test.wav",
            TaskType.TRANSCRIPTION,
            TaskStatus.READY,
            null,
            null,
            "test-owner",
            LocalDateTime.now(),
            LocalDateTime.now()
        );
    }
}