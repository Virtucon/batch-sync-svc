package com.virtucon.batch_sync_service.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Entity
@Table(name = "tasks")
public class TaskEntity {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "file_id", unique = true, nullable = false)
    @NotNull(message = "File cannot be null")
    private FileEntity file;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_type", nullable = false)
    @NotNull(message = "Task type cannot be null")
    private TaskType taskType;

    @Enumerated(EnumType.STRING)
    @Column(name = "task_status", nullable = false)
    @NotNull(message = "Task status cannot be null")
    private TaskStatus taskStatus;

    @Column(name = "processing_start")
    private LocalDateTime processingStart;

    @Column(name = "processing_end")
    private LocalDateTime processingEnd;

    @Column(name = "owner")
    private String owner;

    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;

    @Column(name = "updated_at", nullable = false)
    private LocalDateTime updatedAt;

    protected TaskEntity() {
    }

    public TaskEntity(FileEntity file, TaskType taskType, TaskStatus taskStatus, String owner) {
        this.file = file;
        this.taskType = taskType;
        this.taskStatus = taskStatus;
        this.owner = owner;
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    public void preUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    @PrePersist
    public void prePersist() {
        LocalDateTime now = LocalDateTime.now();
        if (this.createdAt == null) {
            this.createdAt = now;
        }
        this.updatedAt = now;
    }

    public UUID getId() {
        return id;
    }

    public FileEntity getFile() {
        return file;
    }

    public void setFile(FileEntity file) {
        this.file = file;
    }

    public TaskType getTaskType() {
        return taskType;
    }

    public void setTaskType(TaskType taskType) {
        this.taskType = taskType;
    }

    public TaskStatus getTaskStatus() {
        return taskStatus;
    }

    public void setTaskStatus(TaskStatus taskStatus) {
        this.taskStatus = taskStatus;
    }

    public LocalDateTime getProcessingStart() {
        return processingStart;
    }

    public void setProcessingStart(LocalDateTime processingStart) {
        this.processingStart = processingStart;
    }

    public LocalDateTime getProcessingEnd() {
        return processingEnd;
    }

    public void setProcessingEnd(LocalDateTime processingEnd) {
        this.processingEnd = processingEnd;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskEntity that = (TaskEntity) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}