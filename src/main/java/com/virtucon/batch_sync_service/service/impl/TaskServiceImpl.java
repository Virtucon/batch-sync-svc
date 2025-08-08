package com.virtucon.batch_sync_service.service.impl;

import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.virtucon.batch_sync_service.dto.CreateTaskDto;
import com.virtucon.batch_sync_service.dto.PatchTaskDto;
import com.virtucon.batch_sync_service.entity.FileEntity;
import com.virtucon.batch_sync_service.entity.TaskEntity;
import com.virtucon.batch_sync_service.entity.TaskStatus;
import com.virtucon.batch_sync_service.entity.TaskType;
import com.virtucon.batch_sync_service.exception.EntityNotFoundException;
import com.virtucon.batch_sync_service.repository.FileRepository;
import com.virtucon.batch_sync_service.repository.TaskRepository;
import com.virtucon.batch_sync_service.service.TaskService;

@Service
@Transactional
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final FileRepository fileRepository;

    public TaskServiceImpl(TaskRepository taskRepository, FileRepository fileRepository) {
        this.taskRepository = taskRepository;
        this.fileRepository = fileRepository;
    }

    @Override
    public TaskEntity createTask(CreateTaskDto createTaskDto) {
        FileEntity file = fileRepository.findByUrl(createTaskDto.fileUrl())
                .orElseGet(() -> {
                    FileEntity newFile = new FileEntity(createTaskDto.fileUrl());
                    return fileRepository.save(newFile);
                });

        TaskEntity task = new TaskEntity(file, createTaskDto.taskType(), createTaskDto.taskStatus(), createTaskDto.owner());
        task.setProcessingStart(createTaskDto.processingStart());
        task.setProcessingEnd(createTaskDto.processingEnd());
        
        return taskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<TaskEntity> findById(UUID id) {
        return taskRepository.findById(id);
    }

    @Override
    public TaskEntity patchTask(UUID id, PatchTaskDto patchTaskDto) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task", id));

        if (patchTaskDto.taskType() != null) {
            task.setTaskType(patchTaskDto.taskType());
        }
        if (patchTaskDto.taskStatus() != null) {
            task.setTaskStatus(patchTaskDto.taskStatus());
        }

        return taskRepository.save(task);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskEntity> findNotAssignedReadyTasks(Pageable pageable) {
        return taskRepository.findByTaskTypeAndTaskStatus(TaskType.NOT_ASSIGNED, TaskStatus.READY, pageable);
    }
}