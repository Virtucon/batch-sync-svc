package com.virtucon.batch_sync_service.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.virtucon.batch_sync_service.dto.PatchTaskDto;
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
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(TaskController.class)
@Import(TaskMapper.class)
class TaskControllerPatchTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private TaskService taskService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void shouldPatchTaskSuccessfully() throws Exception {
        UUID taskId = UUID.randomUUID();
        
        PatchTaskDto patchTaskDto = new PatchTaskDto(
            TaskType.ENRICHMENT,
            TaskStatus.IN_PROGRESS
        );

        TaskEntity mockTaskEntity = createMockPatchedTaskEntity(taskId);
        when(taskService.patchTask(eq(taskId), any(PatchTaskDto.class))).thenReturn(mockTaskEntity);

        mockMvc.perform(patch("/api/tasks/{id}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchTaskDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Task patched successfully."))
                .andExpect(jsonPath("$.data.id").value(taskId.toString()))
                .andExpect(jsonPath("$.data.task_type").value("ENRICHMENT"))
                .andExpect(jsonPath("$.data.task_status").value("IN_PROGRESS"));
    }

    @Test
    void shouldPatchTaskWithOnlyTypeSuccessfully() throws Exception {
        UUID taskId = UUID.randomUUID();
        
        PatchTaskDto patchTaskDto = new PatchTaskDto(
            TaskType.TRANSCRIPTION,
            null // Only updating type, not status
        );

        TaskEntity mockTaskEntity = createMockPatchedTaskEntity(taskId);
        mockTaskEntity.setTaskType(TaskType.TRANSCRIPTION);
        when(taskService.patchTask(eq(taskId), any(PatchTaskDto.class))).thenReturn(mockTaskEntity);

        mockMvc.perform(patch("/api/tasks/{id}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchTaskDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Task patched successfully."))
                .andExpect(jsonPath("$.data.task_type").value("TRANSCRIPTION"));
    }

    @Test
    void shouldPatchTaskWithOnlyStatusSuccessfully() throws Exception {
        UUID taskId = UUID.randomUUID();
        
        PatchTaskDto patchTaskDto = new PatchTaskDto(
            null, // Only updating status, not type
            TaskStatus.COMPLETED
        );

        TaskEntity mockTaskEntity = createMockPatchedTaskEntity(taskId);
        mockTaskEntity.setTaskStatus(TaskStatus.COMPLETED);
        when(taskService.patchTask(eq(taskId), any(PatchTaskDto.class))).thenReturn(mockTaskEntity);

        mockMvc.perform(patch("/api/tasks/{id}", taskId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(patchTaskDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.success").value(true))
                .andExpect(jsonPath("$.message").value("Task patched successfully."))
                .andExpect(jsonPath("$.data.task_status").value("COMPLETED"));
    }

    private TaskEntity createMockPatchedTaskEntity(UUID id) {
        TaskEntity entity = null;
        try {
            FileEntity file = new FileEntity("https://example.com/test.wav");
            java.lang.reflect.Field fileIdField = FileEntity.class.getDeclaredField("id");
            fileIdField.setAccessible(true);
            fileIdField.set(file, UUID.randomUUID());

            entity = new TaskEntity(file, TaskType.ENRICHMENT, TaskStatus.IN_PROGRESS, "test-owner");
            
            java.lang.reflect.Field idField = TaskEntity.class.getDeclaredField("id");
            idField.setAccessible(true);
            idField.set(entity, id);
            
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
