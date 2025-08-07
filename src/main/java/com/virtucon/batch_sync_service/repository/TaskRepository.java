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
    
    @Query("SELECT t FROM TaskEntity t JOIN FETCH t.file WHERE t.file.url = :url")
    List<TaskEntity> findByFileUrl(@Param("url") String url);
    
    @Query("SELECT t FROM TaskEntity t JOIN FETCH t.file WHERE t.taskType = :taskType AND t.taskStatus = :status")
    Page<TaskEntity> findByTaskTypeAndTaskStatus(
        @Param("taskType") TaskType taskType, 
        @Param("status") TaskStatus taskStatus, 
        Pageable pageable
    );
    
    @Query("SELECT t FROM TaskEntity t JOIN FETCH t.file WHERE t.taskStatus = :status ORDER BY t.createdAt DESC")
    List<TaskEntity> findByTaskStatusWithFile(@Param("status") TaskStatus status);
    
    @Query("SELECT t FROM TaskEntity t JOIN FETCH t.file WHERE t.owner = :owner ORDER BY t.updatedAt DESC")
    List<TaskEntity> findByOwnerWithFile(@Param("owner") String owner);
    
    interface TaskSummaryProjection {
        java.util.UUID getId();
        TaskStatus getTaskStatus();
        TaskType getTaskType();
        String getOwner();
        java.time.LocalDateTime getCreatedAt();
        java.time.LocalDateTime getUpdatedAt();
        FileProjection getFile();
        
        interface FileProjection {
            java.util.UUID getId();
            String getUrl();
        }
    }
    
    @Query("SELECT t FROM TaskEntity t")
    Page<TaskSummaryProjection> findAllSummary(Pageable pageable);
}