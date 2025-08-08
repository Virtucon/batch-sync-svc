package com.virtucon.batch_sync_service.service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.virtucon.batch_sync_service.dto.CreateTaskDto;
import com.virtucon.batch_sync_service.dto.TaskDto;
import com.virtucon.batch_sync_service.dto.UpdateTaskDto;
import com.virtucon.batch_sync_service.entity.TaskEntity;
import com.virtucon.batch_sync_service.entity.TaskStatus;
import com.virtucon.batch_sync_service.entity.TaskType;

public interface TaskService {
    
    TaskEntity createTask(CreateTaskDto createTaskDto);
    
    Optional<TaskEntity> findById(UUID id);
    
    TaskDto getTaskDto(UUID id);
    
    TaskEntity updateTask(UUID id, UpdateTaskDto updateTaskDto);
    
    void deleteTask(UUID id);
    
    List<TaskEntity> findAllTasks();
    
    Page<TaskEntity> findAllTasks(Pageable pageable);
    
    List<TaskEntity> findByTaskType(TaskType taskType);
    
    List<TaskEntity> findByTaskStatus(TaskStatus taskStatus);
    
    List<TaskEntity> findByOwner(String owner);
    
    List<TaskEntity> findByFileUrl(String url);
    
    Page<TaskEntity> findNotAssignedReadyTasks(Pageable pageable);
}