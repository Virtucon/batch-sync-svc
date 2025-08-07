package com.virtucon.batch_sync_service.repository;

import com.virtucon.batch_sync_service.entity.TaskEntity;
import com.virtucon.batch_sync_service.entity.TaskStatus;
import com.virtucon.batch_sync_service.entity.TaskType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskRepository extends JpaRepository<TaskEntity, UUID> {
    
    List<TaskEntity> findByTaskType(TaskType taskType);
    
    List<TaskEntity> findByTaskStatus(TaskStatus taskStatus);
    
    List<TaskEntity> findByOwner(String owner);
    
    @Query("SELECT t FROM TaskEntity t WHERE t.file.url = :url")
    List<TaskEntity> findByFileUrl(@Param("url") String url);
    
    Page<TaskEntity> findByTaskTypeAndTaskStatus(TaskType taskType, TaskStatus taskStatus, Pageable pageable);
}