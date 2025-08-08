package com.virtucon.batch_sync_service.service;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.virtucon.batch_sync_service.dto.CreateTaskDto;
import com.virtucon.batch_sync_service.dto.PatchTaskDto;
import com.virtucon.batch_sync_service.entity.TaskEntity;

public interface TaskService {
    
    TaskEntity createTask(CreateTaskDto createTaskDto);
    
    Optional<TaskEntity> findById(UUID id);
    
    TaskEntity patchTask(UUID id, PatchTaskDto patchTaskDto);
    
    Page<TaskEntity> findNotAssignedReadyTasks(Pageable pageable);
}