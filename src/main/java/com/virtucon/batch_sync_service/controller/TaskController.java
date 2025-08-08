package com.virtucon.batch_sync_service.controller;

import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virtucon.batch_sync_service.dto.CreateTaskDto;
import com.virtucon.batch_sync_service.dto.PatchTaskDto;
import com.virtucon.batch_sync_service.dto.TaskDto;
import com.virtucon.batch_sync_service.entity.TaskEntity;
import com.virtucon.batch_sync_service.mapper.TaskMapper;
import com.virtucon.batch_sync_service.response.ApiResponse;
import com.virtucon.batch_sync_service.service.TaskService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("api/tasks")
public class TaskController {

    private final TaskService taskService;
    private final TaskMapper taskMapper;

    public TaskController(TaskService taskService, TaskMapper taskMapper) {
        this.taskService = taskService;
        this.taskMapper = taskMapper;
    }

    @PostMapping
    public ResponseEntity<ApiResponse<TaskDto>> createTask(@Valid @RequestBody CreateTaskDto createTaskDto) {
        TaskEntity savedTask = taskService.createTask(createTaskDto);
        TaskDto data = taskMapper.toDto(savedTask);
        
        ApiResponse<TaskDto> response = ApiResponse.success("Task created successfully.", data);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> patchTask(@PathVariable UUID id, @Valid @RequestBody PatchTaskDto patchTaskDto) {
        TaskEntity patchedTask = taskService.patchTask(id, patchTaskDto);
        TaskDto data = taskMapper.toDto(patchedTask);
        
        ApiResponse<TaskDto> response = ApiResponse.success("Task patched successfully.", data);
        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<Page<TaskDto>>> getAllNotAssignedReadyTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<TaskEntity> taskPage = taskService.findNotAssignedReadyTasks(pageable);
        Page<TaskDto> data = taskPage.map(taskMapper::toDto);
        
        ApiResponse<Page<TaskDto>> response = ApiResponse.success("Not assigned ready tasks retrieved successfully", data);
        return ResponseEntity.ok(response);
    }
}