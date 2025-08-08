package com.virtucon.batch_sync_service.service.impl;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.virtucon.batch_sync_service.dto.CreateTaskDto;
import com.virtucon.batch_sync_service.dto.PatchTaskDto;
import com.virtucon.batch_sync_service.dto.TaskDto;
import com.virtucon.batch_sync_service.dto.UpdateTaskDto;
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
    private final java.time.Clock clock;

    public TaskServiceImpl(TaskRepository taskRepository, FileRepository fileRepository, java.time.Clock clock) {
        this.taskRepository = taskRepository;
        this.fileRepository = fileRepository;
        this.clock = clock;
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
    @Transactional(readOnly = true)
    public TaskDto getTaskDto(UUID id) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task", id));
        
        return new TaskDto(
                task.getId(),
                task.getFile().getId(),
                task.getFile().getUrl(),
                task.getTaskType(),
                task.getTaskStatus(),
                task.getProcessingStart(),
                task.getProcessingEnd(),
                task.getOwner(),
                task.getCreatedAt(),
                task.getUpdatedAt()
        );
    }

    @Override
    public TaskEntity updateTask(UUID id, UpdateTaskDto updateTaskDto) {
        TaskEntity task = taskRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Task", id));

        if (updateTaskDto.taskType() != null) {
            task.setTaskType(updateTaskDto.taskType());
        }
        if (updateTaskDto.taskStatus() != null) {
            task.setTaskStatus(updateTaskDto.taskStatus());
        }
        if (updateTaskDto.processingStart() != null) {
            task.setProcessingStart(updateTaskDto.processingStart());
        }
        if (updateTaskDto.processingEnd() != null) {
            task.setProcessingEnd(updateTaskDto.processingEnd());
        }
        if (updateTaskDto.owner() != null) {
            task.setOwner(updateTaskDto.owner());
        }

        return taskRepository.save(task);
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
    public void deleteTask(UUID id) {
        if (!taskRepository.existsById(id)) {
            throw new EntityNotFoundException("Task", id);
        }
        taskRepository.deleteById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskEntity> findAllTasks() {
        return taskRepository.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskEntity> findAllTasks(Pageable pageable) {
        return taskRepository.findAll(pageable);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskEntity> findByTaskType(TaskType taskType) {
        return taskRepository.findByTaskType(taskType);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskEntity> findByTaskStatus(TaskStatus taskStatus) {
        return taskRepository.findByTaskStatus(taskStatus);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskEntity> findByOwner(String owner) {
        return taskRepository.findByOwner(owner);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TaskEntity> findByFileUrl(String url) {
        return taskRepository.findByFileUrl(url);
    }

    @Override
    @Transactional(readOnly = true)
    public Page<TaskEntity> findNotAssignedReadyTasks(Pageable pageable) {
        return taskRepository.findByTaskTypeAndTaskStatus(TaskType.NOT_ASSIGNED, TaskStatus.READY, pageable);
    }
}