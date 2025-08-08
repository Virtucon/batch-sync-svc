package com.virtucon.batch_sync_service.controller;

import java.util.List;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.virtucon.batch_sync_service.dto.CreateTaskDto;
import com.virtucon.batch_sync_service.dto.TaskDto;
import com.virtucon.batch_sync_service.dto.UpdateTaskDto;
import com.virtucon.batch_sync_service.entity.TaskEntity;
import com.virtucon.batch_sync_service.entity.TaskStatus;
import com.virtucon.batch_sync_service.entity.TaskType;
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

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> getTask(@PathVariable UUID id) {
        TaskDto data = taskService.getTaskDto(id);
        ApiResponse<TaskDto> response = ApiResponse.success("Task found", data);
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<TaskDto>> updateTask(@PathVariable UUID id, @Valid @RequestBody UpdateTaskDto updateTaskDto) {
        TaskEntity updatedTask = taskService.updateTask(id, updateTaskDto);
        TaskDto data = taskMapper.toDto(updatedTask);
        
        ApiResponse<TaskDto> response = ApiResponse.success("Task updated successfully.", data);
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Void>> deleteTask(@PathVariable UUID id) {
        taskService.deleteTask(id);
        ApiResponse<Void> response = ApiResponse.success("Task deleted successfully.");
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

    @GetMapping("/all")
    public ResponseEntity<ApiResponse<Page<TaskDto>>> getAllTasks(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "20") int size,
            @RequestParam(defaultValue = "createdAt") String sortBy,
            @RequestParam(defaultValue = "desc") String sortDir) {
        
        Sort sort = sortDir.equalsIgnoreCase("desc") ? 
                Sort.by(sortBy).descending() : Sort.by(sortBy).ascending();
        Pageable pageable = PageRequest.of(page, size, sort);
        
        Page<TaskEntity> taskPage = taskService.findAllTasks(pageable);
        Page<TaskDto> data = taskPage.map(taskMapper::toDto);
        
        ApiResponse<Page<TaskDto>> response = ApiResponse.success("Tasks retrieved successfully", data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/type/{taskType}")
    public ResponseEntity<ApiResponse<List<TaskDto>>> getTasksByType(@PathVariable TaskType taskType) {
        List<TaskEntity> tasks = taskService.findByTaskType(taskType);
        List<TaskDto> data = tasks.stream().map(taskMapper::toDto).toList();
        
        ApiResponse<List<TaskDto>> response = ApiResponse.success("Tasks found by type", data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/status/{taskStatus}")
    public ResponseEntity<ApiResponse<List<TaskDto>>> getTasksByStatus(@PathVariable TaskStatus taskStatus) {
        List<TaskEntity> tasks = taskService.findByTaskStatus(taskStatus);
        List<TaskDto> data = tasks.stream().map(taskMapper::toDto).toList();
        
        ApiResponse<List<TaskDto>> response = ApiResponse.success("Tasks found by status", data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/owner/{owner}")
    public ResponseEntity<ApiResponse<List<TaskDto>>> getTasksByOwner(@PathVariable String owner) {
        List<TaskEntity> tasks = taskService.findByOwner(owner);
        List<TaskDto> data = tasks.stream().map(taskMapper::toDto).toList();
        
        ApiResponse<List<TaskDto>> response = ApiResponse.success("Tasks found by owner", data);
        return ResponseEntity.ok(response);
    }

    @GetMapping("/file")
    public ResponseEntity<ApiResponse<List<TaskDto>>> getTasksByFileUrl(@RequestParam String url) {
        List<TaskEntity> tasks = taskService.findByFileUrl(url);
        List<TaskDto> data = tasks.stream().map(taskMapper::toDto).toList();
        
        ApiResponse<List<TaskDto>> response = ApiResponse.success("Tasks found by file URL", data);
        return ResponseEntity.ok(response);
    }

}